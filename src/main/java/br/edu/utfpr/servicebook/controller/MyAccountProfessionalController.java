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
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @Autowired
    private CityService cityService;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private StateService stateService;

    @Autowired
    private ClientService clientService;

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


    @GetMapping("/detalhes-servico/{id}")
    public ModelAndView showAvailableDetailJobs(
            @PathVariable Long id
    ) throws Exception {

        Optional<Professional> oProfessional = Optional.ofNullable(professionalService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        ModelAndView mv = new ModelAndView("professional/detail-service");
        Optional<JobRequest> oJob = jobRequestService.findById(id);

        if (!oJob.isPresent()) {
            throw new Exception("O trabalho não foi encontrado em nosso sistema!");
        }

        JobRequest jb = oJob.get();

        JobRequestDetailsDTO jobFull = jobRequestMapper.jobRequestDetailsDTO(jb);

        Optional oClient, oCity, oState;

        oClient = clientService.findById(jobFull.getClient().getId());
        Client client = (Client) oClient.get();

        oCity = cityService.findById(jobFull.getClient().getAddress().getCity().getId());

        City city = (City) oCity.get();
        oState = stateService.findById(city.getState().getId());

        State state = (State) oState.get();


        int quantityCanditatorMax;
        int amountOfCandidates;
        quantityCanditatorMax = jobFull.getQuantityCandidatorsMax();


        if (jobFull.getAmountOfCandidates() == null)
            amountOfCandidates = 0;
        else
            amountOfCandidates = jobFull.getAmountOfCandidates();

        int percentage = (amountOfCandidates * 100) / quantityCanditatorMax;


        mv.addObject("quantityCanditatorMax", quantityCanditatorMax);
        mv.addObject("amountOfCandidates", amountOfCandidates);
        mv.addObject("percentage", percentage);
        mv.addObject("job", jobFull);
        mv.addObject("job", jobFull);
        mv.addObject("client", client);
        mv.addObject("city", city.getName());
        mv.addObject("state", state.getName());
        mv.addObject("ricardo", "ricardo");
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

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("date").descending());
        Page<JobCandidate> jobCandidatePage = null;
        List<JobCandidateMinDTO> jobCandidateDTOs = null;

        if (id == 0) {
            jobCandidatePage = jobCandidateService.findByJobRequest_StatusAndProfessional(JobRequest.Status.AVAILABLE, oProfessional.get(), pageRequest);
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

            jobCandidatePage = jobCandidateService.findByJobRequest_StatusAndJobRequest_ExpertiseAndProfessional(JobRequest.Status.AVAILABLE, oExpertise.get(), oProfessional.get(), pageRequest);
        }

        jobCandidateDTOs = jobCandidatePage.stream()
                .map(jobCandidate -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobCandidate.getJobRequest());

                    if (totalCandidates.isPresent()) {
                        return jobCandidateMapper.toMinDto(jobCandidate, totalCandidates);
                    }

                    return jobCandidateMapper.toMinDto(jobCandidate, Optional.ofNullable(0L));
                }).collect(Collectors.toList());

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(jobCandidatePage, "/minha-conta/profissional/em-disputa");

        ModelAndView mv = new ModelAndView("professional/disputed-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobCandidateDTOs);

        return mv;
    }

    @GetMapping("/para-fazer")
    public ModelAndView showTodoJobs(
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
            jobRequestPage = jobRequestService.findByStatusAndJobContracted_Professional(JobRequest.Status.TO_DO, oProfessional.get(), pageRequest);
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

            jobRequestPage = jobRequestService.findByStatusAndExpertiseAndJobContracted_Professional(JobRequest.Status.TO_DO, oExpertise.get(), oProfessional.get(), pageRequest);
        }

        jobRequestFullDTOs = jobRequestPage.stream()
                .map(jobRequest -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobRequest);

                    if (totalCandidates.isPresent()) {
                        return jobRequestMapper.toFullDto(jobRequest, totalCandidates);
                    }

                    return jobRequestMapper.toFullDto(jobRequest, Optional.ofNullable(0L));
                }).collect(Collectors.toList());

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/profissional/para-fazer");

        ModelAndView mv = new ModelAndView("professional/todo-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

        return mv;
    }

    @GetMapping("/executados")
    public ModelAndView showJobsPerformed(
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

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<JobContracted> jobContractedPage = null;
        List<JobContractedFullDTO> jobContractedDTOs = null;

        if (id == 0) {
            jobContractedPage = jobContractedService.findByJobRequest_StatusAndProfessional(JobRequest.Status.CLOSED, oProfessional.get(), pageRequest);
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

            jobContractedPage = jobContractedService.findByJobRequest_StatusAndJobRequest_ExpertiseAndProfessional(JobRequest.Status.CLOSED, oExpertise.get(), oProfessional.get(), pageRequest);
        }

        jobContractedDTOs = jobContractedPage.stream()
                .map(jobContracted -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobContracted.getJobRequest());

                    if (totalCandidates.isPresent()) {
                        return jobContractedMapper.toFullDto(jobContracted, totalCandidates);
                    }

                    return jobContractedMapper.toFullDto(jobContracted, Optional.ofNullable(0L));
                })
                .collect(Collectors.toList());

        PaginationDTO paginationDTO = PaginationUtil.getPaginationDTO(jobContractedPage, "/minha-conta/profissional/executados");

        ModelAndView mv = new ModelAndView("professional/jobs-performed-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobContractedDTOs);

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

    @GetMapping("/perfil/{id}")
    public ModelAndView editProfile(@PathVariable Long id) throws IOException {
        Optional<Professional> oProfessional = Optional.ofNullable(professionalService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));
        if (!oProfessional.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        ProfessionalDTO professionalDTO = professionalMapper.toDto(oProfessional.get());

        Optional<City> oCity = cityService.findById(oProfessional.get().getAddress().getCity().getId());
        if (!oCity.isPresent()) {
            throw new EntityNotFoundException("Cidade não foi encontrada pelo id informado.");
        }
        CityMinDTO cityMinDTO = cityMapper.toMinDto(oCity.get());

        ModelAndView mv = new ModelAndView("professional/edit-account");
        mv.addObject("professional", professionalDTO);
        mv.addObject("city", cityMinDTO);

        return mv;
    }

}
