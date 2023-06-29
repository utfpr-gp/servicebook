package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@RequestMapping("/minha-conta")
@Controller
public class MyAccountController {

    public static final Logger log =
            LoggerFactory.getLogger(MyAccountController.class);

    @Autowired
    private IndividualService individualService;

    @Autowired
    private UserService userService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private UserCodeService userCodeService;

    @Autowired
    private AuthenticationCodeGeneratorService authenticationCodeGeneratorService;

    @Autowired
    private UserCodeMapper userCodeMapper;

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private TemplateUtil templateUtil;

    @GetMapping
    public String home(HttpServletRequest request) {
        return "redirect:/minha-conta/cliente";
    }

    @Autowired
    private CompanyService companyService;

    /**
     * Mostra a tela de perfil do usuário.
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/perfil")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView editProfile() throws IOException {

        Optional<User> oUser = (userService.findByEmail(authentication.getEmail()));

        if (!oUser.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        User user = oUser.get();

        UserDTO userDTO = userMapper.toDto(user);

        Optional<City> oCity = cityService.findById(user.getAddress().getCity().getId());
        if (!oCity.isPresent()) {
            throw new EntityNotFoundException("Cidade não foi encontrada pelo id informado.");
        }

        CityMinDTO cityMinDTO = cityMapper.toMinDto(oCity.get());

        UserTemplateInfo templateInfo = templateUtil.getUserInfo(user);

        ModelAndView mv = new ModelAndView("professional/edit-account");
        mv.addObject("professional", userDTO);
        mv.addObject("city", cityMinDTO);
        mv.addObject("userInfo", templateInfo);

        return mv;
    }

    /**
     * Mostra a tela de edição do anúncio geral do profissional.
     * Esta descrição aparecerá no portfólio público do profissional.
     *
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/meu-anuncio/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showMyAd(@PathVariable Long id) throws IOException {

        Optional<User> oUser = this.userService.findByEmail(authentication.getEmail());

        if (!oUser.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        User user = oUser.get();
        UserDTO userDTO = userMapper.toDto(user);

        UserTemplateInfo templateInfo = templateUtil.getUserInfo(user);

        ModelAndView mv = new ModelAndView("professional/account/my-ad");
        mv.addObject("user", userDTO);
        mv.addObject("userInfo", templateInfo);


        return mv;
    }

    @PatchMapping("/cadastra-descricao/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String updateDescriptionProfessional(@PathVariable Long id, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        Optional<User> oUser = this.userService.findByEmail(authentication.getEmail());

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado pelo id informado.");
        }

        User user = oUser.get();
        String description = request.getParameter("description");
        user.setDescription(description);
        this.userService.save(user);

        return "redirect:/minha-conta/meu-anuncio/{id}";
    }

    /**
     * Apresenta a tela de email do usuário.
     * FIXME Depois de modificado, informar para o usuário fazer o login novamente.
     *
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/meu-email/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showMyEmail(@PathVariable Long id) throws IOException {
        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        UserDTO userDTO = userMapper.toDto(oUser.get());

        ModelAndView mv = new ModelAndView("professional/account/my-email");

        mv.addObject("professional", userDTO);

        return mv;
    }

    /**
     * FIXME Ao mudar o email, fazer logout para o usuário logar novamente, aí com o novo email
     *
     * @param id
     * @param request
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PostMapping("/salvar-email/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String saveEmail(
            @PathVariable Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes)
            throws IOException {

        Optional<User> oUser = this.userService.findByEmail(authentication.getEmail());

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado pelas informações fornecidas.");
        }

        User user = oUser.get();

        String email = request.getParameter("email");
        Optional<User> oOtherUser = userService.findByEmail(email);

        if (oOtherUser.isPresent()) {
            redirectAttributes.addFlashAttribute("msgError", "Email já cadastrado! Por favor, insira um email não cadastrado!");
            return "redirect:/minha-conta/meu-email/{id}";
        }

        user.setEmail(email);
        user.setEmailVerified(false);
        this.userService.save(user);

        Optional<UserCode> oUserCode = userCodeService.findByEmail(email);
        String actualCode = "";

        if (!oUserCode.isPresent()) {
            String code = authenticationCodeGeneratorService.generateAuthenticationCode();

            UserCodeDTO userCodeDTO = new UserCodeDTO(email, code);
            UserCode userCode = userCodeMapper.toEntity(userCodeDTO);

            userCodeService.save(userCode);
            actualCode = code;
        } else {
            actualCode = oUserCode.get().getCode();
        }

        String tokenLink = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString() + "/login/codigo/" + actualCode;
        quartzService.sendEmailToConfirmationCode(email, actualCode, tokenLink);

        redirectAttributes.addFlashAttribute("msg", "Email salvo com sucesso!");

        return "redirect:/minha-conta/meu-email/{id}";
    }

    @PostMapping("/validar-email/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String saveUserEmailCode(
            @PathVariable Long id,
            @Validated(UserCodeDTO.RequestUserCodeInfoGroupValidation.class) UserCodeDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes
    ) {

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("msgError", "Houve um erro na verificação do código, verifique se digitou corretamente!");
            return "redirect:/minha-conta/meu-email/{id}";
        }

        Optional<Individual> oProfessional = this.individualService.findById(id);

        if (!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado pelas informações fornecidas.");
        }

        Individual professional = oProfessional.get();
        Optional<UserCode> oUserCode = userCodeService.findByEmail(professional.getEmail());

        if (!oUserCode.isPresent()) {
            redirectAttributes.addFlashAttribute("msgError", "Código inválido! Por favor, insira o código de autenticação.");
            return "redirect:/minha-conta/meu-email/{id}";
        }

        if (!dto.getCode().equals(oUserCode.get().getCode())) {
            redirectAttributes.addFlashAttribute("msgError", "Código inválido! Por favor, insira o código de autenticação.");
            return "redirect:/minha-conta/meu-email/{id}";
        }

        professional.setEmailVerified(true);
        this.individualService.save(professional);

        redirectAttributes.addFlashAttribute("msg", "Email verificado com sucesso!");

        return "redirect:/minha-conta/meu-email/{id}";
    }

    /**
     * Mostra a tela de edição das informações pessoais do profissional.
     *
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/informacoes-pessoais/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showMyPersonalData(@PathVariable Long id) throws IOException {

        Optional<User> oUser = this.userService.findByEmail(authentication.getEmail());

        if (oUser.isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        User user = oUser.get();
        UserDTO userDTO = userMapper.toDto(user);

        UserTemplateInfo templateInfo = templateUtil.getUserInfo(user);

        ModelAndView mv = new ModelAndView("professional/account/my-personal-data");
        mv.addObject("userDTO", userDTO);
        mv.addObject("userInfo", templateInfo);

        return mv;
    }

    @PatchMapping("/cadastra-informacoes-pessoais/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView updateMyPersonalData(
            @PathVariable Long id,
            @Valid UserDTO userDTO,
            BindingResult errors,
            RedirectAttributes redirectAttributes
    ) throws IOException {

        Optional<User> oUser = this.userService.findByEmail(authentication.getEmail());

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado pelo id informado.");
        }

        if (!id.equals(oUser.get().getId())) {
            throw new InvalidParamsException("Acesso não autorizado!");
        }

        if (errors.hasErrors()) {
            ModelAndView mv = new ModelAndView("professional/account/my-personal-data");
            mv.addObject("userDTO", userDTO);
            mv.addObject("errors", errors.getAllErrors());

            return mv;
        }

        if (userDTO.getId() != null) {

            User user = oUser.get();
            user.setName(userDTO.getName());

            if (userDTO.getCnpj() == null) {
                Optional<Individual> oInvididual = this.individualService.findById(id);

                Individual individual = oInvididual.get();
                individual.setCpf(userDTO.getCpf());

                individual.setBirthDate(userDTO.getBirthDate());

                this.individualService.save(individual);
            } else {
                Optional<Company> oCompany = this.companyService.findById(id);

                Company company = oCompany.get();
                company.setCnpj(userDTO.getCnpj());

                this.companyService.save(company);
            }

            this.userService.save(user);
        }

        redirectAttributes.addFlashAttribute("msg", "Informações pessoais atualizadas com sucesso!");

        return new ModelAndView("redirect:/minha-conta/informacoes-pessoais/{id}");
    }
}
