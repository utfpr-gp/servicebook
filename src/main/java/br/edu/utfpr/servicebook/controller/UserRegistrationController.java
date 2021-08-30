package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.AddressDTO;
import br.edu.utfpr.servicebook.model.dto.UserCodeDTO;
import br.edu.utfpr.servicebook.model.dto.UserDTO;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import br.edu.utfpr.servicebook.model.mapper.UserCodeMapper;
import br.edu.utfpr.servicebook.model.mapper.UserMapper;
import br.edu.utfpr.servicebook.service.AuthenticationCodeGeneratorService;
import br.edu.utfpr.servicebook.service.EmailSenderService;
import br.edu.utfpr.servicebook.service.UserCodeService;
import br.edu.utfpr.servicebook.service.UserService;
import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@Slf4j
@RequestMapping("/cadastrar-se")
@SessionAttributes("wizard")
public class UserRegistrationController {

    @Autowired
    private WizardSessionUtil<UserDTO> wizardSessionUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCodeService userCodeService;

    @Autowired
    private UserCodeMapper userCodeMapper;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private AuthenticationCodeGeneratorService authenticationCodeGeneratorService;

    @GetMapping
    public String showUserRegistrationWizard(
            @RequestParam(value = "passo", required = false, defaultValue = "1") Long step,
            HttpSession httpSession,
            Model model
    ) {

        if (step < 1 || step > 7) {
            step = 1L;
        }

        UserDTO dto = wizardSessionUtil.getWizardState(httpSession, UserDTO.class);
        model.addAttribute("dto", dto);

        return "visitor/user-registration/wizard-step-0" + step;
    }

    @PostMapping("/passo-1")
    public String saveUserEmail(
            HttpSession httpSession,
            @Validated(UserDTO.RequestUserEmailInfoGroupValidation.class) UserDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws MessagingException {

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-01";
        }

        if (dto.getEmail() != null) {
            Optional<User> oUser = userService.findByEmail(dto.getEmail());

            if (oUser.isPresent()) {
                errors.rejectValue("email", "error.dto", "Email já cadastrado! Por favor, insira um email não cadastrado.");
            }
        }

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-01";
        }

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

        UserDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, UserDTO.class);
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
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-02";
        }

        UserDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, UserDTO.class);
        Optional<UserCode> oUserCode = null;

        if (dto.getCode() != null) {
            oUserCode = userCodeService.findByEmail(sessionDTO.getEmail());

            if (!oUserCode.isPresent()) {
                errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de verificação de email.");
            }
        }

        if (dto.getCode().equals(oUserCode.get().getCode())) {
            sessionDTO.setEmailVerified(true);
        } else {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de verificação de email.");
        }

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-02";
        }

        redirectAttributes.addFlashAttribute("msg", "Email verificado com sucesso!");

        return "redirect:/cadastrar-se?passo=3";
    }

    @PostMapping("/passo-3")
    public String saveUserPhone(
            HttpSession httpSession,
            @Validated(UserDTO.RequestUserPhoneInfoGroupValidation.class) UserDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-03";
        }

        if (dto.getPhoneNumber() != null) {
            Optional<User> oUser = userService.findByPhoneNumber(dto.getPhoneNumber());

            if (oUser.isPresent()) {
                errors.rejectValue("phoneNumber", "error.dto", "Telefone já cadastrado! Por favor, insira um número de telefone não cadastrado.");
            }
        }

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-03";
        }

        ///// Enviar código de verificação para telefone.

        UserDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, UserDTO.class);
        sessionDTO.setPhoneNumber(dto.getPhoneNumber());

        return "redirect:/cadastrar-se?passo=4";
    }

    @PostMapping("/passo-4")
    public String saveUserPhoneCode(
            HttpSession httpSession,
            @Validated(UserCodeDTO.RequestUserCodeInfoGroupValidation.class) UserCodeDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-04";
        }

        UserDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, UserDTO.class);
        Optional<UserCode> oUserCode = null;

        if (dto.getCode() != null) {
            oUserCode = userCodeService.findByEmail(sessionDTO.getEmail());

            if (!oUserCode.isPresent()) {
                errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de verificação de telefone.");
            }
        }

        if (dto.getCode().equals(oUserCode.get().getCode())) {
            sessionDTO.setEmailVerified(true);
        } else {
            errors.rejectValue("code", "error.dto", "Código inválido! Por favor, insira o código de verificação de telefone.");
        }

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-04";
        }

        redirectAttributes.addFlashAttribute("msg", "Telefone verificado com sucesso!");

        return "redirect:/cadastrar-se?passo=5";
    }

    @PostMapping("/passo-5")
    public String saveUserNameAndCPF(
            HttpSession httpSession,
            @Validated({
                    UserDTO.RequestUserNameInfoGroupValidation.class,
                    UserDTO.RequestUserCPFInfoGroupValidation.class
            }) UserDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-05";
        }

        Optional<User> oUser = null;

        if (dto.getName() != null) {
            oUser = userService.findByName(dto.getName());

            if (oUser.isPresent()) {
                errors.rejectValue("name", "error.dto", "Nome já cadastrado! Por favor, insira um nome não cadastrado.");
            }
        }

        if (dto.getCpf() != null) {
            oUser = userService.findyByCpf(dto.getCpf());

            if (oUser.isPresent()) {
                errors.rejectValue("cpf", "error.dto", "CPF já cadastrado! Por favor, insira um CPF não cadastrado.");
            }
        }

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-05";
        }

        UserDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, UserDTO.class);
        sessionDTO.setName(dto.getName());
        sessionDTO.setCpf(dto.getCpf());

        return "redirect:/cadastrar-se?passo=6";
    }

    @PostMapping("/passo-6")
    public String saveUserAddress(
            HttpSession httpSession,
            @Validated({
                    AddressDTO.RequestAddressStreetInfoGroupValidation.class,
                    AddressDTO.RequestAddressNumberInfoGroupValidation.class,
                    AddressDTO.RequestAddressPostalCodeInfoGroupValidation.class,
                    AddressDTO.RequestAddressNeighborhoodInfoGroupValidation.class,
                    AddressDTO.RequestAddressCityInfoGroupValidation.class,
                    AddressDTO.RequestAddressStateInfoGroupValidation.class
            }) AddressDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) {

        if (errors.hasErrors()) {
            model.addAttribute("dto", dto);
            model.addAttribute("errors", errors.getAllErrors());

            return "visitor/user-registration/wizard-step-06";
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(dto.getStreet());
        addressDTO.setNumber(dto.getNumber());
        addressDTO.setPostalCode(dto.getPostalCode());
        addressDTO.setNeighborhood(dto.getNeighborhood());

        ///// Adicionar cidade e estado para addressDTO.

        UserDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, UserDTO.class);
        sessionDTO.setAddress(addressDTO);

        return "redirect:/cadastrar-se?passo=7";
    }

    @PostMapping("/passo-7")
    public String saveUser(
            HttpSession httpSession,
            UserDTO dto,
            RedirectAttributes redirectAttributes,
            Model model,
            SessionStatus status
    ) {

        UserDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, UserDTO.class);
        sessionDTO.setProfileVerified(true);

        User user = userMapper.toEntity(sessionDTO);
        userService.save(user);

        redirectAttributes.addFlashAttribute("msg", "Usuário cadastrado com sucesso! Realize o login no Servicebook!");
        status.setComplete();

        return "redirect:/entrar";
    }

}
