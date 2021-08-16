package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.ProfessionalMinDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.JobContractedMapper;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.service.ExpertiseService;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.service.ProfessionalExpertiseService;
import br.edu.utfpr.servicebook.service.ProfessionalService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta")
@Controller
public class MyAccountProfessionalController {

    public static final Logger log = LoggerFactory.getLogger(MyAccountProfessionalController.class);

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private ProfessionalMapper professionalMapper;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobContractedMapper jobContractedMapper;

    @GetMapping
    public ModelAndView showMyAccount(@RequestParam Optional<Long> id) {
        log.debug("ServiceBook: Minha conta.");

        Optional<Professional> oProfessional = Optional.ofNullable(professionalService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));
        ProfessionalMinDTO professionalMinDTO = professionalMapper.toMinDto(oProfessional.get());

        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(oProfessional.get());
        List<ExpertiseDTO> expertiseDTOs = professionalExpertises.stream()
                .map(professionalExpertise -> professionalExpertise.getExpertise())
                .map(expertise -> expertiseMapper.toDto(expertise))
                .collect(Collectors.toList());

        ModelAndView mv = new ModelAndView("professional/my-account");

        mv.addObject("professional", professionalMinDTO);
        mv.addObject("expertises", expertiseDTOs);

        Optional<Long> jobs, ratings, comments;

        if (!id.isPresent() || id.get() == 0L) {
            jobs = jobContractedService.countByProfessional(oProfessional.get());
            comments = jobContractedService.countCommentsByProfessional(oProfessional.get());
            ratings = jobContractedService.countRatingByProfessional(oProfessional.get());

            mv.addObject("id", 0L);
        } else {
            if (id.get() < 0) {
                throw new InvalidParamsException("O id informado não pode ser negativo.");
            }

            Optional<Expertise> expertise = expertiseService.findById(id.get());

            if (!expertise.isPresent()) {
                throw new EntityNotFoundException("A especialidade não foi encontrada pelo id informado.");
            }

            Optional<Integer> professionalExpertiseRating = professionalExpertiseService.selectRatingByProfessionalAndExpertise(oProfessional.get().getId(), expertise.get().getId());

            jobs = jobContractedService.countByProfessionalAndJobRequest_Expertise(oProfessional.get(), expertise.get());
            comments = jobContractedService.countCommentsByProfessionalAndJobRequest_Expertise(oProfessional.get(), expertise.get());
            ratings = jobContractedService.countRatingByProfessionalAndJobRequest_Expertise(oProfessional.get(), expertise.get());

            mv.addObject("id", id.get());
            mv.addObject("professionalExpertiseRating", professionalExpertiseRating.get());
        }

        mv.addObject("jobs", jobs.get());
        mv.addObject("ratings", ratings.get());
        mv.addObject("comments", comments.get());

        return mv;
    }

}
