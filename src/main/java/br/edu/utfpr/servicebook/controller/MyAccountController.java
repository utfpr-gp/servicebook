package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.State;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
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

}

