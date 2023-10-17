package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.ProfileEnum;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.PhoneNumberVerificationService;
import br.edu.utfpr.servicebook.util.UserWizardUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.PermitAll;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

import static br.edu.utfpr.servicebook.security.ProfileEnum.ROLE_COMPANY;

/**
 * Faz o cadastro do usuário individual ou empresa por meio de um wizard.
 * O primeiro passo define se o cadastro será de empresa ou indivíduo e a distinção se dá pela rota acessada.
 * A principal diferença entre as duas entidades é o CPF e CNPJ. Em relação aos demais atributos, usa-se a classe User.
 */
@Controller
@Slf4j
@RequestMapping("/cadastrar-se")
public class UserRegisterController {

    @Autowired
    private UserWizardUtil userWizardUtil;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserCodeService userCodeService;

    @Autowired
    private UserCodeMapper userCodeMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private AuthenticationCodeGeneratorService authenticationCodeGeneratorService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ProfessionalExpertiseMapper professionalExpertiseMapper;

    @Autowired
    PhoneNumberVerificationService phoneNumberVerificationService;

    private String userRegistrationErrorForwarding(String step, UserDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/user-registration/wizard-step-0" + step;
    }

    private String userCodeErrorForwarding(String step, UserCodeDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/user-registration/wizard-step-0" + step;
    }

    private String userSmsErrorForwarding(String step, UserSmsDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/user-registration/wizard-step-0" + step;
    }

    private String userAddressRegistrationErrorForwarding(String step, AddressDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/user-registration/wizard-step-0" + step;
    }

    @GetMapping
    @PermitAll
    public String showUserRegistrationWizard(
            @RequestParam(value = "passo", required = false, defaultValue = "1") Long step,
            HttpSession httpSession,
            Model model
    ) throws Exception {

        if (step < 1 || step > 9) {
            step = 1L;
        }

        if (step == 7) {
            List<Expertise> professionPage = expertiseService.findAll();
            List<ExpertiseDTO> expertiseDTOs = professionPage.stream()
                    .map(s -> expertiseMapper.toDto(s))
                    .collect(Collectors.toList());

            IndividualDTO individualDTO = (IndividualDTO) userWizardUtil.getWizardState(httpSession, IndividualDTO.class, UserWizardUtil.KEY_WIZARD_INDIVIDUAL);
            CompanyDTO companyDTO = (CompanyDTO) userWizardUtil.getWizardState(httpSession, CompanyDTO.class, UserWizardUtil.KEY_WIZARD_COMPANY);

            model.addAttribute("individual", individualDTO);
            model.addAttribute("expertises", expertiseDTOs);
            model.addAttribute("companies", companyDTO);

            ProfessionalExpertiseDTO professionalExpertiseDTO = (ProfessionalExpertiseDTO) userWizardUtil.getWizardState(httpSession, ProfessionalExpertiseDTO.class, UserWizardUtil.KEY_EXPERTISES);

            List<Expertise> professionalExpertises = new ArrayList<>();

            if (professionalExpertiseDTO.getIds() != null) {
                for (Integer id : professionalExpertiseDTO.getIds()) {
                    Optional<Expertise> oExpertises = expertiseService.findById((Long.valueOf(id)));
                    if (!oExpertises.isPresent()) {
                        throw new Exception("Não existe essa especialidade!");
                    }

                    professionalExpertises.add(oExpertises.get());
                }

                model.addAttribute("professionalExpertises", professionalExpertises);
            }
        }

        return "visitor/user-registration/wizard-step-0" + step;
    }

    /**
     * Remove os dados do cadastro corrente da sessão a fim de permitir um novo cadastro.
     */
    private void resetSessionAttributes(HttpSession httpSession) {
        httpSession.removeAttribute(UserWizardUtil.KEY_IS_REGISTER_COMPANY);
        userWizardUtil.removeWizardState(httpSession, UserWizardUtil.KEY_WIZARD_INDIVIDUAL);
        userWizardUtil.removeWizardState(httpSession, UserWizardUtil.KEY_EXPERTISES);
        userWizardUtil.removeWizardState(httpSession, UserWizardUtil.KEY_WIZARD_COMPANY);
    }

    /**
     * Cadastra os dados iniciais de um indivíduo..
     * Guarda na sessão que o cadastro é de um indivíduo.
     * Recebe o email apenas e antes de persistir, verifica se já não está sendo usado.
     * Envia o email com o código para confirmar a veracidade do email.
     *
     * @param httpSession
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @param model
     * @return
     * @throws MessagingException
     */
    @PostMapping("/individuo/passo-1")
    @PermitAll
    public String saveUserEmail(
            HttpSession httpSession,
            @Validated(UserDTO.RequestUserEmailInfoGroupValidation.class) IndividualDTO dto,
            @Validated(UserDTO.RequestUserNameAndCPFInfoGroupValidation.class) IndividualDTO dtouser,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws MessagingException {

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dto, model, errors);
        }
        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dtouser, model, errors);
        }

        String email = dto.getEmail().trim();
        httpSession.setAttribute(UserWizardUtil.KEY_IS_REGISTER_COMPANY, false);

        //verifica se o email já está cadastrado em uma conta existente
        Optional<User> oUser = userService.findByEmail(email);

        if (oUser.isPresent()) {
            errors.rejectValue("email", "error.dto", "Email já cadastrado! Por favor, insira um email não cadastrado.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dto, model, errors);
        }

        //gera um código para validar o email no passo seguinte
        Optional<UserCode> oUserCode = userCodeService.findByEmail(email);
        String actualCode = "";

        if (!oUserCode.isPresent()) {
            String code = authenticationCodeGeneratorService.generateAuthenticationCode();

            UserCodeDTO userCodeDTO = new UserCodeDTO(dto.getEmail(), code);
            UserCode userCode = userCodeMapper.toEntity(userCodeDTO);

            userCodeService.save(userCode);
            actualCode = code;
        } else {
            actualCode = oUserCode.get().getCode();
        }

        //envia o código por email ao usuário
        String tokenLink = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString() + "/login/codigo/" + actualCode;
        quartzService.sendEmailToConfirmationCode(email, actualCode, tokenLink);

        IndividualDTO userSessionDTO = null;
        userSessionDTO = (IndividualDTO) userWizardUtil.getWizardState(httpSession, IndividualDTO.class, UserWizardUtil.KEY_WIZARD_INDIVIDUAL);

        if (!dtouser.getCpf().isEmpty()) {
            //verifica se o CPF já está cadastrado para algum outro usuário
            Optional<Individual> oUserCpf = individualService.findByCpf(dtouser.getCpf());

            if (oUserCpf.isPresent()) {
                errors.rejectValue("cpf", "error.dto", "CPF já cadastrado! Por favor, insira um CPF não cadastrado.");
            }

            if (errors.hasErrors()) {
                return this.userRegistrationErrorForwarding("1", dtouser, model, errors);
            }

            userSessionDTO.setCpf(dtouser.getCpf());
        }

        System.out.println("userSessionDTO");
        System.out.println(dtouser.getCpf());

        userSessionDTO.setProfile(ProfileEnum.ROLE_USER);
        userSessionDTO.setEmail(email);
        //salva na sessão
        userSessionDTO.setName(dtouser.getName());
        userSessionDTO.setCpf(dtouser.getCpf());
        userSessionDTO.setProfileVerified(true);
        return "redirect:/cadastrar-se?passo=2";
    }

    /**
     * Cadastra os dados iniciais de uma empresa.
     * Guarda na sessão que o cadastro é de empresa e não de um indivíduo.
     * Recebe o email apenas e antes de persistir, verifica se já não está sendo usado.
     * Envia o email com o código para confirmar a veracidade do email.
     *
     * @param httpSession
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @param model
     * @return
     * @throws MessagingException
     */
    @PostMapping("/empresa/passo-1")
    @PermitAll
    public String saveUserEmail(
            HttpSession httpSession,
            @Validated(UserDTO.RequestUserEmailInfoGroupValidation.class) CompanyDTO dto,
            @Validated(UserDTO.RequestUserNameAndCNPJInfoGroupValidation.class) CompanyDTO dtocompany,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws MessagingException {

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dto, model, errors);
        }
        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dtocompany, model, errors);
        }
        String email = dto.getEmail().trim();
        httpSession.setAttribute(UserWizardUtil.KEY_IS_REGISTER_COMPANY, true);

        //verifica se o email já está cadastrado em uma conta existente
        Optional<User> oUser = userService.findByEmail(email);

        if (oUser.isPresent()) {
            errors.rejectValue("email", "error.dto", "Email já cadastrado! Por favor, insira um email não cadastrado.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dto, model, errors);
        }

        //gera um código para validar o email no passo seguinte
        Optional<UserCode> oUserCode = userCodeService.findByEmail(email);
        String actualCode = "";

        if (!oUserCode.isPresent()) {
            String code = authenticationCodeGeneratorService.generateAuthenticationCode();

            UserCodeDTO userCodeDTO = new UserCodeDTO(dto.getEmail(), code);
            UserCode userCode = userCodeMapper.toEntity(userCodeDTO);

            userCodeService.save(userCode);
            actualCode = code;
        } else {
            actualCode = oUserCode.get().getCode();
        }

        //envia o código por email ao usuário
        String tokenLink = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString() + "/login/codigo/" + actualCode;
        quartzService.sendEmailToConfirmationCode(email, actualCode, tokenLink);

        CompanyDTO userSessionDTO = null;

        //realiza o processamento particular de acordo com o tipo do usuário
        CompanyDTO companyDTO = (CompanyDTO) dto;
        userSessionDTO = (CompanyDTO) userWizardUtil.getWizardState(httpSession, CompanyDTO.class, UserWizardUtil.KEY_WIZARD_COMPANY);
        userSessionDTO.setProfile(ROLE_COMPANY);
        userSessionDTO.setEmail(email);

        CompanyDTO companySessionDTO = (CompanyDTO) userWizardUtil.getWizardState(httpSession, CompanyDTO.class, UserWizardUtil.KEY_WIZARD_COMPANY);

        //verifica se o CPF já está cadastrado para algum outro usuário
        Optional<Company> oUserCnpj = companyService.findByCnpj(dtocompany.getCnpj());

        if (oUserCnpj.isPresent()) {
            errors.rejectValue("cnpj", "error.dto", "CNPJ já cadastrado! Por favor, insira um CNPJ não cadastrado.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dtocompany, model, errors);
        }

        companySessionDTO.setName(dto.getName().trim());
        companySessionDTO.setCnpj(dto.getCnpj().trim());
        companySessionDTO.setProfileVerified(true);



        return "redirect:/cadastrar-se?passo=2";
    }

    /**
     * Realiza a verificação do email por meio de um código numérico enviado para o respectivo email.
     *
     * @param httpSession
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("/passo-2")
    @PermitAll
    public String saveUserEmailCode(
            HttpSession httpSession,
            @Validated UserCodeDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding("2", dto, model, errors);
        }

        UserDTO userSessionDTO = userWizardUtil.getUserDTO(httpSession);

        Optional<UserCode> oUserCode = userCodeService.findByEmail(userSessionDTO.getEmail());

        if (!oUserCode.isPresent()) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
        }

        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding("2", dto, model, errors);
        }

        if (!dto.getCode().equals(oUserCode.get().getCode())) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
        }

        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding("2", dto, model, errors);
        }

        userSessionDTO.setEmailVerified(true);

        redirectAttributes.addFlashAttribute("msg", "Email verificado com sucesso!");

        return "redirect:/cadastrar-se?passo=3";
    }

    /**
     * Salva a senha e contrasenha na sessão
     *
     * @param httpSession
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("/passo-3")
    @PermitAll
    public String saveUserPassword(
            HttpSession httpSession,
            @Validated(UserDTO.RequestUserPasswordInfoGroupValidation.class) UserDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("3", dto, model, errors);
        }

        if (!dto.getPassword().equals(dto.getRepassword())) {
            errors.rejectValue("password", "error.dto", "As senhas não correspondem. Por favor, tente novamente.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("3", dto, model, errors);
        }

        UserDTO userSessionDTO = userWizardUtil.getUserDTO(httpSession);
        userSessionDTO.setPassword(dto.getPassword());
        userSessionDTO.setRepassword(dto.getRepassword());

        return "redirect:/cadastrar-se?passo=4";
    }

    /**
     * Salva o telefone na sessão e enviar o código para verificação do número de telefone.
     *
     * @param httpSession
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("/passo-4")
    @PermitAll
    public String saveUserPhone(
            HttpSession httpSession,
            @Validated(UserDTO.RequestUserPhoneInfoGroupValidation.class) UserDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("4", dto, model, errors);
        }

        //envio o SMS para validação
        phoneNumberVerificationService.sendSMSToVerification(dto.getPhoneNumber());

        //salva o telefone na sessão
        UserDTO userSessionDTO = userWizardUtil.getUserDTO(httpSession);
        userSessionDTO.setPhoneNumber(dto.getPhoneNumber());

        return "redirect:/cadastrar-se?passo=5";
    }

    /**
     * Verifica se o código recebido por SMS é válido.
     *
     * @param httpSession
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("/passo-5")
    @PermitAll
    public String saveUserPhoneCode(
            HttpSession httpSession,
            @Validated(UserSmsDTO.RequestUserSmsInfoGroupValidation.class) UserSmsDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userSmsErrorForwarding("5", dto, model, errors);
        }

        UserDTO userSessionDTO = userWizardUtil.getUserDTO(httpSession);
        String phoneNumber = userSessionDTO.getPhoneNumber();

        boolean isVerified = false;
        try {
            isVerified = phoneNumberVerificationService.verify(dto.getCode(), phoneNumber);
        } catch (Exception e) {
            errors.rejectValue("code", "error.dto", "Não foi possível verificar seu telefone no momento. Continue com o seu cadastro e tente novamente mais tarde.");
        }

        if (errors.hasErrors()) {
            return this.userSmsErrorForwarding("5", dto, model, errors);
        }

        //telefone não foi verificado com sucesso
        if (!isVerified) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
        }

        if (errors.hasErrors()) {
            return this.userSmsErrorForwarding("5", dto, model, errors);
        }

        //telefone verificado com sucesso
        userSessionDTO.setPhoneVerified(true);

        //se este telefone está sendo usado por outro usuário, marca como não verificado, pois acabou de ser
        //verificado pelo usuário corrente que está sendo cadastrado
        Optional<User> oUser = userService.findByPhoneNumber(dto.getPhoneNumber());

        if (oUser.isPresent()) {
            User user = oUser.get();
            user.setPhoneVerified(false);
            userService.save(user);
        }

        redirectAttributes.addFlashAttribute("msg", "Telefone verificado com sucesso!");

//        return "redirect:/cadastrar-se?passo=6";
        return "redirect:/cadastrar-se?passo=6";

    }

    /**
     * Salva o endereço na sessão.
     *
     * @param httpSession
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("/passo-6")
    @PermitAll
    public String saveUserAddress(
            HttpSession httpSession,
            @Validated(AddressDTO.RequestUserAddressInfoGroupValidation.class) AddressDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userAddressRegistrationErrorForwarding("7", dto, model, errors);
        }

        Optional<City> oCity = cityService.findByName(dto.getCity());

        if (!oCity.isPresent()) {
            errors.rejectValue("city", "error.dto", "Cidade não cadastrada! Por favor, insira uma cidade cadastrada.");
        }

        if (errors.hasErrors()) {
            return this.userAddressRegistrationErrorForwarding("7", dto, model, errors);
        }

        CityMidDTO cityMidDTO = cityMapper.toMidDto(oCity.get());

        AddressFullDTO addressFullDTO = new AddressFullDTO();
        addressFullDTO.setStreet(dto.getStreet().trim());
        addressFullDTO.setNumber(dto.getNumber().trim());
        addressFullDTO.setPostalCode(dto.getPostalCode().trim());
        addressFullDTO.setNeighborhood(dto.getNeighborhood().trim());
        addressFullDTO.setCity(cityMidDTO);

        UserDTO userSessionDTO = userWizardUtil.getUserDTO(httpSession);
        userSessionDTO.setAddress(addressFullDTO);

        return "redirect:/cadastrar-se?passo=7";
    }

    @PostMapping("/passo-7")
    @PermitAll
    public String saveExpertises(
            HttpSession httpSession,
            ProfessionalExpertiseDTO dto,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws Exception {

        UserDTO userSessionDTO = userWizardUtil.getUserDTO(httpSession);
        Set<Integer> ids = dto.getIds();

        if (ids != null) {
            ProfessionalExpertiseDTO professionalExpertiseSessionDTO = (ProfessionalExpertiseDTO) userWizardUtil.getWizardState(httpSession, ProfessionalExpertiseDTO.class, UserWizardUtil.KEY_EXPERTISES);
            for (Integer id : ids) {
                Optional<Expertise> oExpertise = expertiseService.findById((Long.valueOf(id)));
                if (!oExpertise.isPresent()) {
                    throw new Exception("Não existe essa especialidade!");
                }
                professionalExpertiseSessionDTO.getIds().add(id);
            }
        }

        return "redirect:/cadastrar-se?passo=8";
    }

    @PostMapping("/passo-8")
    @PermitAll
    public String saveUser(
            HttpSession httpSession,
            UserDTO dto,
            BindingResult errors,
            Model model,
            RedirectAttributes redirectAttributes,
            SessionStatus status
    ) throws Exception {

        boolean isCompany = (Boolean) httpSession.getAttribute(UserWizardUtil.KEY_IS_REGISTER_COMPANY);
        String email = null;

        if (isCompany) {
            CompanyDTO userSessionDTO = (CompanyDTO) userWizardUtil.getWizardState(httpSession, CompanyDTO.class, UserWizardUtil.KEY_WIZARD_COMPANY);

            validator.validate(userSessionDTO, errors, new Class[]{
                    IndividualDTO.RequestUserEmailInfoGroupValidation.class,
                    IndividualDTO.RequestUserPasswordInfoGroupValidation.class,
                    IndividualDTO.RequestUserPhoneInfoGroupValidation.class,
                    IndividualDTO.RequestUserNameAndCNPJInfoGroupValidation.class,
                    AddressDTO.RequestUserAddressInfoGroupValidation.class
            });

            if (errors.hasErrors()) {
                return this.userRegistrationErrorForwarding("8", dto, model, errors);
            }

            Company company = companyMapper.toEntity(userSessionDTO);
            email = company.getEmail();
            companyService.save(company);
        } else {
            IndividualDTO userSessionDTO = (IndividualDTO) userWizardUtil.getWizardState(httpSession, IndividualDTO.class, UserWizardUtil.KEY_WIZARD_INDIVIDUAL);

            validator.validate(userSessionDTO, errors, new Class[]{
                    IndividualDTO.RequestUserEmailInfoGroupValidation.class,
                    IndividualDTO.RequestUserPasswordInfoGroupValidation.class,
                    IndividualDTO.RequestUserPhoneInfoGroupValidation.class,
                    IndividualDTO.RequestUserNameAndCPFInfoGroupValidation.class,
                    AddressDTO.RequestUserAddressInfoGroupValidation.class
            });

            if (errors.hasErrors()) {
                return this.userRegistrationErrorForwarding("8", dto, model, errors);
            }

            Individual individual = individualMapper.toEntity(userSessionDTO);
            email = individual.getEmail();
            userService.save(individual);
        }

        Optional<User> oUser = userService.findByEmail(email);

        if (!oUser.isPresent()) {
            throw new Exception("O usuário não foi encontrado.");
        }

        //faz a busca pelas especialidades informadas e relaciona ao profissional
        ProfessionalExpertiseDTO professionalExpertiseDTO = (ProfessionalExpertiseDTO) userWizardUtil.getWizardState(httpSession, ProfessionalExpertiseDTO.class, UserWizardUtil.KEY_EXPERTISES);
        if (professionalExpertiseDTO.getIds() != null) {
            for (Integer id : professionalExpertiseDTO.getIds()) {
                Optional<Expertise> e = expertiseService.findById((Long.valueOf(id)));
                if (!e.isPresent()) {
                    throw new Exception("Não existe essa especialidade!");
                }

                ProfessionalExpertise professionalExpertise = new ProfessionalExpertise(oUser.get(), e.get());
                professionalExpertiseService.save(professionalExpertise);
            }
        }

        redirectAttributes.addFlashAttribute("msg", "Usuário cadastrado com sucesso! Realize o login no Servicebook!");
        this.resetSessionAttributes(httpSession);

        return "redirect:/login";
    }
}
