package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.City;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.mapper.ExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalExpertiseMapper;
import br.edu.utfpr.servicebook.model.mapper.ProfessionalMapper;
import br.edu.utfpr.servicebook.service.ExpertiseService;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.service.ProfessionalExpertiseService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/profissional/especialidades")
@Controller
public class ProfessionalExpertiseController {

    public static final Logger log = LoggerFactory.getLogger(ProfessionalHomeController.class);

    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ProfessionalMapper professionalMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private ProfessionalExpertiseMapper professionalExpertiseMapper;

    @GetMapping()
    public ModelAndView showExpertises(@RequestParam(required = false, defaultValue = "0") Optional<Long> id)  throws Exception {
        Optional<Individual> oProfessional = (individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));
        ModelAndView mv = new ModelAndView("professional/my-expertises");
        boolean isClient = false;

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }
//        IndividualMinDTO professionalMinDTO = individualMapper.toMinDto(oProfessional.get());
        IndividualDTO professionalMinDTO = individualMapper.toDto(oProfessional.get());

        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(oProfessional.get());
        Optional<List<Expertise>> oExpertises = Optional.ofNullable(expertiseService.findAll());

        Optional<Long> jobs, ratings, comments;
        if (!id.isPresent() || id.get() == 0L) {
            jobs = jobContractedService.countByProfessional(oProfessional.get());
            comments = jobContractedService.countCommentsByProfessional(oProfessional.get());
            ratings = jobContractedService.countRatingByProfessional(oProfessional.get());

            mv.addObject("id", 0L);
        } else {
            if (id.get() < 0) {
                throw new InvalidParamsException("O identificador da especialidade não pode ser negativo. Por favor, tente novamente.");
            }

            Optional<Expertise> oExpertise = expertiseService.findById(id.get());

            if (!oExpertise.isPresent()) {
                throw new EntityNotFoundException("A especialidade não foi encontrada pelo id informado. Por favor, tente novamente.");
            }

            Optional<ProfessionalExpertise> oProfessionalExpertise = professionalExpertiseService.findByProfessionalAndExpertise(oProfessional.get(), oExpertise.get());

            if (!oProfessionalExpertise.isPresent()) {
                throw new InvalidParamsException("A especialidade profissional não foi encontrada. Por favor, tente novamente.");
            }

            Optional<Integer> professionalExpertiseRating = professionalExpertiseService.selectRatingByProfessionalAndExpertise(oProfessional.get().getId(), oExpertise.get().getId());

            jobs = jobContractedService.countByProfessionalAndJobRequest_Expertise(oProfessional.get(), oExpertise.get());
            comments = jobContractedService.countCommentsByProfessionalAndJobRequest_Expertise(oProfessional.get(), oExpertise.get());
            ratings = jobContractedService.countRatingByProfessionalAndJobRequest_Expertise(oProfessional.get(), oExpertise.get());

            mv.addObject("id", id.get());
            mv.addObject("professionalExpertiseRating", professionalExpertiseRating.get());
        }

        mv.addObject("jobs", jobs.get());
        mv.addObject("ratings", ratings.get());
        mv.addObject("comments", comments.get());
        mv.addObject("isClient", isClient);

        if (!oExpertises.isPresent()) {
            throw new Exception("Nenhuma expecialidade encontrada");
        }

        List<ProfessionalExpertiseDTO2> professionalExpertiseDTOs = professionalExpertises.stream()
                                                                    .map(s -> professionalExpertiseMapper.toResponseDTO(s))
                                                                    .collect(Collectors.toList());

        List<Expertise> professionPage = expertiseService.findAll();

        List<ExpertiseDTO> professionDTOs = professionPage.stream()
                .map(s -> expertiseMapper.toDto(s))
                .collect(Collectors.toList());

        List<ExpertiseDTO> expertiseDTOs = professionalExpertises.stream()
                .map(professionalExpertise -> professionalExpertise.getExpertise())
                .map(expertise -> expertiseMapper.toDto(expertise))
                .collect(Collectors.toList());

        mv.addObject("individual", professionalMinDTO);
        mv.addObject("expertises", professionDTOs);
//        mv.addObject("expertises", expertiseDTOs);
        mv.addObject("professionalExpertises", professionalExpertiseDTOs);
        return mv;
    }

    @PostMapping()
    public ModelAndView saveExpertises(@Valid ProfessionalExpertiseDTO dto, BindingResult errors, RedirectAttributes redirectAttributes) throws Exception {
        ModelAndView mv = new ModelAndView("redirect:especialidades");

        List<Integer> ids = dto.getIds();

        if (ids == null) {
            return mv;
        }

        Individual professional = this.getProfessional();

        for (int id : ids) {
            Optional<Expertise> e = expertiseService.findById((Long.valueOf(id)));

            if (!e.isPresent()) {
                throw new Exception("Não existe essa especialidade!");
            }
            ProfessionalExpertise p = professionalExpertiseService.save(new ProfessionalExpertise(professional, e.get()));
        }
        return mv;
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Expertise id, RedirectAttributes redirectAttributes) throws Exception {
        log.debug("Removendo uma profissão com id {}", id);
        Individual professional = this.getProfessional();

        Optional <ProfessionalExpertise> optionalProfession = this.professionalExpertiseService.findByProfessionalAndExpertise(professional,id);
        if(!optionalProfession.isPresent()){
            throw new EntityNotFoundException("Erro ao remover, registro não encontrado para o id " + id);
        }
        this.professionalExpertiseService.delete(optionalProfession.get().getId());
        return "redirect:/minha-conta/profissional/especialidades";
    }

    private Individual getProfessional() throws Exception {
        Optional<Individual> oProfessional = (individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Opss! Não foi possivel encontrar seus dados, tente fazer login novamente");
        }

        return oProfessional.get();
    }

}
