package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.follower.FollowsService;
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
import br.edu.utfpr.servicebook.util.TemplateUtil;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.UserTemplateStatisticInfo;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/profissional")
@Controller
public class ProfessionalInitialController {

    public static final Logger log = LoggerFactory.getLogger(ProfessionalInitialController.class);

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
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private JobRequestMapper jobRequestMapper;

    @Autowired
    private JobCandidateService jobCandidateService;

    @Autowired
    private StateService stateService;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private SSEService sseService;

    @Autowired
    private EventSseMapper eventSseMapper;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private PaginationUtil paginationUtil;

    /**
     * Mostra a tela da minha conta no perfil do profissional, mostrando os anúncios disponíveis por default.
     *
     * @param expertiseId
     * @param request
     * @param id
     * @param page
     * @param size
     * @param order
     * @param direction
     * @return
     * @throws Exception
     */
    @GetMapping
    @RolesAllowed({RoleType.USER})
    public ModelAndView showProfessional(
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "0") Long id,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "5") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction,
            @RequestParam(required = false, defaultValue = "0") Optional<Long> expertiseId
    ) throws Exception {
        log.debug("ServiceBook: Minha conta.");

        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));
        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }
        Page<JobRequest> jobRequestPage = findJobRequests(id, JobRequest.Status.AVAILABLE, page, size);

        List<JobRequestFullDTO> jobRequestFullDTOs = generateJobRequestDTOList(jobRequestPage);

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobRequestPage, "/profissional");

        ModelAndView mv = new ModelAndView("professional/initial");

        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(oProfessional.get());
        List<ExpertiseDTO> expertiseDTOs = professionalExpertises.stream()
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
        mv.addObject("expertises", expertiseDTOs);
        mv.addObject("userInfo", individualInfo);
        mv.addObject("statisticInfo", statisticInfo);
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

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

}
