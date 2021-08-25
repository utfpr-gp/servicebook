package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/profissional")
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

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private JobRequestMapper jobRequestMapper;

    @Autowired
    private JobCandidateService jobCandidateService;

    @Autowired
    private JobCandidateMapper jobCandidateMapper;

    @GetMapping
    public ModelAndView showMyAccountProfessional(@RequestParam(required = false, defaultValue = "0") Optional<Long> id) throws Exception {
        log.debug("ServiceBook: Minha conta.");

        Optional<Professional> oProfessional = Optional.ofNullable(professionalService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

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

        return mv;
    }

    @GetMapping("/disponiveis")
    public ModelAndView showAvailableJobs(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Professional> oProfessional = Optional.ofNullable(professionalService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateExpired").ascending());
        Page<JobRequest> jobRequestPage = null;
        List<JobRequestFullDTO> jobRequestFullDTOs = null;

        if (id == 0) {
            jobRequestPage = jobRequestService.findByStatusAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(JobRequest.Status.AVAILABLE, oProfessional.get(), pageRequest);
        } else {
            if (id < 0) {
                throw new InvalidParamsException("O identificador da especialidade não pode ser negativo. Por favor, tente novamente.");
            }

            Optional<Expertise> oExpertise = expertiseService.findById(id);

            if (!oExpertise.isPresent()) {
                throw new EntityNotFoundException("A especialidade não foi encontrada pelo id informado. Por favor, tente novamente.");
            }

            Optional<ProfessionalExpertise> oProfessionalExpertise = professionalExpertiseService.findByProfessionalAndExpertise(oProfessional.get(), oExpertise.get());

            if (!oProfessionalExpertise.isPresent()) {
                throw new InvalidParamsException("A especialidade profissional não foi encontrada. Por favor, tente novamente.");
            }

            jobRequestPage = jobRequestService.findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(JobRequest.Status.AVAILABLE, oExpertise.get(), oProfessional.get(), pageRequest);
        }

        jobRequestFullDTOs = jobRequestPage.stream()
                .map(jobRequest -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobRequest);

                    if (totalCandidates.isPresent()) {
                        return jobRequestMapper.toFullDto(jobRequest, totalCandidates);
                    }

                    return jobRequestMapper.toFullDto(jobRequest, Optional.ofNullable(0L));
                }).collect(Collectors.toList());

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/profissional/disponiveis");

        ModelAndView mv = new ModelAndView("professional/available-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

        return mv;
    }

    ////// --->
    @GetMapping("/em-disputa")
    public ModelAndView showDisputedJobs(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Professional> oProfessional = Optional.ofNullable(professionalService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<JobCandidate> jobRequestPage = null;
        List<JobCandidateDTO> jobCandidateDTOs = null;

        if (id == 0) {
            jobRequestPage = jobCandidateService.findByProfessional(oProfessional.get(), pageRequest);

            jobCandidateDTOs = jobRequestPage.stream()
                    .map(jobCandidate -> jobCandidateMapper.toDto(jobCandidate))
                    .collect(Collectors.toList());
        }

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/profissional/disponiveis");

        ModelAndView mv = new ModelAndView("professional/disputed-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobCandidateDTOs);

        return mv;
    }

    @GetMapping("/para-fazer")
    public ModelAndView showJobsTodo() {
        ModelAndView mv = new ModelAndView("available-jobs-report");
        return mv;
    }

    @GetMapping("/executados")
    public ModelAndView showJobsPerformed() {
        ModelAndView mv = new ModelAndView("available-jobs-report");
        return mv;
    }

    @GetMapping("/detalhes")
    public ModelAndView showMyDetails() throws Exception {
        ModelAndView mv = new ModelAndView("client/details-contact");

        Optional<Professional> oProfessional = Optional.ofNullable(professionalService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        ProfessionalDTO professionalDTO = professionalMapper.toResponseDto(oProfessional.get());

        List<JobContracted> jobContracted = jobContractedService.findByIdProfessional(professionalDTO.getId());
        List<JobContractedDTO> jobContractedDTOs = jobContracted.stream()
                .map(contracted -> jobContractedMapper.toResponseDto(contracted))
                .collect(Collectors.toList());

        mv.addObject("professional", professionalDTO);
        mv.addObject("jobContracted", jobContractedDTOs);

        return mv;
    }

}
