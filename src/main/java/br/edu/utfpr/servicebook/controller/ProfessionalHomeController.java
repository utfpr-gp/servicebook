package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.service.FollowsService;
import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.sse.EventSSE;
import br.edu.utfpr.servicebook.sse.EventSSEDTO;
import br.edu.utfpr.servicebook.sse.EventSseMapper;
import br.edu.utfpr.servicebook.sse.SSEService;
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.UserTemplateStatisticInfo;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/profissional")
@Controller
public class ProfessionalHomeController {

    public static final Logger log = LoggerFactory.getLogger(ProfessionalHomeController.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    private FollowsService followsService;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private CityMapper cityMapper;

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
    private StateService stateService;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private SSEService sseService;

    @Autowired
    private EventSseMapper eventSseMapper;

    @Autowired
    private JobAvailableToHideService jobAvailableToHideService;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private PaginationUtil paginationUtil;
    @Autowired
    private UserService userService;

    /**
     * Mostra a tela da minha conta no perfil do profissional, mostrando os anúncios disponíveis por default.
     * @param expertiseId
     * @return
     * @throws Exception
     */
    @GetMapping
    @RolesAllowed({RoleType.USER})
    public ModelAndView showMyAccountProfessional(@RequestParam(required = false, defaultValue = "0") Optional<Long> expertiseId) throws Exception {
        log.debug("ServiceBook: Minha conta.");
   
        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));
//        Optional<User> oProfessional = (userService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        ModelAndView mv = new ModelAndView("professional/my-account");
    
        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(oProfessional.get());
        List<ExpertiseDTO> professionalExpertiseDTOs = professionalExpertises.stream()
                .map(professionalExpertise -> professionalExpertise.getExpertise())
                .map(expertise -> expertiseMapper.toDto(expertise))
                .collect(Collectors.toList());

        IndividualDTO professionalDTO = individualMapper.toDto(oProfessional.get());
        Optional<Long> oProfessionalFollowingAmount = followsService.countByProfessional(oProfessional.get());
        professionalDTO.setFollowingAmount(oProfessionalFollowingAmount.get());

        UserTemplateInfo individualInfo = templateUtil.getUserInfo(professionalDTO);
        UserTemplateStatisticInfo statisticInfo = templateUtil.getProfessionalStatisticInfo(oProfessional.get(), expertiseId.get());

        //envia a notificação ao usuário
        List<EventSSE> eventSsesList = sseService.findPendingEventsByEmail(authentication.getEmail());
        List<EventSSEDTO> eventSSEDTOs = eventSsesList.stream()
                .map(eventSse -> {
                    return eventSseMapper.toFullDto(eventSse);
                })
                .collect(Collectors.toList());

        mv.addObject("eventsse", eventSSEDTOs);
        mv.addObject("professionalExpertises", professionalExpertiseDTOs);
        mv.addObject("userInfo", individualInfo);
        mv.addObject("statisticInfo", statisticInfo);

        return mv;
    }

    /**
     * Retorna a lista de jobs no estado de disponíveis. Se o profissional estiver com o filtro de especialidade, busca
     * por especialidade ou então, por todas as especialidades.
     * Se o profissional marcou alguns anúncios para não aparecer, estes não aparecerão. Há um filtro na busca no BD.
     * @param request
     * @param id
     * @param page
     * @param size
     * @param order
     * @param direction
     * @return
     * @throws Exception
     */
    @GetMapping("/disponiveis")
    @RolesAllowed({RoleType.USER})
    public ModelAndView showAvailableJobs(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "5") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Page<JobRequest> jobRequestPage = findJobRequests(id, JobRequest.Status.AVAILABLE, page, size);

        List<JobRequestFullDTO> jobRequestFullDTOs = generateJobRequestDTOList(jobRequestPage);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/profissional/disponiveis");

        ModelAndView mv = new ModelAndView("professional/available-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

        return mv;
    }

    /**
     * Mostra os serviços em que o profissional se candidatou.
     * @param request
     * @param id
     * @param page
     * @param size
     * @param order
     * @param direction
     * @return
     * @throws Exception
     */
    @GetMapping("/em-disputa")
    @RolesAllowed({RoleType.USER})
    public ModelAndView showDisputedJobs(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateCreated").descending());
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

        jobCandidateDTOs = generateJobCandidateDTOList(jobCandidatePage);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobCandidatePage, "/minha-conta/profissional/em-disputa");

        ModelAndView mv = new ModelAndView("professional/disputed-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobCandidateDTOs);

        return mv;
    }

    @GetMapping("/em-orcamento")
    @RolesAllowed({RoleType.USER})
    public ModelAndView showBudgetJobs(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateCreated").descending());
        Page<JobCandidate> jobCandidatePage = null;
        List<JobCandidateMinDTO> jobCandidateDTOs = null;

        if (id == 0) {
            jobCandidatePage = jobCandidateService.findByJobRequest_StatusAndProfessional(JobRequest.Status.BUDGET, oProfessional.get(), pageRequest);
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

            jobCandidatePage = jobCandidateService.findByJobRequest_StatusAndJobRequest_ExpertiseAndProfessional(JobRequest.Status.BUDGET, oExpertise.get(), oProfessional.get(), pageRequest);
        }

        jobCandidateDTOs = generateJobCandidateDTOList(jobCandidatePage);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobCandidatePage, "/minha-conta/profissional/em-orcamento");

        ModelAndView mv = new ModelAndView("professional/budget-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobCandidateDTOs);

        return mv;
    }

    @GetMapping("/para-contratar")
    @RolesAllowed({RoleType.USER})
    public ModelAndView showForHiredJobs(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Page<JobCandidate> jobCandidatePage = findJobCandidates(id, JobRequest.Status.TO_HIRED, page, size);

        List<JobCandidateMinDTO> jobCandidateDTOs = generateJobCandidateDTOList(jobCandidatePage);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobCandidatePage, "/minha-conta/profissional/para-contratar");

        ModelAndView mv = new ModelAndView("professional/to-hired-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobCandidateDTOs);

        return mv;
    }

    /**
     * Mostra as ordens de serviços para serem feitas, ou seja, que o profissional já foi contratado.
     * @param request
     * @param expertiseId
     * @param page
     * @param size
     * @param order
     * @param direction
     * @return
     * @throws Exception
     */
    @GetMapping("/para-fazer")
    @RolesAllowed({RoleType.USER})
    public ModelAndView showTodoJobs(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long expertiseId,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Page<JobRequest> jobRequestPage = findJobRequests(expertiseId, JobRequest.Status.TO_DO, page, size);

        List<JobRequestFullDTO> jobRequestFullDTOs = generateJobRequestDTOList(jobRequestPage);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/profissional/para-fazer");

        ModelAndView mv = new ModelAndView("professional/todo-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

        return mv;
    }

    @GetMapping("/fazendo")
    @RolesAllowed({RoleType.USER})
    public ModelAndView showWorkingJobs(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Page<JobRequest> jobRequestPage = findJobRequests(id, JobRequest.Status.DOING, page, size);
        List<JobRequestFullDTO> jobRequestFullDTOs = generateJobRequestDTOList(jobRequestPage);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/profissional/para-fazer");

        ModelAndView mv = new ModelAndView("professional/todo-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

        return mv;
    }

    @GetMapping("/executados")
    @RolesAllowed({RoleType.USER})
    public ModelAndView showJobsPerformed(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Page<JobContracted> jobContractedPage = findJobContracted(id, JobRequest.Status.CLOSED, page, size);
        List<JobContractedFullDTO> jobContractedDTOs = generateJobContractedDTOList(jobContractedPage);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobContractedPage, "/minha-conta/profissional/executados");

        ModelAndView mv = new ModelAndView("professional/jobs-performed-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobContractedDTOs);

        return mv;
    }

    @GetMapping("/cancelados")
    @RolesAllowed({RoleType.USER})
    public ModelAndView showCanceledJobs(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Page<JobRequest> jobRequestPage = findJobRequests(id, JobRequest.Status.CANCELED, page, size);
        List<JobRequestFullDTO> jobRequestFullDTOs = generateJobRequestDTOList(jobRequestPage);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/profissional/para-fazer");

        ModelAndView mv = new ModelAndView("professional/todo-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

        return mv;
    }

    @GetMapping("/detalhes")
    @RolesAllowed({RoleType.USER})
    public ModelAndView showMyDetails() throws Exception {
        ModelAndView mv = new ModelAndView("client/details-contact");

        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        IndividualDTO professionalDTO = individualMapper.toDto(oProfessional.get());
        IndividualDTO professionalMinDTO = individualMapper.toDto(oProfessional.get());

        List<JobContracted> jobContracted = jobContractedService.findByIdProfessional(professionalDTO.getId());
        List<JobContractedDTO> jobContractedDTOs = jobContracted.stream()
                .map(contracted -> jobContractedMapper.toResponseDto(contracted))
                .collect(Collectors.toList());

        mv.addObject("individual", professionalMinDTO);
        mv.addObject("jobContracted", jobContractedDTOs);

        return mv;
    }

    @GetMapping("/detalhes-servico/{id}")
    @RolesAllowed({RoleType.USER})
    public ModelAndView showAvailableDetailJobs(
            @PathVariable Long id
    ) throws Exception {

        Optional<Individual> oIndividual = (individualService.findByEmail(authentication.getEmail()));

        if (!oIndividual.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        ModelAndView mv = new ModelAndView("professional/detail-service");
        Optional<JobRequest> oJobRequest = jobRequestService.findById(id);

        if (!oJobRequest.isPresent()) {
            throw new Exception("O serviço não foi encontrado em nosso sistema!");
        }

        JobRequest jobRequest = oJobRequest.get();

        JobRequestDetailsDTO jobFull = jobRequestMapper.jobRequestDetailsDTO(jobRequest);

        Optional oClient, oCity, oState;

        oClient = individualService.findById(jobFull.getUser().getId());
        Individual client = (Individual) oClient.get();

        oCity = cityService.findById(jobFull.getUser().getAddress().getCity().getId());

        City city = (City) oCity.get();
        oState = stateService.findById(city.getState().getId());

        State state = (State) oState.get();

        int maxCandidates = jobRequest.getQuantityCandidatorsMax();
        int currentCandidates = jobRequest.getJobCandidates().size();
        int percentCandidatesApplied = (int)(((double)currentCandidates / (double)maxCandidates) * 100);

        boolean isAvailableJobRequest = jobRequest.getStatus().equals(JobRequest.Status.AVAILABLE) && jobRequest.isClientConfirmation();
        boolean isJobToHired = jobRequest.getStatus().equals(JobRequest.Status.TO_HIRED);

        Optional<JobContracted> oJobContracted = jobContractedService.findByJobRequest(jobRequest);

        if (oJobContracted.isPresent()) {
            JobContracted jobContracted = oJobContracted.get();
            boolean hasToDoDate = jobContracted.getTodoDate() != null;

            String date = this.dateFormat.format(jobRequest.getDateTarget());

            mv.addObject("todoDate",  date);
            mv.addObject("hasTodoDate",  hasToDoDate);
        }

        UserTemplateInfo individualInfo = templateUtil.getUserInfo(oIndividual.get());

        mv.addObject("job", jobFull);
        mv.addObject("client", client);
        mv.addObject("city", city.getName());
        mv.addObject("state", state.getName());
        mv.addObject("candidatesApplied", currentCandidates);
        mv.addObject("maxCandidates", maxCandidates);
        mv.addObject("percentCandidatesApplied", percentCandidatesApplied);
        mv.addObject("isAvailableJobRequest", isAvailableJobRequest);
        mv.addObject("isJobToHired", isJobToHired);
        mv.addObject("userInfo", individualInfo);
        return mv;
    }

    /**
     * Realiza a busca no banco de dados pelos anúncios com paginação filtrando por status e especialidades.
     * @param expertiseId
     * @param status
     * @param page
     * @param size
     * @return
     */
    private Page<JobRequest> findJobRequests(Long expertiseId, JobRequest.Status status, int page, int size){
        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateTarget").ascending());
        Page<JobRequest> jobRequestPage = null;
        List<JobRequestFullDTO> jobRequestFullDTOs = null;

        if (expertiseId == 0) {
            if(status == JobRequest.Status.AVAILABLE){
                return jobRequestPage = jobRequestService.findAvailableAllExpertises(JobRequest.Status.AVAILABLE, oProfessional.get().getId(), pageRequest);
            }
            else if(status == JobRequest.Status.TO_DO){
                return jobRequestPage = jobRequestService.findByStatusAndJobContracted_User(JobRequest.Status.TO_DO, oProfessional.get(), pageRequest);
            }
            else if(status == JobRequest.Status.DOING){
                return jobRequestPage = jobRequestService.findByStatusAndJobContracted_User(JobRequest.Status.DOING, oProfessional.get(), pageRequest);
            }
            else if(status == JobRequest.Status.CANCELED){
                return jobRequestPage = jobRequestService.findByStatusAndJobContracted_User(JobRequest.Status.CANCELED, oProfessional.get(), pageRequest);
            }
            return null;
        } else {

            if (expertiseId < 0) {
                throw new InvalidParamsException("O identificador da especialidade não pode ser negativo. Por favor, tente novamente.");
            }

            Optional<Expertise> oExpertise = expertiseService.findById(expertiseId);

            if (!oExpertise.isPresent()) {
                throw new EntityNotFoundException("A especialidade não foi encontrada pelo id informado. Por favor, tente novamente.");
            }

            Optional<ProfessionalExpertise> oProfessionalExpertise = professionalExpertiseService.findByProfessionalAndExpertise(oProfessional.get(), oExpertise.get());

            if (!oProfessionalExpertise.isPresent()) {
                throw new InvalidParamsException("A especialidade profissional não foi encontrada. Por favor, tente novamente.");
            }

            if(status == JobRequest.Status.AVAILABLE){
                return jobRequestPage = jobRequestService.findAvailableByExpertise(JobRequest.Status.AVAILABLE, oExpertise.get(), oProfessional.get().getId(), pageRequest);
            }
            else if(status == JobRequest.Status.TO_DO){
                return jobRequestPage = jobRequestService.findByStatusAndExpertiseAndJobContracted_Professional(JobRequest.Status.TO_DO, oExpertise.get(), oProfessional.get(), pageRequest);
            }
            else if(status == JobRequest.Status.DOING){
                return jobRequestPage = jobRequestService.findByStatusAndExpertiseAndJobContracted_Professional(JobRequest.Status.DOING, oExpertise.get(), oProfessional.get(), pageRequest);
            }
            else if(status == JobRequest.Status.CANCELED){
                return jobRequestPage = jobRequestService.findByStatusAndExpertiseAndJobContracted_Professional(JobRequest.Status.CANCELED, oExpertise.get(), oProfessional.get(), pageRequest);
            }
            return null;
        }
    }

    private Page<JobCandidate> findJobCandidates(Long expertiseId, JobRequest.Status status, int page, int size){
        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateCreated").descending());
        Page<JobCandidate> jobCandidatePage = null;
        List<JobCandidateMinDTO> jobCandidateDTOs = null;

        if (expertiseId == 0) {
            if(status == JobRequest.Status.TO_HIRED){
                return jobCandidatePage = jobCandidateService.findByJobRequest_StatusAndProfessional(status, oProfessional.get(), pageRequest);
            }
            return null;
        } else {
            if (expertiseId < 0) {
                throw new InvalidParamsException("O identificador da especialidade não pode ser negativo. Por favor, tente novamente.");
            }

            Optional<Expertise> oExpertise = expertiseService.findById(expertiseId);

            if (!oExpertise.isPresent()) {
                throw new EntityNotFoundException("A especialidade não foi encontrada pelo id informado. Por favor, tente novamente.");
            }

            Optional<ProfessionalExpertise> oProfessionalExpertise = professionalExpertiseService.findByProfessionalAndExpertise(oProfessional.get(), oExpertise.get());

            if (!oProfessionalExpertise.isPresent()) {
                throw new InvalidParamsException("A especialidade profissional não foi encontrada. Por favor, tente novamente.");
            }

            if(status == JobRequest.Status.TO_HIRED){
                return jobCandidatePage = jobCandidateService.findByJobRequest_StatusAndJobRequest_ExpertiseAndProfessional(JobRequest.Status.TO_HIRED, oExpertise.get(), oProfessional.get(), pageRequest);
            }

            return null;
        }
    }

    private Page<JobContracted> findJobContracted(Long expertiseId, JobRequest.Status status, int page, int size){
        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<JobContracted> jobContractedPage = null;
        List<JobContractedFullDTO> jobContractedDTOs = null;

        if (expertiseId == 0) {
            if(status == JobRequest.Status.CLOSED){
                return jobContractedPage = jobContractedService.findByJobRequest_StatusAndProfessional(JobRequest.Status.CLOSED, oProfessional.get(), pageRequest);
            }
            return null;
        } else {

            if (expertiseId < 0) {
                throw new InvalidParamsException("O identificador da especialidade não pode ser negativo. Por favor, tente novamente.");
            }

            Optional<Expertise> oExpertise = expertiseService.findById(expertiseId);

            if (!oExpertise.isPresent()) {
                throw new EntityNotFoundException("A especialidade não foi encontrada pelo id informado. Por favor, tente novamente.");
            }

            Optional<ProfessionalExpertise> oProfessionalExpertise = professionalExpertiseService.findByProfessionalAndExpertise(oProfessional.get(), oExpertise.get());

            if (!oProfessionalExpertise.isPresent()) {
                throw new InvalidParamsException("A especialidade profissional não foi encontrada. Por favor, tente novamente.");
            }

            if(status == JobRequest.Status.CLOSED){
                jobContractedPage = jobContractedService.findByJobRequest_StatusAndJobRequest_ExpertiseAndProfessional(JobRequest.Status.CLOSED, oExpertise.get(), oProfessional.get(), pageRequest);
            }
            return null;
        }
    }

    /**
     * Recebe o resultado em uma página e transforma em DTO.
     * @param jobRequestPage
     * @return
     */
    private List<JobRequestFullDTO> generateJobRequestDTOList(Page<JobRequest> jobRequestPage){
        return jobRequestPage.stream().distinct()
                .map(jobRequest -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobRequest);

                    if (totalCandidates.isPresent()) {
                        return jobRequestMapper.toFullDto(jobRequest, totalCandidates);
                    }

                    return jobRequestMapper.toFullDto(jobRequest, Optional.ofNullable(0L));
                }).collect(Collectors.toList());
    }


    private List<JobCandidateMinDTO> generateJobCandidateDTOList(Page<JobCandidate> jobCandidatePage){
        return jobCandidatePage.stream()
                .map(jobCandidate -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobCandidate.getJobRequest());

                    if (totalCandidates.isPresent()) {
                        return jobCandidateMapper.toMinDto(jobCandidate, totalCandidates);
                    }

                    return jobCandidateMapper.toMinDto(jobCandidate, Optional.ofNullable(0L));
                }).collect(Collectors.toList());
    }

    private List<JobContractedFullDTO> generateJobContractedDTOList(Page<JobContracted> jobContractedPage) {
        return jobContractedPage.stream()
                .map(jobContracted -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobContracted.getJobRequest());

                    if (totalCandidates.isPresent()) {
                        return jobContractedMapper.toFullDto(jobContracted, totalCandidates);
                    }

                    return jobContractedMapper.toFullDto(jobContracted, Optional.ofNullable(0L));
                })
                .collect(Collectors.toList());
    }


}
