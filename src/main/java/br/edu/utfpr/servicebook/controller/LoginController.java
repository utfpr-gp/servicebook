package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.AddressDTO;
import br.edu.utfpr.servicebook.model.dto.IndividualDTO;
import br.edu.utfpr.servicebook.model.dto.LoginDTO;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.UserCodeMapper;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@RequestMapping("/login")
@Controller
public class LoginController {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private WizardSessionUtil<LoginDTO> loginSessionUtil;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private AuthenticationCodeGeneratorService authenticationCodeGeneratorService;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private UserCodeService userCodeService;

    @Autowired
    private UserCodeMapper userCodeMapper;

    @Autowired
    private QuartzService quartzService;
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public ModelAndView showLogin() {
        ModelAndView mv = new ModelAndView("visitor/login");
        return mv;
    }

    /**
     * Encaminha o erro para a página de login.
     * O erro aparecerá dentro do campo de mensagem com fundo vermelho.
     * @param dto
     * @param model
     * @param errors
     * @return
     */
    private String emailErrorForwarding(LoginDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/login";
    }

    /**
     * Encaminha o erro de código de confirmação para a página de digitação do token.
     * A mensagem de erro aparecerá na página.
     * @param dto
     * @param model
     * @param errors
     * @return
     */
    private String codeErrorForwarding(LoginDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/login-sucess";
    }

    /**
     * Retorna a página de login.
     * @return
     * @throws IOException
     */
    @GetMapping("/email")
    public String formEmail() throws IOException {
        return "visitor/login";
    }

    /**
     * Retorna a página do usuário após verificar token de login.
     * @return
     * @throws IOException
     */
    @GetMapping("/login-by-token-email/{code}")
    public String loginByTokenEmail(
            @PathVariable("code") String code,
            @Validated(LoginDTO.CodeGroupValidation.class) LoginDTO dto,
            BindingResult errors,
            Model model
    ) throws IOException {
        if (errors.hasErrors()) {
            return this.codeErrorForwarding(dto, model, errors);
        }

        Optional<UserCode> oUserCode = userCodeService.findByCode(code);

        if (!oUserCode.isPresent()) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
            return this.codeErrorForwarding(dto, model, errors);
        }

        if (!dto.getCode().equals(oUserCode.get().getCode())) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
            return this.codeErrorForwarding(dto, model, errors);
        }

        return "redirect:/minha-conta/cliente";
    }

    /**
     * Valida e persiste o email na sessão.
     *
     * @param httpSession
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @param model
     * @return
     * @throws MessagingException
     */
    @PostMapping("/email")
    public String loginUserEmail(
            HttpSession httpSession,
            @Validated(LoginDTO.EmailGroupValidation.class) LoginDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws MessagingException {
        log.debug("ServiceBook: Login por Token.");
        if (errors.hasErrors()) {
            return this.emailErrorForwarding(dto, model, errors);
        }

        Optional<Individual> oUser = individualService.findByEmail(dto.getEmail());

        if (!oUser.isPresent()) {
            errors.rejectValue(null, "not-found", "O email não foi encontrado! Por favor, realize o cadastro!");
            return this.emailErrorForwarding(dto, model, errors);
        }

        log.debug("ServiceBook: Existe usuário.");

        Optional<UserCode> oUserCode = userCodeService.findByEmail(dto.getEmail());

        //se o código não for encontrado, gera um novo. Caso contrário, envia o código cadastrado para este email.
        if (!oUserCode.isPresent()) {
            String code = authenticationCodeGeneratorService.generateAuthenticationCode();

            UserCode userCode = new UserCode(dto.getEmail(), code);
            userCodeService.save(userCode);

            String tokenLink = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString() + "/login/login-by-token-email/" + code;
            quartzService.sendEmailWithAuthenticatationCode(dto.getEmail(), code, tokenLink, oUser.get().getName());

        } else {
            String tokenLink = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString() + "/login/login-by-token-email/" + oUserCode.get().getCode();
            quartzService.sendEmailWithAuthenticatationCode(dto.getEmail(), oUserCode.get().getCode(), tokenLink, oUser.get().getName());

        }

        //salva o email na sessão
        LoginDTO loginDTO = loginSessionUtil.getWizardState(httpSession, LoginDTO.class, WizardSessionUtil.KEY_LOGIN);
        loginDTO.setEmail(dto.getEmail());

        return "redirect:/login/token";
    }

    /**
     * Apresenta a página de digitação do token.
     * @return
     * @throws IOException
     */
    @GetMapping("/token")
    public String formToken() throws IOException {
        return "visitor/login-sucess";
    }

    /**
     * Valida o token, se estiver ok, faz o login.
     * @param httpSession
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @param model
     * @return
     */
    @PostMapping("/token")
    public String saveUserEmailCode(
            HttpSession httpSession,
            @Validated(LoginDTO.CodeGroupValidation.class) LoginDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        log.debug("ServiceBook: Confirmar Código.");

        if (errors.hasErrors()) {
            return this.codeErrorForwarding(dto, model, errors);
        }

        LoginDTO loginDTO = loginSessionUtil.getWizardState(httpSession, LoginDTO.class, WizardSessionUtil.KEY_LOGIN);

        Optional<UserCode> oUserCode = userCodeService.findByEmail(loginDTO.getEmail());

        if (!oUserCode.isPresent()) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
            return this.codeErrorForwarding(dto, model, errors);
        }

        if (!dto.getCode().equals(oUserCode.get().getCode())) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
            return this.codeErrorForwarding(dto, model, errors);
        }

        return "redirect:/minha-conta/cliente";
    }
}
