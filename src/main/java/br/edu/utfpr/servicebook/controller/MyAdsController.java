package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.IndividualDTO;
import br.edu.utfpr.servicebook.model.dto.JobContractedDTO;
import br.edu.utfpr.servicebook.model.dto.ServiceDTO;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.ServiceMapper;
import br.edu.utfpr.servicebook.service.ExpertiseService;
import br.edu.utfpr.servicebook.service.ServiceService;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/profissional/meus-anuncios")
@Controller
public class MyAdsController {
    public static final Logger log = LoggerFactory.getLogger(ProfessionalHomeController.class);

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @GetMapping
    @PermitAll
    protected ModelAndView showMyAds() throws Exception {
        ModelAndView mv = new ModelAndView("professional/my-ads");

        //paginação de serviços
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Service> servicePage = serviceService.findAll(pageRequest);
        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());

        mv.addObject("services", serviceDTOS);

        return mv;
    }

    @GetMapping("/novo")
    @PermitAll
    protected ModelAndView registerMyAds() throws Exception {
        ModelAndView mv = new ModelAndView("professional/my-ads-register");

        //paginação de serviços
        PageRequest pageRequest = PageRequest.of(0, 5);

        List<Expertise> expertises = expertiseService.findAll();
        List<ExpertiseDTO> expertiseDTOs = expertises.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertiseDTOs);

        Page<Service> servicePage = serviceService.findAll(pageRequest);
        List<ServiceDTO> serviceDTOS = servicePage.stream()
                .map(s -> serviceMapper.toDto(s))
                .collect(Collectors.toList());
        mv.addObject("services", serviceDTOS);


        return mv;
    }
}
