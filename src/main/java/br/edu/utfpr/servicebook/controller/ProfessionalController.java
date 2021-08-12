package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalMinDTO;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.JobContractedMapper;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.service.ProfessionalExpertiseService;
import br.edu.utfpr.servicebook.service.ProfessionalService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta")
@Controller
public class ProfessionalController {

    public static final Logger log = LoggerFactory.getLogger(ProfessionalController.class);

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private ProfessionalMapper professionalMapper;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobContractedMapper jobContractedMapper;

    @GetMapping
    public ModelAndView showMyAccount() {
        log.debug("ServiceBook: Minha conta.");

        ModelAndView mv = new ModelAndView("professional/my-account");

        Optional<Professional> oProfessional = Optional.ofNullable(professionalService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

        ProfessionalMinDTO professionalMinDTO = professionalMapper.toMinDto(oProfessional.get());
        mv.addObject("professional", professionalMinDTO);

        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(oProfessional.get());
        List<ExpertiseDTO> expertiseDTOs = professionalExpertises.stream()
                .map(professionalExpertise -> professionalExpertise.getExpertise())
                .map(expertise -> expertiseMapper.toDto(expertise))
                .collect(Collectors.toList());
        mv.addObject("expertises", expertiseDTOs);

        Integer jobs = jobContractedService.countByIdProfessional(oProfessional.get().getId());
        mv.addObject("jobs", jobs);

        Integer ratings = jobContractedService.countRatingByIdProfessional(oProfessional.get().getId());
        mv.addObject("ratings", ratings);

        Integer comments = jobContractedService.countCommentsByIdProfessional(oProfessional.get().getId());
        mv.addObject("comments", comments);

        return mv;
    }

    @GetMapping("/filtros/{expertise}")
    public ModelAndView showStatusByExpertise(@PathVariable("expertise") Long expertise) {
        log.debug("ServiceBook: Minha conta - Especialidades.");

        ModelAndView mv = new ModelAndView("professional/my-account");
        mv.addObject("expertise", expertise);

        return mv;
    }

}
