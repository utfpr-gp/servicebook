package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.PasswordUtil;
import br.edu.utfpr.servicebook.util.PhoneNumberVerificationService;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Map;

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
    private CompanyService companyService;

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

    @Autowired
    PhoneNumberVerificationService phoneNumberVerificationService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ModerateService moderateService;

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

        if(!oUser.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado pelo id informado.");
        }

        User user = oUser.get();
        user.setDescription(request.getParameter("description"));
        this.userService.save(user);

        return "redirect:/minha-conta/meu-anuncio/{id}";
    }

    //Métodos para edição de email

    /**
     * Apresenta a tela de email do usuário.
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/edita-email/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showFormEditEmail(@PathVariable Long id) throws IOException {

        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());

        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não tem permissão para alterar este email.");
        }

        UserDTO userDTO = userMapper.toDto(userAuthenticated);

        ModelAndView mv = new ModelAndView("professional/account/my-email");
        mv.addObject("user", userDTO);

        return mv;
    }

    /**
     * Salva o email do usuário.
     * @param id
     * @param userDTO
     * @param errors
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PatchMapping("/edita-email/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String saveEmail(
            @PathVariable Long id,
            @Validated(UserDTO.RequestUserEmailInfoGroupValidation.class) UserDTO userDTO,
            BindingResult errors,
            RedirectAttributes redirectAttributes)
            throws IOException {

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/edita-email/" + id;
        }

        //busca o usuário pelo id passado na URL
        Optional<User> oUser = this.userService.findById(id);

        if(!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        //busca o usuário autenticado e verifica se o id passado na URL é o mesmo do usuário autenticado
        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não tem permissão para alterar esta senha.");
        }

        //verifica se o email já está cadastrado para outro usuário
        String email = userDTO.getEmail();
        Optional<User> oOtherUser = userService.findByEmail(email);
        if (oOtherUser.isPresent()){
            redirectAttributes.addFlashAttribute("msgError", "Email já cadastrado! Por favor, insira um email não cadastrado!");
            return "redirect:/minha-conta/edita-email/" + id;
        }

        //Verifica se o email foi verificado
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

        userAuthenticated.setEmail(email);
        userAuthenticated.setEmailVerified(false);
        this.userService.save(userAuthenticated);

        return "redirect:/minha-conta/valida-email/" + id;
    }

    /**
     * Apresenta a tela de validação de email do usuário.
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/valida-email/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showFormValidateEmail(@PathVariable Long id) throws IOException {

        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        UserDTO userDTO = userMapper.toDto(oUser.get());

        System.out.println("Valida com email: " + userDTO.getEmail());

        ModelAndView mv = new ModelAndView("professional/account/validate-my-email");
        mv.addObject("user", userDTO);

        return mv;
    }

    /**
     * Salva o código de validação de email do usuário.
     * @param id
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/valida-email/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String saveUserEmailCode(
            @PathVariable Long id,
            @Validated UserCodeDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes
    ) {

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("msgError", "Houve um erro na verificação do código, verifique se digitou corretamente!");
            return "redirect:/minha-conta/valida-email/" + id;
        }

        Optional<User> oUser = this.userService.findById(id);

        if(!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        String email = dto.getEmail();

        Optional<UserCode> oUserCode = userCodeService.findByEmail(email);

        if (!oUserCode.isPresent()) {
            redirectAttributes.addFlashAttribute("msgError", "Código inválido! Por favor, insira o código de autenticação.");
            return "redirect:/minha-conta/valida-email/" + id;
        }

        if (!dto.getCode().equals(oUserCode.get().getCode())) {
            redirectAttributes.addFlashAttribute("msgError", "Código inválido! Por favor, insira o código de autenticação.");
            return "redirect:/minha-conta/valida-email/" + id;
        }

        User user = oUser.get();
        user.setEmailVerified(true);
        this.userService.save(user);

        redirectAttributes.addFlashAttribute("msg", "Email salvo com sucesso!");

        return "redirect:/logout";
    }

    @GetMapping("/reenvia-codigo-verificacao-email/{id}")
    @ResponseBody
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String resendEmailCode(
            @PathVariable Long id,
            @RequestParam("email") String email
            )
            throws IOException {

        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

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

        return "";
    }

    //Métodos para edição de telefone

    /**
     * Apresenta a tela para editar o telefone do usuário.
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/edita-telefone/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showFormEditContactPhone(@PathVariable Long id) throws IOException {

        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não ter permissão para atualizar esse telefone.");
        }

        UserDTO userDTO = userMapper.toDto(userAuthenticated);

        ModelAndView mv = new ModelAndView("professional/account/my-contact-phone");
        mv.addObject("user", userDTO);

        return mv;
    }

    /**
     * Salva o telefone do usuário.
     * @param id
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PatchMapping("/edita-telefone/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String savePhoneNumber(
            @PathVariable Long id,
            @Validated(UserDTO.RequestUserPhoneInfoGroupValidation.class) UserDTO userDTO,
            BindingResult errors,
            RedirectAttributes redirectAttributes)
            throws IOException {

        //Faz a validação dos campos
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/edita-telefone/" + id;
        }

        //busca o usuário pelo id passado na URL
        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        //busca o usuário autenticado e verifica se o id passado na URL é o mesmo do usuário autenticado
        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não ter permissão para atualizar esse telefone.");
        }

        //verifica se mudou o número para não precisa validar o código novamente
        String phoneNumber = userDTO.getPhoneNumber();
        if(phoneNumber.equals(userAuthenticated.getPhoneNumber())){
            errors.rejectValue("phoneNumber","error.dto.phoneNumber.not-change","O número de telefone não foi alterado");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/edita-telefone/" + id;
        }

        //verifica se o telefone já está cadastrado para outro usuário
        Optional<User> oOtherUser = userService.findByPhoneNumber(phoneNumber);
        if(oOtherUser.isPresent() && oOtherUser.get().getId() != userAuthenticated.getId()){
            errors.rejectValue("phoneNumber","error.dto.phoneNumber.duplicate","Este telefone já está cadastrado para outro usuário.");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/edita-telefone/" + id;
        }

        userAuthenticated.setPhoneNumber(phoneNumber);
        userAuthenticated.setPhoneVerified(false);
        this.userService.save(userAuthenticated);

        //envia o SMS para o telefone cadastrado
        phoneNumberVerificationService.sendSMSToVerification(phoneNumber);

        redirectAttributes.addFlashAttribute("msg", "Telefone salvo com sucesso!");

        return "redirect:/minha-conta/valida-telefone/" + id;
    }

    /**
     * Reenvia o código de verificação para o telefone do usuário.
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/reenvia-codigo-verificacao/{id}")
    @ResponseBody
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String resendPhoneCode(
            @PathVariable Long id)
            throws IOException {

        //busca o usuário pelo id passado na URL
        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        //busca o usuário autenticado e verifica se o id passado na URL é o mesmo do usuário autenticado
        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não ter permissão para atualizar esse telefone.");
        }

        //envia o SMS para o telefone cadastrado
        phoneNumberVerificationService.sendSMSToVerification(userAuthenticated.getPhoneNumber());

        return "";
    }

    /**
     * Apresenta a tela para validar o telefone do usuário.
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/valida-telefone/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showFormValidateContactPhone(@PathVariable Long id) throws IOException {

        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        //verifica se o usuário está logado e se é o mesmo usuário que está tentando editar o telefone
        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não ter permissão para atualizar esse telefone.");
        }

        //apresenta a tela para validar o telefone
        UserDTO userDTO = userMapper.toDto(userAuthenticated);
        ModelAndView mv = new ModelAndView("professional/account/validate-my-contact-phone");
        mv.addObject("user", userDTO);

        return mv;
    }

    /**
     * Salva o código de validação do telefone do usuário.
     * @param id
     * @param dto
     * @param errors
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/valida-telefone/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String saveUserPhoneCode(
            @PathVariable Long id,
            @Validated UserCodeDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes
    ) {

        //Faz a validação dos campos
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/valida-telefone/" + id;
        }

        //busca o usuário pelo id passado na URL
        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        //busca o usuário autenticado e verifica se o id passado na URL é o mesmo do usuário autenticado
        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não ter permissão para atualizar esse telefone.");
        }

        String phoneNumber = userAuthenticated.getPhoneNumber();

        //verifica se o usuário digitou corretamente o código enviado por SMS
        boolean isVerified = false;
        try {
            isVerified = phoneNumberVerificationService.verify(dto.getCode(), phoneNumber);
        } catch (Exception e) {
            errors.rejectValue(null, "phone-verification-fail", "Ocorreu um erro ao validar o telefone. Tente novamente.");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/valida-telefone/" + id;
        }

        //o telefone não foi verificado, o usuário digitou o código errado
        if (!isVerified) {
            errors.rejectValue(null, "phone-not-verified", "O código digitado está incorreto.");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/valida-telefone/" + id;
        }

        //o telefone foi verificado com sucesso
        userAuthenticated.setPhoneVerified(true);
        userService.save(userAuthenticated);

        redirectAttributes.addFlashAttribute("msg", "O telefone foi verificado com sucesso!");

        return "redirect:/minha-conta/edita-telefone/" + id;
    }

    //edita senha

    /**
     * Apresenta a tela de editar senha do usuário.
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/edita-senha/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showFormEditPassword(@PathVariable Long id) throws IOException {

        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não tem permissão para alterar esta senha.");
        }

        UserDTO userDTO = userMapper.toDto(userAuthenticated);

        ModelAndView mv = new ModelAndView("professional/account/my-password");
        mv.addObject("user", userDTO);

        return mv;
    }

    @PatchMapping("/edita-senha/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String savePassword(
            @PathVariable Long id,
            @Validated(UserDTO.RequestUserPasswordInfoGroupValidation.class) UserDTO userDTO,
            BindingResult errors,
            RedirectAttributes redirectAttributes)
            throws IOException {

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/edita-senha/" + id;
        }

        //verifica se a senha e contrasenha são iguais
        if(!userDTO.getPassword().equals(userDTO.getRepassword())){
            errors.rejectValue("password", "error.dto", "As senhas não correspondem. Por favor, tente novamente.");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/edita-senha/" + id;
        }

        Optional<User> oUser = this.userService.findById(id);

        if(!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não tem permissão para alterar esta senha.");
        }

        //verifica se realmente está alterando a senha
        if(PasswordUtil.isSamePassword(userDTO.getPassword(), userAuthenticated.getPassword())){
            errors.rejectValue("password", "error.dto", "A senha informada é igual a senha atual. Por favor, tente novamente.");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/edita-senha/" + id;
        }

        userAuthenticated.setPassword(userDTO.getPassword());
        this.userService.save(userAuthenticated);

        redirectAttributes.addFlashAttribute("msg", "Senha salva com sucesso.");

        return "redirect:/minha-conta/edita-senha/" + id;
    }

    /**
     * Altera a foto do usuário.
     * @return
     * @throws IOException
     */
    @PostMapping("/enviar-foto")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String uploadPhoto(
            @RequestParam("fileUpload") MultipartFile file,
            RedirectAttributes redirectAttributes
    ) {
        Optional<User> oUser = userService.findByEmail(authentication.getEmail());
        if (!oUser.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("msgError", "Nenhuma foto foi selecionada!");
            return "redirect:/minha-conta/perfil";
        }


        if(!isValidateImage(file)) {
            redirectAttributes.addFlashAttribute("msgError", "Você não enviou uma imagem em formato valido. Por gentileza selecione imagens em png,jpg ou jpeg");
            return "redirect:/minha-conta/perfil";
        }

        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            String imageUrl = (String) uploadResult.get("url");

            if(moderateService.isNsfwImage(imageUrl)){
                String publicId = (String) uploadResult.get("public_id");
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                redirectAttributes.addFlashAttribute("msgError", "A imagem enviada contém conteúdo impróprio. Por favor, envie outra foto.");
                return "redirect:/minha-conta/perfil";
            }

            User user = oUser.get();
            user.setProfilePicture(imageUrl);
            userService.save(user);

            redirectAttributes.addFlashAttribute("msg", "Foto enviada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msgError", "Ocorreu um erro ao enviar a foto.");
        }

        return "redirect:/minha-conta/perfil";
    }

    /**
     * Verifica se a imagem é valida.
     * @param image
     * @return
     */
    public boolean isValidateImage(MultipartFile image){
        List<String> contentTypes = Arrays.asList("image/png", "image/jpg", "image/svg");

        for(int i = 0; i < contentTypes.size(); i++){
            if(image.getContentType().toLowerCase().startsWith(contentTypes.get(i))){
                return true;
            }
        }

        return false;
    }

    //edição de informações pessoais do individuo e empresa

    /**
     * Mostra a tela de edição das informações do indivíduo e empresa
     *
     */
    @GetMapping("/informacoes-pessoais/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showMyPersonalData(@PathVariable Long id) throws IOException {

        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());

        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não tem permissão para atualizar essas informações pessoais.");
        }

        UserDTO userDTO = userMapper.toDto(userAuthenticated);

        ModelAndView mv = new ModelAndView("professional/account/my-personal-info");
        mv.addObject("userDTO", userDTO);
        return mv;
    }

    /**
     * Atualiza as informações pessoais do indivíduo e empresa
     * @param userDTO
     * @param errors
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PatchMapping("/cadastra-informacoes-pessoais/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String updateMyPersonalData(@PathVariable Long id,
            @Validated(UserDTO.RequestUpdatePersonalInfo.class) UserDTO userDTO,
            BindingResult errors,
            RedirectAttributes redirectAttributes
    ) throws IOException {

        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/informacoes-pessoais/" + id;
        }

        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado.");
        }

        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        User userAuthenticated = oUserAuthenticated.get();

        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não tem permissão para atualizar essas informações pessoais.");
        }

        if (userDTO.getCnpj() == null) {
            Optional<Individual> oInvididual = this.individualService.findById(id);

            //verifica se o CPF já está cadastrado para outro usuário
            Optional<Individual> oOtherUser = individualService.findByCpf(userDTO.getCpf());

            if(oOtherUser.isPresent() && oOtherUser.get().getId() != userAuthenticated.getId()) {
                errors.rejectValue(
                        "cpf",
                        "error.dto.cpf.duplicate",
                        "Este CPF já está cadastrado para outro usuário."
                );
                redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
                return "redirect:/minha-conta/informacoes-pessoais/" + id;
            }

            Individual individual = oInvididual.get();
            individual.setName(userDTO.getName());
            individual.setCpf(userDTO.getCpf());
            individual.setBirthDate(userDTO.getBirthDate());

            this.individualService.save(individual);
        } else {
            Optional<Company> oCompany = this.companyService.findById(id);

            //verifica se o CNPJ já está cadastrado para outro usuário
            Optional<Company> oOtherCompany = this.companyService.findByCnpj(userDTO.getCnpj());

            if(oOtherCompany.isPresent() && oOtherCompany.get().getId() != userAuthenticated.getId()) {
                errors.rejectValue(
                        "cnpj",
                        "error.dto.cnpj.duplicate",
                        "Este CNPJ já está cadastrado para outro usuário."
                );
                redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());

                return "redirect:/minha-conta/informacoes-pessoais/" + id;
            }

            Company company = oCompany.get();
            company.setName(userDTO.getName());
            company.setCnpj(userDTO.getCnpj());

            this.companyService.save(company);
        }

        redirectAttributes.addFlashAttribute("msg", "Informações pessoais atualizadas com sucesso!");
        return "redirect:/minha-conta/informacoes-pessoais/" + id;
    }
}




