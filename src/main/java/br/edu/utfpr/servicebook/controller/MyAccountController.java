package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import br.edu.utfpr.servicebook.util.UserWizardUtil;
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

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private UserWizardUtil userWizardUtil;

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private StateService stateService;
    @Autowired
    private StateMapper stateMapper;

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

    //edita endereço

    /**
     * Apresenta a tela de endereço do usuário.
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/meu-endereco/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String showMyAddress(@PathVariable Long id, Model model) throws IOException {

        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("Usuário não encontrado pelo id informado.");
        }

        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        if(!oUserAuthenticated.isPresent()){
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        User userAuthenticated = oUserAuthenticated.get();

        //verifica se o usuario autenticado é o mesmo que está tentando atualizar as informações
        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não tem permissão para atualizar essas informações");
        }

        Address address = userAuthenticated.getAddress();
        City city = address.getCity();
        State state = city.getState();

        UserDTO userDTO = userMapper.toDto(oUser.get());

        List<City> cities = this.cityService.findAll();
        List<State> states = this.stateService.findAll();

        model.addAttribute("professional", userDTO);
        model.addAttribute("cities", cities);
        model.addAttribute("states", states);

        return "professional/account/my-address";
    }


    /**
     * @param id
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PatchMapping("/meu-endereco/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String saveAddress(
            @PathVariable Long id,
            @Validated AddressUpdateDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes)
            throws IOException {

        Optional<User> oUser = this.userService.findById(id);

        if (!oUser.isPresent()) {
            throw new EntityNotFoundException("Usuário não encontrado pelo id informado.");
        }

        Optional<User> oUserAuthenticated = this.userService.findByEmail(authentication.getEmail());
        if(!oUserAuthenticated.isPresent()){
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        User userAuthenticated = oUserAuthenticated.get();

        //verifica se o usuario autenticado é o mesmo que está tentando atualizar as informações
        if (id != userAuthenticated.getId()) {
            throw new AuthenticationCredentialsNotFoundException("Você não tem permissão para atualizar essas informações");
        }

        //verifica se o estado está cadastrado
        if(!stateService.findById(dto.getState()).isPresent()){
            errors.rejectValue("state", "error.dto", "Estado não cadastrado! Por favor, insira um estado cadastrado.");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/meu-endereco/{id}";
        }

        //verifica se a cidade existe para o estado informado
        if(!cityService.findByIdAndState_Id(dto.getCity(), dto.getState()).isPresent()){
            errors.rejectValue("city", "error.dto", "Cidade não cadastrada para o estado informado! Por favor, insira uma cidade cadastrada.");
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/meu-endereco/{id}";
        }

        try {
            //atualiza o endereço do usuário
            Address address = userAuthenticated.getAddress();
            address.setCity(cityService.findById(dto.getCity()).get());
            address.setStreet(dto.getStreet().trim());
            address.setNumber(dto.getNumber().trim());
            address.setPostalCode(dto.getPostalCode().trim());
            address.setNeighborhood(dto.getNeighborhood().trim());

            userAuthenticated.setAddress(address);

            this.userService.save(userAuthenticated);
            redirectAttributes.addFlashAttribute("msg", "Endereço editado com sucesso");
        } catch (Exception exception) {
            errors.rejectValue(null, "not-found", "Erro ao editar endereço: " + exception.getMessage());
            redirectAttributes.addFlashAttribute("errors", errors.getAllErrors());
            return "redirect:/minha-conta/meu-endereco/{id}";
        }

        return "redirect:/minha-conta/meu-endereco/{id}";
    }

    /**
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

    @PostMapping("/validar-email/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String saveUserEmailCode(
            @PathVariable Long id,
            @Validated UserCodeDTO dto,
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
}

