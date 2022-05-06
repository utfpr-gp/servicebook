package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobContracted;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.JobContractedMapper;
import br.edu.utfpr.servicebook.service.CityService;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/profissionais")
public class ProfessionalController {

    @Autowired
    private IndividualService individualService;

    @Autowired
    private CityService cityService;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobContractedMapper jobContractedMapper;

    @Autowired
    private IndividualMapper individualMapper;

    @GetMapping
    protected ModelAndView showAll() throws Exception {
        ModelAndView mv = new ModelAndView("visitor/search-results");

        Optional<Individual> oIndividual = (individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));
        IndividualDTO individualDTO = individualMapper.toDto(oIndividual.get());
        mv.addObject("professional", individualDTO);
        List<City> cities = cityService.findAll();
        mv.addObject("cities", cities);

        List<JobContracted> jobContracted = jobContractedService.findByIdProfessional(individualDTO.getId());
        List<JobContractedDTO> JobContractedDTO = jobContracted.stream()
                .map(contracted -> jobContractedMapper.toResponseDto(contracted))
                .collect(Collectors.toList());
        List<Individual> professionals = individualService.findAll();
        Map<Long, List> professionalsExpertises = new HashMap<>();

        for (Individual professional : professionals) {
            List<ExpertiseDTO> expertisesDTO = individualService.getExpertises(professional);
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

        List<Individual> professionals = individualService.findDistinctByTermIgnoreCase(searchTerm);

        List<ProfessionalSearchItemDTO> professionalSearchItemDTOS = professionals.stream()
                .map(s -> individualMapper.toSearchItemDto(s, individualService.getExpertises(s)))
                .collect(Collectors.toList());

        mv.addObject("professionals", professionalSearchItemDTOS);
        mv.addObject("searchTerm", searchTerm);
        
        return mv;
    }

    @GetMapping("/detalhes/{id}")
    protected ModelAndView showProfessionalDetailsToVisitors(@PathVariable("id") Long id) throws Exception {
        ModelAndView mv = new ModelAndView("visitor/professional-details");

        Optional<Individual> oProfessional = individualService.findById(id);

        if(!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Profissional não encontrado.");
        }

        List<ExpertiseDTO> expertisesDTO = individualService.getExpertises(oProfessional.get());

        IndividualDTO professionalDTO = individualMapper.toDto(oProfessional.get());

        mv.addObject("professional", professionalDTO);
        mv.addObject("professionalExpertises", expertisesDTO);
        return mv;
    }
}
