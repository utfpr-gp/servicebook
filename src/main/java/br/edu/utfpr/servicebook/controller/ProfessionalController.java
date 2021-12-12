package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/profissionais")
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private ProfessionalMapper professionalMapper;

    @Autowired
    private CityService cityService;

    @GetMapping
    protected ModelAndView showAll() throws Exception {
        ModelAndView mv = new ModelAndView("visitor/search-results");

        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

        List<Professional> professionals = professionalService.findAll();
        mv.addObject("professionals", professionals);

        return mv;
    }

    @GetMapping(value = "/busca")
    protected ModelAndView showSearchResults(@RequestParam(value = "termo-da-busca") String searchTerm
                                             ) throws Exception {
        ModelAndView mv = new ModelAndView("visitor/search-results");

        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

        List<Professional> professionals = professionalService.findDistinctByTermIgnoreCase(searchTerm);
        mv.addObject("professionals", professionals);
        mv.addObject("searchTerm", searchTerm);
        
        return mv;
    }

    @GetMapping("/detalhes/{id}")
    protected ModelAndView showProfessionalDetailsToVisitors(@PathVariable("id") Long id) throws Exception {
        ModelAndView mv = new ModelAndView("visitor/professional-details");

        Optional<Professional> oProfessional = professionalService.findById(id);

        if(!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado.");
        }

        ProfessionalDTO professionalDTO = professionalMapper.toDto(oProfessional.get());

        mv.addObject("professional", professionalDTO);
        return mv;
    }
}
