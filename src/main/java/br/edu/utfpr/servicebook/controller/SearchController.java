package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.UserDTO;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.entity.User;
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


@Controller
@RequestMapping("/buscar")
public class SearchController {

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private CityService cityService;

    @PostMapping
    protected ModelAndView showSearchResults(
            HttpSession httpSession,
            @Validated(UserDTO.RequestUserEmailInfoGroupValidation.class) UserDTO dto,
            BindingResult errors,
            RedirectAttributes redirectAttributes,
            Model model
    ) throws Exception {
        ModelAndView mv = new ModelAndView("visitor/search-results");

        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

        List<Professional> professionals = professionalService.findAllByNameIgnoreCase(dto.getName());
        mv.addObject("professionals", professionals);

        return mv;
    }
}
