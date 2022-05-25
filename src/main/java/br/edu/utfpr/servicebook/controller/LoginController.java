package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.AddressDTO;
import br.edu.utfpr.servicebook.model.dto.IndividualDTO;
import br.edu.utfpr.servicebook.model.dto.LoginDTO;
import br.edu.utfpr.servicebook.model.dto.UserCodeDTO;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.UserCodeMapper;
import br.edu.utfpr.servicebook.service.AuthenticationCodeGeneratorService;
import br.edu.utfpr.servicebook.service.EmailSenderService;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.service.UserCodeService;
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

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@RequestMapping("/login")
@SessionAttributes("loginUser")
@Controller
public class LoginController {
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private WizardSessionUtil<LoginDTO> loginSessionUtil;

    @Autowired
    private WizardSessionUtil<UserCodeDTO> codeSessionUtil;

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
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public ModelAndView showLogin() {
        log.debug("Servicebook: Login.");

        ModelAndView mv = new ModelAndView("visitor/login");

        return mv;
    }
    private String userRegistrationErrorForwarding(LoginDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/login";
    }
    private String userCodeErrorForwarding(UserCodeDTO dto, Model model, BindingResult errors) {
        model.addAttribute("dto", dto);
        model.addAttribute("errors", errors.getAllErrors());

        return "visitor/login";
    }
    @PostMapping("/email")
    public String loginUserEmail(
            HttpSession httpSession,
            @Validated(LoginDTO.RequestUserEmailInfoGroupValidation.class) LoginDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws MessagingException {
        log.debug("ServiceBook: Login por Token.");
        if (errors.hasErrors()) {
            return this.userRegistrationErrorForwarding(dto, model, errors);
        }
        Optional<Individual> oUser = individualService.findByEmail(dto.getEmail());

        if (oUser.isPresent()) {
            log.debug("ServiceBook: Exite usuário.");

            Optional<UserCode> oUserCode = userCodeService.findByEmail(dto.getEmail());

            if (!oUserCode.isPresent()) {
                String code = authenticationCodeGeneratorService.generateAuthenticationCode();

                UserCodeDTO userCodeDTO = new UserCodeDTO(dto.getEmail(), code);
                UserCode userCode = userCodeMapper.toEntity(userCodeDTO);

                userCodeService.save(userCode);
                emailSenderService.sendEmailToServer(dto.getEmail(), "Servicebook: Código de autenticação.", "Código de autenticação:" + "\n\n\n" + code);
            } else {
                emailSenderService.sendEmailToServer(dto.getEmail(), "Servicebook: Código de autenticação.", "Código de autenticação:" + "\n\n\n" + oUserCode.get().getCode());
            }
        }

        LoginDTO loginDTO = loginSessionUtil.getWizardState(httpSession, LoginDTO.class, WizardSessionUtil.KEY_LOGIN);
        loginDTO.setEmail(dto.getEmail());

        return "redirect:/login/token";
    }
    @GetMapping("/token")
    public String formToken() throws IOException {
        return "visitor/login-sucess";
    }

    @PostMapping("/token")
    public String saveUserEmailCode(
            HttpSession httpSession,
            @Validated(UserCodeDTO.RequestUserCodeInfoGroupValidation.class) UserCodeDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        log.debug("ServiceBook: Confirmar Código.");
        if (errors.hasErrors()) {
            return this.userCodeErrorForwarding( dto, model, errors);
        }
        LoginDTO loginDTO = loginSessionUtil.getWizardState(httpSession, LoginDTO.class, WizardSessionUtil.KEY_LOGIN);
        Optional<UserCode> oUserCode = userCodeService.findByEmail(loginDTO.getEmail());

        if (!oUserCode.isPresent()) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
        }

        if (!dto.getCode().equals(oUserCode.get().getCode())) {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de autenticação.");
        }
        return "redirect:/minha-conta/cliente";
    }
}
