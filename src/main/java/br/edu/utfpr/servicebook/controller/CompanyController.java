package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.exception.InvalidParamsException;
import br.edu.utfpr.servicebook.follower.FollowsService;
import br.edu.utfpr.servicebook.model.dto.ExpertiseDTO;
import br.edu.utfpr.servicebook.model.dto.IndividualDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestFullDTO;
import br.edu.utfpr.servicebook.model.dto.UserCodeDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
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
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelIndividualDTO;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelStatisticsDTO;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/empresa")
@Controller
public class CompanyController {
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
    private SidePanelUtil sidePanelUtil;

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


    @GetMapping
    public ModelAndView showMyAccountCompany(@RequestParam(required = false, defaultValue = "0") Optional<Long> expertiseId
    ) throws Exception {
        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        ModelAndView mv = new ModelAndView("company/my-account");
        IndividualDTO professionalDTO = individualMapper.toDto(oProfessional.get());

        Optional<Long> oProfessionalFollowingAmount = followsService.countByProfessional(oProfessional.get());
        professionalDTO.setFollowingAmount(oProfessionalFollowingAmount.get());
        SidePanelIndividualDTO individualInfo = sidePanelUtil.getIndividualInfo(professionalDTO);
        SidePanelStatisticsDTO statisticInfo = sidePanelUtil.getProfessionalStatisticInfo(oProfessional.get(), expertiseId.get());
        mv.addObject("individualInfo", individualInfo);

        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(oProfessional.get());
        List<ExpertiseDTO> expertiseDTOs = professionalExpertises.stream()
                .map(professionalExpertise -> professionalExpertise.getExpertise())
                .map(expertise -> expertiseMapper.toDto(expertise))
                .collect(Collectors.toList());

        //envia a notificação ao usuário
        List<EventSSE> eventSsesList = sseService.findPendingEventsByEmail(authentication.getEmail());
        List<EventSSEDTO> eventSSEDTOs = eventSsesList.stream()
                .map(eventSse -> {
                    return eventSseMapper.toFullDto(eventSse);
                })
                .collect(Collectors.toList());
        IndividualDTO professionalDTO1 = individualMapper.toResponseDto(oProfessional.get());
        mv.addObject("eventsse", eventSSEDTOs);
        mv.addObject("expertises", expertiseDTOs);
        mv.addObject("individualInfo", individualInfo);
        mv.addObject("professionalDTO1", professionalDTO1);
        mv.addObject("statisticInfo", statisticInfo);
        mv.addObject("company", true);
        return mv;
    }

    @GetMapping("/adicionar-profissional")
    public ModelAndView newProfessional(@RequestParam(required = false, defaultValue = "0") Optional<Long> expertiseId
    ) throws Exception {
        ModelAndView mv = new ModelAndView("company/new-professional");
        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));

        IndividualDTO professionalDTO = individualMapper.toDto(oProfessional.get());

        Optional<Long> oProfessionalFollowingAmount = followsService.countByProfessional(oProfessional.get());
        professionalDTO.setFollowingAmount(oProfessionalFollowingAmount.get());
        SidePanelIndividualDTO individualInfo = sidePanelUtil.getIndividualInfo(professionalDTO);
        SidePanelStatisticsDTO statisticInfo = sidePanelUtil.getProfessionalStatisticInfo(oProfessional.get(), expertiseId.get());
        mv.addObject("individualInfo", individualInfo);

        List<ProfessionalExpertise> professionalExpertises = professionalExpertiseService.findByProfessional(oProfessional.get());
        List<ExpertiseDTO> expertiseDTOs = professionalExpertises.stream()
                .map(professionalExpertise -> professionalExpertise.getExpertise())
                .map(expertise -> expertiseMapper.toDto(expertise))
                .collect(Collectors.toList());

        //envia a notificação ao usuário
        List<EventSSE> eventSsesList = sseService.findPendingEventsByEmail(authentication.getEmail());
        List<EventSSEDTO> eventSSEDTOs = eventSsesList.stream()
                .map(eventSse -> {
                    return eventSseMapper.toFullDto(eventSse);
                })
                .collect(Collectors.toList());
        IndividualDTO professionalDTO1 = individualMapper.toResponseDto(oProfessional.get());
        mv.addObject("eventsse", eventSSEDTOs);
        mv.addObject("expertises", expertiseDTOs);
        mv.addObject("individualInfo", individualInfo);
        mv.addObject("professionalDTO1", professionalDTO1);
        mv.addObject("statisticInfo", statisticInfo);
        mv.addObject("company", true);
        return mv;
    }

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

    private Page<JobRequest> findJobRequests(Long expertiseId, JobRequest.Status status, int page, int size){
        Optional<Individual> oProfessional = (individualService.findByEmail(authentication.getEmail()));

        if (!oProfessional.isPresent()) {
            throw new EntityNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateExpired").ascending());
        Page<JobRequest> jobRequestPage = null;
        List<JobRequestFullDTO> jobRequestFullDTOs = null;

        if (expertiseId == 0) {
            if(status == JobRequest.Status.AVAILABLE){
                return jobRequestPage = jobRequestService.findAvailableAllExpertises(JobRequest.Status.AVAILABLE, oProfessional.get().getId(), pageRequest);
            }
            else if(status == JobRequest.Status.TO_DO){
                return jobRequestPage = jobRequestService.findByStatusAndJobContracted_Professional(JobRequest.Status.TO_DO, oProfessional.get(), pageRequest);
            }
            else if(status == JobRequest.Status.DOING){
                return jobRequestPage = jobRequestService.findByStatusAndJobContracted_Professional(JobRequest.Status.DOING, oProfessional.get(), pageRequest);
            }
            else if(status == JobRequest.Status.CANCELED){
                return jobRequestPage = jobRequestService.findByStatusAndJobContracted_Professional(JobRequest.Status.CANCELED, oProfessional.get(), pageRequest);
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
