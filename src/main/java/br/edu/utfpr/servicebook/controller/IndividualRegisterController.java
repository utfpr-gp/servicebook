package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import br.edu.utfpr.servicebook.util.NumberValidator;
import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelItensDTO;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelProfessionalExpertiseRatingDTO;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelUtil;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TESTE COMMIT LUCAS AURELIO
@Controller
@Slf4j
@RequestMapping("/cadastrar-se")
@SessionAttributes("wizard")
public class IndividualRegisterController {

    @Autowired
    private WizardSessionUtil<IndividualDTO> wizardSessionUtil;
    @Autowired
    private WizardSessionUtil<ProfessionalExpertiseDTO> wizardSessionUtilExpertise;
    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private UserCodeService userCodeService;

    @Autowired
    private UserCodeMapper userCodeMapper;

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

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.verify.service.sid}")
    private String twilioVerifyServiceSid;

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

    private String userRegistrationErrorForwarding(String step, IndividualDTO dto, Model model, BindingResult errors) {
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
    public String showUserRegistrationWizard(
            @RequestParam(value = "passo", required = false, defaultValue = "1") Long step,
            HttpSession httpSession,
            Model model
    ) throws Exception{

        if (step < 1 || step > 9) {
            step = 1L;
        }

        if(step == 8){
            List<Expertise> professionPage = expertiseService.findAll();
            List<ExpertiseDTO> expertiseDTOs = professionPage.stream()
                    .map(s -> expertiseMapper.toDto(s))
                    .collect(Collectors.toList());

            IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);

            model.addAttribute("individual", sessionDTO);
            model.addAttribute("expertises", expertiseDTOs);

            ProfessionalExpertiseDTO professionalExpertiseDTO= wizardSessionUtilExpertise.getWizardState(httpSession, ProfessionalExpertiseDTO.class, WizardSessionUtil.KEY_EXERPERTISES);

            List<Expertise> documentList = new ArrayList<>();

            if(professionalExpertiseDTO.getIds() != null){
                for (int id : professionalExpertiseDTO.getIds()) {
                    Optional<Expertise> oExpertises =  expertiseService.findById((Long.valueOf(id)));
                    if (!oExpertises.isPresent()) {
                        throw new Exception("Não existe essa especialidade!");
                    }

                    documentList.add(oExpertises.get());
                }

                model.addAttribute("professionalExpertises", documentList);
            }
        }

        IndividualDTO dto = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        model.addAttribute("dto", dto);
        return "visitor/user-registration/wizard-step-0" + step;
    }

    @PostMapping("/passo-1")
    public String saveUserEmail(
            HttpSession httpSession,
            @Validated(IndividualDTO.RequestUserEmailInfoGroupValidation.class) IndividualDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws MessagingException {

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dto, model, errors);
        }
        String email = dto.getEmail();
        Optional<Individual> oUser = individualService.findByEmail(dto.getEmail());

        if (oUser.isPresent()) {
            errors.rejectValue("email", "error.dto", "Email já cadastrado! Por favor, insira um email não cadastrado.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("1", dto, model, errors);
        }

        Optional<UserCode> oUserCode = userCodeService.findByEmail(dto.getEmail());
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
        quartzService.sendEmailToConfirmationCode(email, actualCode);

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        sessionDTO.setEmail(dto.getEmail());

        return "redirect:/cadastrar-se?passo=2";
    }

    @PostMapping("/passo-2")
    public String saveUserEmailCode(
            HttpSession httpSession,
            @Validated(UserCodeDTO.RequestUserCodeInfoGroupValidation.class) UserCodeDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding("2", dto, model, errors);
        }

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        Optional<UserCode> oUserCode = userCodeService.findByEmail(sessionDTO.getEmail());

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

        sessionDTO.setEmailVerified(true);

        redirectAttributes.addFlashAttribute("msg", "Email verificado com sucesso!");

        return "redirect:/cadastrar-se?passo=3";
    }

    @PostMapping("/passo-3")
    public String saveUserPassword(
            HttpSession httpSession,
            @Validated(IndividualDTO.RequestUserPasswordInfoGroupValidation.class) IndividualDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("3", dto, model, errors);
        }

        if(!dto.getPassword().equals(dto.getRepassword())){
            errors.rejectValue("password", "error.dto", "As senhas não correspondem. Por favor, tente novamente.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("3", dto, model, errors);
        }

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        sessionDTO.setPassword(dto.getPassword());
        sessionDTO.setRepassword(dto.getRepassword());

        return "redirect:/cadastrar-se?passo=4";
    }

    @PostMapping("/passo-4")
    public String saveUserPhone(
            HttpSession httpSession,
            @Validated(IndividualDTO.RequestUserPhoneInfoGroupValidation.class) IndividualDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("4", dto, model, errors);
        }

        NumberValidator numberValidator = new NumberValidator(twilioAccountSid, twilioAuthToken, twilioVerifyServiceSid, dto.getPhoneNumber());

        numberValidator.sendVerifySms();

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        sessionDTO.setPhoneNumber(dto.getPhoneNumber());

        return "redirect:/cadastrar-se?passo=5";
    }

    @PostMapping("/passo-5")
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

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);

        NumberValidator numberValidator = new NumberValidator(twilioAccountSid, twilioAuthToken, twilioVerifyServiceSid, sessionDTO.getPhoneNumber());

        try {
            numberValidator.sendVerifyCode(dto.getCode());
        } catch (Exception e) {
            errors.rejectValue("code", "error.dto", "Não foi possível verificar seu telefone no momento. Continue com o seu cadastro e tente novamente mais tarde.");
        }

        if (errors.hasErrors()) {
            return this.userSmsErrorForwarding("5", dto, model, errors);
        }

        if (!numberValidator.isVerified()) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
        }

        if (errors.hasErrors()) {
            return this.userSmsErrorForwarding("5", dto, model, errors);
        }

        sessionDTO.setPhoneVerified(true);

        Optional<Individual> oUser = individualService.findByPhoneNumber(dto.getPhoneNumber());

        if(oUser.isPresent()) {
            Individual user = oUser.get();
            user.setPhoneVerified(false);
            individualService.save(user);
        }

        redirectAttributes.addFlashAttribute("msg", "Telefone verificado com sucesso!");

        return "redirect:/cadastrar-se?passo=6";
    }

    @PostMapping("/passo-6")
    public String saveUserNameAndCPF(
            HttpSession httpSession,
            @Validated(IndividualDTO.RequestUserNameAndCPFInfoGroupValidation.class) IndividualDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("6", dto, model, errors);
        }

        Optional<Individual> oUser = individualService.findByCpf(dto.getCpf());

        if (oUser.isPresent()) {
            errors.rejectValue("cpf", "error.dto", "CPF já cadastrado! Por favor, insira um CPF não cadastrado.");
        }

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("6", dto, model, errors);
        }

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        sessionDTO.setName(dto.getName());
        sessionDTO.setCpf(dto.getCpf());

        return "redirect:/cadastrar-se?passo=7";
    }

    @PostMapping("/passo-7")
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
        addressFullDTO.setStreet(dto.getStreet());
        addressFullDTO.setNumber(dto.getNumber());
        addressFullDTO.setPostalCode(dto.getPostalCode());
        addressFullDTO.setNeighborhood(dto.getNeighborhood());
        addressFullDTO.setCity(cityMidDTO);

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        sessionDTO.setAddress(addressFullDTO);
        sessionDTO.setProfileVerified(true);

        return "redirect:/cadastrar-se?passo=8";
    }

    @PostMapping("/passo-8")
    public String saveExpertises(
            HttpSession httpSession,
            ProfessionalExpertiseDTO dto,
            RedirectAttributes redirectAttributes,
            Model model,
            IndividualDTO individualDTO
    )throws Exception{
        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        Individual user = individualMapper.toEntity(sessionDTO);

        List<Integer> ids = dto.getIds();

        if(ids != null){
            for (int id : ids) {
                Optional<Expertise> oExpertises =  expertiseService.findById((Long.valueOf(id)));
                if (!oExpertises.isPresent()) {
                    throw new Exception("Não existe essa especialidade!");
                }

                ProfessionalExpertiseDTO professionalExpertiseDTO= wizardSessionUtilExpertise.getWizardState(httpSession, ProfessionalExpertiseDTO.class, WizardSessionUtil.KEY_EXERPERTISES);
                professionalExpertiseDTO.setIds(ids);
            }
        }


        return "redirect:/cadastrar-se?passo=8";
    }

    @PostMapping("/passo-9")
    public String saveUser(
            HttpSession httpSession,
            IndividualDTO dto,
            BindingResult errors,
            Model model,
            RedirectAttributes redirectAttributes,
            SessionStatus status
    )throws Exception {

        IndividualDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, IndividualDTO.class, WizardSessionUtil.KEY_WIZARD_USER);
        ProfessionalExpertiseDTO professionalExpertiseDTO= wizardSessionUtilExpertise.getWizardState(httpSession, ProfessionalExpertiseDTO.class, WizardSessionUtil.KEY_EXERPERTISES);

        validator.validate(sessionDTO, errors, new Class[]{
                IndividualDTO.RequestUserEmailInfoGroupValidation.class,
                IndividualDTO.RequestUserPasswordInfoGroupValidation.class,
                IndividualDTO.RequestUserPhoneInfoGroupValidation.class,
                IndividualDTO.RequestUserNameAndCPFInfoGroupValidation.class,
                AddressDTO.RequestUserAddressInfoGroupValidation.class
        });

        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding("8", dto, model, errors);
        }

        Individual user = individualMapper.toEntity(sessionDTO);

        for (int id : professionalExpertiseDTO.getIds()) {
            Optional<Expertise> e = expertiseService.findById((Long.valueOf(id)));
            if (!e.isPresent()) {
                throw new Exception("Não existe essa especialidade!");
            }

            ProfessionalExpertise professionalExpertise = new ProfessionalExpertise(user, e.get());

            individualService.saveExpertisesIndividual(user, professionalExpertise);
        }

        redirectAttributes.addFlashAttribute("msg", "Usuário cadastrado com sucesso! Realize o login no Servicebook!");
        status.setComplete();

        return "redirect:/login";
    }
}
