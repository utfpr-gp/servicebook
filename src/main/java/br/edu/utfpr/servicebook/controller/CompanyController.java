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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequestMapping("/minha-conta/empresa")
@Controller
public class CompanyController {

    public static final Logger log = LoggerFactory.getLogger(CompanyController.class);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Autowired
    private FollowsService followsService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private ProfessionalExpertiseService professionalExpertiseService;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private JobRequestMapper jobRequestMapper;

    @Autowired
    private JobCandidateService jobCandidateService;

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
    private IAuthentication authentication;

    @Autowired
    private PaginationUtil paginationUtil;

    @GetMapping
    @RolesAllowed({RoleType.COMPANY})
    public ModelAndView showMyAccountCompany(@RequestParam(required = false, defaultValue = "0") Optional<Long> expertiseId
    ) throws Exception {
        Optional<User> oProfessional = (userService.findByEmail(authentication.getEmail()));

        log.debug("ServiceBook: Minha conta.");

        if (!oProfessional.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        ModelAndView mv = new ModelAndView("company/my-account");
        UserDTO professionalDTO = userMapper.toDto(oProfessional.get());

        Optional<Long> oProfessionalFollowingAmount = followsService.countByProfessional(oProfessional.get());
        professionalDTO.setFollowingAmount(oProfessionalFollowingAmount.get());
        UserTemplateInfo individualInfo = templateUtil.getUserInfo(professionalDTO);
        UserTemplateStatisticInfo statisticInfo = templateUtil.getProfessionalStatisticInfo(oProfessional.get(), expertiseId.get());
        mv.addObject("userInfo", individualInfo);

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
        UserDTO professionalDTO1 = userMapper.toDto(oProfessional.get());
        mv.addObject("eventsse", eventSSEDTOs);
        mv.addObject("expertises", expertiseDTOs);
        mv.addObject("userInfo", individualInfo);
        mv.addObject("professionalDTO1", professionalDTO1);
        mv.addObject("statisticInfo", statisticInfo);
        mv.addObject("company", true);
        return mv;
    }
    @GetMapping("/adicionar-profissional")
    @RolesAllowed({RoleType.COMPANY})
    public ModelAndView newProfessional(@RequestParam(required = false, defaultValue = "0") Optional<Long> expertiseId
    ) throws Exception {
        ModelAndView mv = new ModelAndView("company/new-professional");
        Optional<User> oProfessional = (userService.findByEmail(authentication.getEmail()));

        UserDTO professionalDTO = userMapper.toDto(oProfessional.get());

        Optional<Long> oProfessionalFollowingAmount = followsService.countByProfessional(oProfessional.get());
        professionalDTO.setFollowingAmount(oProfessionalFollowingAmount.get());
        UserTemplateInfo individualInfo = templateUtil.getUserInfo(professionalDTO);
        UserTemplateStatisticInfo statisticInfo = templateUtil.getProfessionalStatisticInfo(oProfessional.get(), expertiseId.get());
        mv.addObject("userInfo", individualInfo);

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

        UserDTO professionalDTO1 = userMapper.toDto(oProfessional.get());
        mv.addObject("eventsse", eventSSEDTOs);
        mv.addObject("expertises", expertiseDTOs);
        mv.addObject("userInfo", individualInfo);
        mv.addObject("professionalDTO1", professionalDTO1);
        mv.addObject("statisticInfo", statisticInfo);
        mv.addObject("company", true);
        return mv;
    }

    @GetMapping("/disponiveis")
    @RolesAllowed({RoleType.COMPANY})
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
    @RolesAllowed({RoleType.COMPANY})
    private Page<JobRequest> findJobRequests(Long expertiseId, JobRequest.Status status, int page, int size){
        Optional<User> oProfessional = (userService.findByEmail(authentication.getEmail()));

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
    @RolesAllowed({RoleType.COMPANY})

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
