package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.JobContractedDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalDTO;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobContracted;
import br.edu.utfpr.servicebook.model.mapper.JobContractedMapper;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.service.ProfessionalService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/profissionais")
@Controller
public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private ProfessionalMapper professionaMapper;

    @Autowired
    private JobContractedMapper jobContractedMapper;

    @GetMapping
    public ModelAndView showForm(){
        ModelAndView mv = new ModelAndView("client/details-contact");

        Optional<Individual> oProfessional = Optional.ofNullable(professionalService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));
        ProfessionalDTO professionalDTO = professionaMapper.toResponseDto(oProfessional.get());
        mv.addObject("professional", professionalDTO);

        List<JobContracted> jobContracted = jobContractedService.findByIdProfessional(professionalDTO.getId());
        List<JobContractedDTO> JobContractedDTO = jobContracted.stream()
                .map(contracted -> jobContractedMapper.toResponseDto(contracted))
                .collect(Collectors.toList());
        mv.addObject("jobContracted", JobContractedDTO);

        return mv;
    }
}