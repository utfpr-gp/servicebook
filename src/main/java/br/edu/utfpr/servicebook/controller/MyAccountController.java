package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.State;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    private IndividualMapper individualMapper;

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

    @GetMapping
    public String home(HttpServletRequest request) {
        return "redirect:/minha-conta/cliente";
    }

    @GetMapping("/perfil")
    public ModelAndView editProfile() throws IOException {
        Optional<Individual> oIndividual = (individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));
        if (!oIndividual.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        IndividualDTO professionalDTO = individualMapper.toDto(oIndividual.get());

        Optional<City> oCity = cityService.findById(oIndividual.get().getAddress().getCity().getId());
        if (!oCity.isPresent()) {
            throw new EntityNotFoundException("Cidade não foi encontrada pelo id informado.");
        }
        CityMinDTO cityMinDTO = cityMapper.toMinDto(oCity.get());

        ModelAndView mv = new ModelAndView("professional/edit-account");
        mv.addObject("professional", professionalDTO);
        mv.addObject("city", cityMinDTO);

        return mv;
    }

    @GetMapping("/meu-anuncio/{id}")
    public ModelAndView showMyAd(@PathVariable Long id) throws IOException {
        Optional<Individual> oProfessional = this.individualService.findById(id);

        if (!oProfessional.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        IndividualMinDTO individualMinDTO = individualMapper.toMinDto(oProfessional.get());

        ModelAndView mv = new ModelAndView("professional/account/my-ad");

        mv.addObject("professional", individualMinDTO);

        return mv;
    }

    @PatchMapping("/cadastra-descricao/{id}")
    public String updateDescriptionProfessional(@PathVariable Long id, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        Optional<Individual> oProfessional = this.individualService.findById(id);

        if(!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado pelo id informado.");
        }

        Individual professional = oProfessional.get();
        String description = request.getParameter("description");
        professional.setDescription(description);
        this.individualService.save(professional);

        return "redirect:/minha-conta/meu-anuncio/{id}";
    }

    @GetMapping("/meu-email/{id}")
    public ModelAndView showMyEmail(@PathVariable Long id) throws IOException {
        Optional<Individual> oProfessional = this.individualService.findById(id);

        if (!oProfessional.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        IndividualDTO individualDTO = individualMapper.toDto(oProfessional.get());

        ModelAndView mv = new ModelAndView("professional/account/my-email");

        mv.addObject("professional", individualDTO);

        return mv;
    }

    @PostMapping("/salvar-email/{id}")
    public String saveEmail(
            @PathVariable Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes)
            throws IOException {

        Optional<Individual> oProfessional = this.individualService.findById(id);

        if(!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado pelas informações fornecidas.");
        }

        String email = request.getParameter("email");
        Optional<Individual> oUser = individualService.findByEmail(email);
        Individual professional = oProfessional.get();

        if (oUser.isPresent() && !oUser.get().getEmail().equals(professional.getEmail())) {
            redirectAttributes.addFlashAttribute("msgError", "Email já cadastrado! Por favor, insira um email não cadastrado!");
            return "redirect:/minha-conta/meu-email/{id}";
        }

        professional.setEmail(email);
        professional.setEmailVerified(false);
        this.individualService.save(professional);

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

        String tokenLink = ServletUriComponentsBuilder.fromCurrentContextPath().build().toString() + "/login/login-by-token-email/" + actualCode;
        quartzService.sendEmailToConfirmationCode(email, actualCode, tokenLink);

        redirectAttributes.addFlashAttribute("msg", "Email salvo com sucesso!");
        return "redirect:/minha-conta/meu-email/{id}";
    }

    @PostMapping("/validar-email/{id}")
    public String saveUserEmailCode(
            @PathVariable Long id,
            @Validated(UserCodeDTO.RequestUserCodeInfoGroupValidation.class) UserCodeDTO dto,
            RedirectAttributes redirectAttributes
    ) {

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

