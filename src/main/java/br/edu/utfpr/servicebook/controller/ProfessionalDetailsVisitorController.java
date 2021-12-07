package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
import br.edu.utfpr.servicebook.model.dto.UserDTO;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.ProfessionalService;
import br.edu.utfpr.servicebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/detalhes-do-profissional")
public class ProfessionalDetailsVisitorController {

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private ProfessionalMapper professionalMapper;


    @GetMapping("/{id}")
    protected ModelAndView showProfessionalDetails(@PathVariable("id") Long id) throws Exception {
        ModelAndView mv = new ModelAndView("visitor/professional-details");

        Optional<Professional> professional = professionalService.findById(id);

        ProfessionalDTO professionalDTO = professionalMapper.toDto(professional.get());

        mv.addObject("professional", professionalDTO);
        return mv;
    }
}
