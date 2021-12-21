package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.ExpertiseService;
import br.edu.utfpr.servicebook.service.ProfessionalExpertiseService;
import br.edu.utfpr.servicebook.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.util.*;


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
        Map<Long, List> professionalsExpertises = new HashMap<>();

        for (Professional professional : professionals) {
            List<ExpertiseDTO> expertisesDTO = professionalService.getExpertises(professional);
            professionalsExpertises.put(professional.getId(), expertisesDTO);
        }

        mv.addObject("professionals", professionals);
        mv.addObject("professionalsExpertises", professionalsExpertises);

        return mv;
    }

    @GetMapping(value = "/busca")
    protected ModelAndView showSearchResults(@RequestParam(value = "termo-da-busca") String searchTerm
                                             ) throws Exception {
        ModelAndView mv = new ModelAndView("visitor/search-results");

        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

        List<Professional> professionals = professionalService.findDistinctByTermIgnoreCase(searchTerm);
        Map<Long, List> professionalsExpertises = new HashMap<>();

        for (Professional professional : professionals) {
            List<ExpertiseDTO> expertisesDTO = professionalService.getExpertises(professional);
            professionalsExpertises.put(professional.getId(), expertisesDTO);
        }

        mv.addObject("professionals", professionals);
        mv.addObject("professionalsExpertises", professionalsExpertises);
        mv.addObject("searchTerm", searchTerm);
        
        return mv;
    }

    @GetMapping("/detalhes/{id}")
    protected ModelAndView showProfessionalDetailsToVisitors(@PathVariable("id") Long id) throws Exception {
        ModelAndView mv = new ModelAndView("visitor/professional-details");

        Optional<Professional> oProfessional = professionalService.findById(id);
        List<ExpertiseDTO> expertisesDTO = professionalService.getExpertises(oProfessional.get());

        if(!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado.");
        }

        ProfessionalDTO professionalDTO = professionalMapper.toDto(oProfessional.get());

        mv.addObject("professional", professionalDTO);
        mv.addObject("professionalExpertises", expertisesDTO);
        return mv;
    }
}
