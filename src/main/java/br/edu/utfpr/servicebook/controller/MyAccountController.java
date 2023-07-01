package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.PhoneNumberVerificationService;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import br.edu.utfpr.servicebook.util.UserWizardUtil;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.twilio.example.ValidationExample.ACCOUNT_SID;
import static com.twilio.example.ValidationExample.AUTH_TOKEN;

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

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.verify.service.sid}")
    private String twilioVerifyServiceSid;

    private String userSmsErrorForwarding(String step, UserSmsDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

       return "redirect:/minha-conta/meu-contato/{id}";
    }
    @GetMapping
    public String home(HttpServletRequest request) {
        return "redirect:/minha-conta/cliente";
    }

    /**
     * Mostra a tela de perfil do usuário.
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

        if(!oUser.isPresent()) {
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

    @GetMapping("/meu-contato/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showMyContactPhone(@PathVariable Long id) throws IOException {
        Optional<User> oUser = this.userService.findByEmail(authentication.getEmail());

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }

        UserDTO userDTO = userMapper.toDto(oUser.get());

        if (id != oUser.get().getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não ter permissão para atualizar esse telefone.");
        }

        ModelAndView mv = new ModelAndView("professional/account/my-contact-phone");

        mv.addObject("professional", userDTO);

        return mv;
    }


    /**
     * FIXME Ao mudar o email, fazer logout para o usuário logar novamente, aí com o novo email
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

        if(!oUser.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado pelas informações fornecidas.");
        }


        User user = oUser.get();

        String email = request.getParameter("email");
        Optional<User> oOtherUser = userService.findByEmail(email);

        if (oOtherUser.isPresent()){
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

        //return "redirect:/logout";
        return "redirect:/minha-conta/meu-email/{id}";
    }

    @PostMapping("/salvar-contato/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String savePhoneNumber(
            @PathVariable Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes)
            throws IOException {

        Optional<User> oUser = this.userService.findByEmail(authentication.getEmail());


        if(!oUser.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado pelas informações fornecidas.");
        }

        if (id != oUser.get().getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não ter permissão para atualizar esse telefone.");
        }

        User user = oUser.get();

        UserDTO userDTO = userMapper.toDto(oUser.get());

        String phoneNumber = userDTO.getPhoneNumber();
        Optional<User> oOtherUser = userService.findByPhoneNumber(phoneNumber);

        if (oOtherUser.isPresent()){
            redirectAttributes.addFlashAttribute("msgError", "Telefone já cadastrado! Por favor, insira um telefone não cadastrado!");
            return "redirect:/minha-conta/meu-contato/{id}";
        }

        user.setPhoneNumber(phoneNumber);
        user.setPhoneVerified(false);
        this.userService.save(user);

        PhoneNumberVerificationService phoneNumberVerificationService = new PhoneNumberVerificationService(twilioAccountSid, twilioAuthToken, twilioVerifyServiceSid, phoneNumber);
        phoneNumberVerificationService.sendSMSToVerification();


        redirectAttributes.addFlashAttribute("msg", "Telefone salvo com sucesso!");

        //return "redirect:/logout";
        return "redirect:/minha-conta/meu-contato/{id}";
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

        if(!oProfessional.isPresent()) {
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

    @PostMapping("/validar-telefone/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String saveUserPhoneCode(
            @PathVariable Long id,
            @Validated(UserCodeDTO.RequestUserCodeInfoGroupValidation.class) UserCodeDTO dto,
            HttpServletRequest request,
            BindingResult errors,
            RedirectAttributes redirectAttributes
    ) {

        Optional<User> oUser = this.userService.findByEmail(authentication.getEmail());

        if (id != oUser.get().getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não ter permissão para atualizar esse telefone.");
        }

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado pelas informações fornecidas.");
        }

        User professional = oUser.get();

        String phoneNumber = professional.getPhoneNumber();

        PhoneNumberVerificationService phoneNumberVerificationService = new PhoneNumberVerificationService(twilioAccountSid, twilioAuthToken, twilioVerifyServiceSid, phoneNumber);

        try {
            phoneNumberVerificationService.verify(dto.getCode());

            if (phoneNumberVerificationService.isVerified()) {
                professional.setPhoneVerified(true);
                userService.save(professional);
                redirectAttributes.addFlashAttribute("msg", "Telefone verificado com sucesso!");
            } else {
                professional.setPhoneVerified(false);
                redirectAttributes.addFlashAttribute("errors", "3Não foi possível verificar seu telefone no momento. Continue com o seu cadastro e tente novamente mais tarde.");
            }
        } catch (Exception e) {
            professional.setPhoneVerified(false);
            errors.rejectValue(null, "not-found", "Não foi possível verificar seu telefone no momento. Continue com o seu cadastro e tente novamente mais tarde.");
            redirectAttributes.addFlashAttribute("msg", errors.getAllErrors());
        }
        return "redirect:/minha-conta/meu-contato/{id}";
    }



}

