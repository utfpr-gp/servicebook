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
import br.edu.utfpr.servicebook.util.pagination.PaginationDTO;
import br.edu.utfpr.servicebook.util.pagination.PaginationUtil;
import br.edu.utfpr.servicebook.util.UserTemplateInfo;
import br.edu.utfpr.servicebook.util.TemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/cliente")
@Controller
public class ClientController {

    public static final Logger log =
            LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private JobCandidateService jobCandidateService;

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private JobCandidateMapper jobCandidateMapper;

    @Autowired
    private JobRequestMapper jobRequestMapper;

    @Autowired
    private ExpertiseService expertiseService;

    @Autowired
    private ExpertiseMapper expertiseMapper;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobContractedMapper jobContractedMapper;

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private SSEService sseService;

    @Autowired
    private EventSseMapper eventSseMapper;

    @Autowired
    private IAuthentication authentication;

    @Autowired
    private FollowsService followsService;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private PaginationUtil paginationUtil;

    @Autowired
    private ProfessionalMapper professionalMapper;


    @GetMapping
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView show() throws Exception {

        Optional<Individual> oClient = individualService.findByEmail(authentication.getEmail());

        if (!oClient.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        //apresenta os eventos de notificação ao cliente que ainda não foram lidos
        List<EventSSE> eventSsesList = sseService.findPendingEventsByEmail(authentication.getEmail());
        List<EventSSEDTO> eventSseDTOs = eventSsesList.stream()
                .map(eventSse -> {
                    return eventSseMapper.toFullDto(eventSse);
                })
                .collect(Collectors.toList());

        //cria o dto do cliente
        IndividualDTO clientDTO = individualMapper.toDto(oClient.get());
        Optional<Long> oClientFollowingAmount = followsService.countByClient(oClient.get());
        clientDTO.setFollowingAmount(oClientFollowingAmount.get());

        //cria o dto para passar ao painel lateral
        UserTemplateInfo userTemplateInfo = templateUtil.getUserInfo(clientDTO);

        List<JobRequest> jobRequests = jobRequestService.findByClientOrderByDateCreatedDesc(oClient.get());

        List<JobRequestMinDTO> jobRequestDTOs = jobRequests.stream()
                .map(job -> {
                    Optional<Long> amountOfCandidates = jobCandidateService.countByJobRequest(job);

                    if (amountOfCandidates.isPresent()) {
                        return jobRequestMapper.toMinDto(job, amountOfCandidates);
                    }
                    return jobRequestMapper.toMinDto(job, Optional.ofNullable(0L));
                })
                .collect(Collectors.toList());

        ModelAndView mv = new ModelAndView("client/my-requests");
        mv.addObject("jobRequests", jobRequestDTOs);
        mv.addObject("eventsse", eventSseDTOs);
        mv.addObject("individualInfo", userTemplateInfo);

        return mv;
    }

    /**
     * Cliente remove o seu próprio anúncio.
     * @param id
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @DeleteMapping("/meus-pedidos/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String delete (@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {

        Optional<Individual> individual = (individualService.findByEmail(authentication.getEmail()));

        if (!individual.isPresent()) {
            throw new IOException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        Optional<JobRequest> jobRequest = this.jobRequestService.findById(id);

        if (!jobRequest.isPresent()) {
            throw new EntityNotFoundException("Solicitação não foi encontrada pelo id informado.");
        }

        Long jobRequestClientId = jobRequest.get().getUser().getId();
        Long clientId = individual.get().getId();

        if (jobRequestClientId != clientId) {
            throw new EntityNotFoundException("Você não ter permissão para deletar essa solicitação.");
        }

        this.jobRequestService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "Solicitação deletada!");

        return "redirect:/minha-conta/meus-pedidos";
    }

    /**
     * Mostra a tela de detalhes de um anúncio do cliente, ou seja, com os candidatos.
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/meus-pedidos/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showDetailsRequest(@PathVariable Optional<Long> id) throws Exception {

        ModelAndView mv = new ModelAndView("client/details-request");

        Optional<JobRequest> jobRequest = jobRequestService.findById(id.get());

        if (!jobRequest.isPresent()) {
            throw new EntityNotFoundException("Solicitação de serviço não encontrado. Por favor, tente novamente.");
        }

        JobRequestFullDTO jobDTO = jobRequestMapper.toFullDto(jobRequest.get());

        Long expertiseId = jobRequest.get().getExpertise().getId();

        Optional<Expertise> expertise = expertiseService.findById(expertiseId);

        if (!expertise.isPresent()) {
            throw new EntityNotFoundException("A especialidade não foi encontrada. Por favor, tente novamente.");
        }

        ExpertiseMinDTO expertiseDTO = expertiseMapper.toMinDto(expertise.get());

        List<JobCandidate> jobCandidates = jobCandidateService.findByJobRequestOrderByChosenByBudgetDesc(jobRequest.get());

        List<JobCandidateDTO> jobCandidatesDTOs = jobCandidates.stream()
                .map(candidate -> {
                    Optional<Long> oProfessionalFollowingAmount = followsService.countByProfessional(candidate.getUser());
                    candidate.getUser().setFollowsAmount(oProfessionalFollowingAmount.get());
                    return jobCandidateMapper.toDto(candidate);
                })
                .collect(Collectors.toList());

        mv.addObject("candidates", jobCandidatesDTOs);
        mv.addObject("userInfo", this.getSidePanelUser());
        mv.addObject("expertise", expertiseDTO);
        mv.addObject("jobRequest", jobDTO);
        return mv;
    }

    /**
     * Apresenta a tela de detalhes de um candidato para um serviço.
     * @param jobId
     * @param candidateId
     * @return
     * @throws Exception
     */
    @GetMapping("/meus-pedidos/{jobId}/detalhes/{candidateId}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showDetailsRequestCandidate(@PathVariable Optional<Long> jobId, @PathVariable Optional<Long> candidateId) throws Exception {
        ModelAndView mv = new ModelAndView("client/details-request-candidate");

        Optional<Individual> oCandidate = individualService.findById(candidateId.get());
        if (!oCandidate.isPresent()) {
            throw new EntityNotFoundException("O usuário não foi encontrado!");
        }

        Optional<JobCandidate> jobCandidate = jobCandidateService.findById(jobId.get(), oCandidate.get().getId());
        if (!jobCandidate.isPresent()) {
            throw new EntityNotFoundException("Candidato não encontrado");
        }
        JobCandidateDTO jobCandidateDTO = jobCandidateMapper.toDto(jobCandidate.get());

        Optional<Individual> client = (individualService.findByEmail(authentication.getEmail()));
        IndividualDTO individualDTO = individualMapper.toDto(client.get());
        jobCandidateDTO.setIndividual(professionalMapper.toMinDto(oCandidate.get()));
        List<Follows> follows = followsService.findFollowProfessionalClient(oCandidate.get(), client.get());
        boolean isFollow = !follows.isEmpty();
        System.out.println(jobCandidateDTO.getId());
        mv.addObject("jobCandidate", jobCandidateDTO);
        mv.addObject("isFollow", isFollow);
        mv.addObject("jobClient", individualDTO);

        return mv;
    }

    @GetMapping("/meus-pedidos/disponiveis")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showAvailableJobs(
            HttpServletRequest request,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Individual> individual = (individualService.findByEmail(authentication.getEmail()));

        if (!individual.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateTarget").ascending());
        Page<JobRequest> jobRequestPage = null;
        List<JobRequestFullDTO> jobRequestFullDTOs = null;

        jobRequestPage = jobRequestService.findByStatusAndClient(JobRequest.Status.AVAILABLE, individual.get(), pageRequest);

        jobRequestFullDTOs = jobRequestPage.stream()
                .map(jobRequest -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobRequest);

                    if (totalCandidates.isPresent()) {
                        return jobRequestMapper.toFullDto(jobRequest, totalCandidates);
                    }

                    return jobRequestMapper.toFullDto(jobRequest, Optional.ofNullable(0L));
                }).collect(Collectors.toList());

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/cliente/meus-pedidos/disponiveis");

        ModelAndView mv = new ModelAndView("client/job-request/tabs/available-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

        return mv;
    }

    /**
     * O cliente exclui um anúncio.
     * O cliente pode ter contratado de alguma outra forma, não necessitando mais do anúncio.
     * @param id
     * @param redirectAttributes
     * @return
     */
    @DeleteMapping("/desistir/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String desist(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String currentUserEmail = authentication.getEmail();

        Optional<Individual> oindividual = individualService.findByEmail(currentUserEmail);
        if(!oindividual.isPresent()){
            throw new EntityNotFoundException("O usuário não foi encontrado!");
        }

        Optional<JobRequest> oJobRequest = jobRequestService.findById(id);
        if(!oJobRequest.isPresent()) {
            throw new EntityNotFoundException("O anúncio não foi encontrado!");
        }

        jobRequestService.delete(id);
        quartzService.sendEmailToConfirmationStatus(id);
        redirectAttributes.addFlashAttribute("msg", "O pedido foi excluído com sucesso!");

        return "redirect:/minha-conta/cliente#disponiveis";
    }

    @GetMapping("/meus-pedidos/para-orcamento")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showDisputedJobs(
            HttpServletRequest request,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Individual> individual = (individualService.findByEmail(authentication.getEmail()));

        if (!individual.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateTarget").ascending());
        Page<JobRequest> jobRequestPage = null;
        List<JobRequestFullDTO> jobRequestFullDTOs = null;

        jobRequestPage = jobRequestService.findByStatusAndClient(JobRequest.Status.BUDGET, individual.get(), pageRequest);

        jobRequestFullDTOs = jobRequestPage.stream()
                .map(jobRequest -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobRequest);

                    if (totalCandidates.isPresent()) {
                        return jobRequestMapper.toFullDto(jobRequest, totalCandidates);
                    }

                    return jobRequestMapper.toFullDto(jobRequest, Optional.ofNullable(0L));
                }).collect(Collectors.toList());

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/cliente/meus-pedidos/disponiveis");

        ModelAndView mv = new ModelAndView("client/job-request/tabs/available-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

        return mv;
    }

    /**
     * Retorna os serviços para fazer, ou seja, que já foram confirmados pelo profissional.
     * @param request
     * @param page
     * @param size
     * @param order
     * @param direction
     * @return
     * @throws Exception
     */
    @GetMapping("/meus-pedidos/para-fazer")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showTodoJobs(
            HttpServletRequest request,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Individual> individual = (individualService.findByEmail(authentication.getEmail()));

        if (!individual.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateTarget").ascending());
        Page<JobRequest> jobRequestPage = null;
        List<JobRequestFullDTO> jobRequestFullDTOs = null;

        jobRequestPage = jobRequestService.findByStatusAndClient(JobRequest.Status.TO_DO, individual.get(), pageRequest);

        jobRequestFullDTOs = jobRequestPage.stream()
                .map(jobRequest -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobRequest);

                    if (totalCandidates.isPresent()) {
                        return jobRequestMapper.toFullDto(jobRequest, totalCandidates);
                    }

                    return jobRequestMapper.toFullDto(jobRequest, Optional.ofNullable(0L));
                }).collect(Collectors.toList());

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobRequestPage, "/minha-conta/cliente/meus-pedidos/disponiveis");

        ModelAndView mv = new ModelAndView("client/job-request/tabs/available-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobRequestFullDTOs);

        return mv;
    }

    /**
     * Retorna os serviços que aguardam confirmação do profissional para serem realizados
     * @param request
     * @param page
     * @param size
     * @param order
     * @param direction
     * @return
     * @throws Exception
     */
    @GetMapping("/meus-pedidos/para-confirmar")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showForHiredJobs(
            HttpServletRequest request,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Individual> client = (individualService.findByEmail(authentication.getEmail()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("dateCreated").descending());
        Page<JobCandidate> jobCandidatePage = null;
        List<JobCandidateMinDTO> jobCandidateDTOs = null;

        jobCandidatePage = jobCandidateService.findByJobRequest_StatusAndJobRequest_Client(JobRequest.Status.TO_HIRED, client.get(),pageRequest);

        jobCandidateDTOs = jobCandidatePage.stream()
                .map(jobCandidate -> {
                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobCandidate.getJobRequest());

                    if (totalCandidates.isPresent()) {
                        return jobCandidateMapper.toMinDto(jobCandidate, totalCandidates);
                    }

                    return jobCandidateMapper.toMinDto(jobCandidate, Optional.ofNullable(0L));
                }).collect(Collectors.toList());

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobCandidatePage, "/minha-conta/cliente/meus-pedidos/para-confirmar");

        ModelAndView mv = new ModelAndView("client/job-request/tabs/to-hired-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobCandidateDTOs);

        return mv;
    }

    @GetMapping("/meus-pedidos/fazendo")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showDoingJobs(
            HttpServletRequest request,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Individual> individual = (individualService.findByEmail(authentication.getEmail()));

        if (!individual.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<JobContracted> jobContractedPage = null;
        List<JobContractedFullDTO> jobContractedDTOs = null;

        jobContractedPage = jobContractedService.findByJobRequest_StatusAndJobRequest_Client(JobRequest.Status.DOING, individual.get(), pageRequest);

        jobContractedDTOs = jobContractedPage.stream()
                .map(jobContracted -> {

                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobContracted.getJobRequest());

                    if (totalCandidates.isPresent()) {
                        return jobContractedMapper.toFullDto(jobContracted, totalCandidates);
                    }

                    return jobContractedMapper.toFullDto(jobContracted, Optional.ofNullable(0L));
                })
                .collect(Collectors.toList());

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobContractedPage, "/minha-conta/cliente/meus-pedidos/fazendo");

        ModelAndView mv = new ModelAndView("client/job-request/tabs/doing-jobs-report");
        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobContractedDTOs);

        quartzService.updateJobRequestStatusWhenIsHiredDateExpired();

        return mv;
    }

    /**
     * Retorna uma lista de pedidos criados pelo cliente que já foram finalizados.
     * @param request
     * @param page
     * @param size
     * @param order
     * @param direction
     * @return
     * @throws Exception
     */
    @GetMapping("/meus-pedidos/executados")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public ModelAndView showJobsPerformed(
            HttpServletRequest request,
            @RequestParam(value = "pag", defaultValue = "1") int page,
            @RequestParam(value = "siz", defaultValue = "3") int size,
            @RequestParam(value = "ord", defaultValue = "id") String order,
            @RequestParam(value = "dir", defaultValue = "ASC") String direction
    ) throws Exception {

        Optional<Individual> individual = (individualService.findByEmail(authentication.getEmail()));

        if (!individual.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("id").descending());
        Page<JobContracted> jobContractedPage = null;
        List<JobContractedFullDTO> jobContractedDTOs = null;

        jobContractedPage = jobContractedService.findByJobRequest_StatusAndJobRequest_Client(JobRequest.Status.CLOSED, individual.get(), pageRequest);
        System.out.println("Quantos: " + jobContractedPage.getTotalElements());
        jobContractedDTOs = jobContractedPage.stream()
                .map(jobContracted -> {

                    System.out.println("ID: " + jobContracted.getJobRequest().getId());

                    Optional<Long> totalCandidates = jobCandidateService.countByJobRequest(jobContracted.getJobRequest());

                    if (totalCandidates.isPresent()) {
                        return jobContractedMapper.toFullDto(jobContracted, totalCandidates);
                    }

                    return jobContractedMapper.toFullDto(jobContracted, Optional.ofNullable(0L));
                })
                .collect(Collectors.toList());

        PaginationDTO paginationDTO = paginationUtil.getPaginationDTO(jobContractedPage, "/minha-conta/cliente/meus-pedidos/executados");

        ModelAndView mv = new ModelAndView("client/job-request/tabs/executed-jobs-report");

        mv.addObject("pagination", paginationDTO);
        mv.addObject("jobs", jobContractedDTOs);

        return mv;
    }

    /**
     * Encerra o recebimento de candidaturas antes de receber o total de candidaturas esperado.
     * Basicamente, muda o estado para BUDGET.
     * @param id
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PatchMapping("/encerra-pedido/{id}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String updateRequest(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {

        Optional<Individual> oClient = (individualService.findByEmail(authentication.getEmail()));

        if (!oClient.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        Optional<JobRequest> oJobRequest = this.jobRequestService.findById(id);

        if (!oJobRequest.isPresent()) {
            throw new EntityNotFoundException("Solicitação não foi encontrada pelo id informado.");
        }

        JobRequest jobRequest = oJobRequest.get();

        Long jobRequestClientId = jobRequest.getUser().getId();
        Long clientId = oClient.get().getId();

        if (jobRequestClientId != clientId) {
            throw new EntityNotFoundException("Você não tem permissão para alterar essa solicitação.");
        }

        if (!jobRequest.getStatus().equals(JobRequest.Status.AVAILABLE)) {
            throw new InvalidParamsException("O status da solicitação não pode ser alterado.");
        }

        jobRequest.setStatus(JobRequest.Status.BUDGET);
        this.jobRequestService.save(jobRequest);

        redirectAttributes.addFlashAttribute("msg", "Solicitação alterada!");
        return "redirect:/minha-conta/meus-pedidos?tab=paraOrcamento";
    }

    private UserTemplateInfo getSidePanelUser() throws Exception {
        Optional<Individual> client = (individualService.findByEmail(authentication.getEmail()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }
        IndividualDTO individualDTO = individualMapper.toDto(client.get());

        return templateUtil.getUserInfo(individualDTO);
    }

    /**
     * O cliente escolhe um profissional para realizar o orçamento ou cancela a escolha do profissional para orçamento.
     * *
     * @param jobId
     * @param candidateId
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PatchMapping("/orcamento-ao/{candidateId}/para/{jobId}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String markAsBudget(@PathVariable Long jobId, @PathVariable Long candidateId, RedirectAttributes redirectAttributes) throws IOException {

        Optional<JobCandidate> oJobCandidate = jobCandidateService.findById(jobId, candidateId);

        if (!oJobCandidate.isPresent()) {
            throw new EntityNotFoundException("Candidato não encontrado");
        }

        Optional<JobRequest> oJobRequest = jobRequestService.findById(jobId);
        if(!oJobRequest.isPresent()) {
            throw new EntityNotFoundException("Pedido não encontrado!");
        }
        JobRequest jobRequest = oJobRequest.get();

        //verifica se o usuário logado é o dono do dado
        User user = individualService.getAuthenticated();

        if(jobRequest.getUser().getId() != user.getId()){
            throw new InvalidParamsException("O usuário não tem permissão de alterar este dado!");
        }

        JobCandidate jobCandidate = oJobCandidate.get();
        jobCandidate.setChosenByBudget(!jobCandidate.isChosenByBudget());
        jobCandidateService.save(jobCandidate);

        //caso o candidato seja o primeiro escolhido para orçamento
        if (jobCandidate.isChosenByBudget() && jobRequest.getStatus() == JobRequest.Status.AVAILABLE) {
            jobRequest.setStatus(JobRequest.Status.BUDGET);
            jobRequestService.save(jobRequest);
        }

        //caso o candidato seja o último ou único e o cliente cancelou o orçamento, muda o job para AVAILABLE novamente
        if(!jobCandidate.isChosenByBudget()){
            List<JobCandidate> jobCandidates = jobCandidateService.findByJobRequestAndChosenByBudget(jobRequest, true);
            if(jobCandidates.isEmpty()) {
                jobRequest.setStatus(JobRequest.Status.AVAILABLE);
            }
        }

        return "redirect:/minha-conta/cliente/meus-pedidos/" + jobId;
    }

    /**
     * Altera o estado para finalizado, ou seja, o cliente verifica que o serviço foi finalizado
     * e então sinaliza manualmete esta informação na plataforma.
     * O estado do JobRequest é mudado para CLOSED e a data da finalização é guardada em JobContracted.
     * @param jobId
     * @param dto
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PatchMapping("/informa-finalizado/{jobId}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String markAsClose(
            @PathVariable Long jobId,
            JobCandidateMinDTO dto,
            RedirectAttributes redirectAttributes) throws IOException {

        Optional<JobRequest> oJobRequest = jobRequestService.findById(jobId);

        if(!oJobRequest.isPresent()) {
            throw new EntityNotFoundException("Pedido não encontrado!");
        }

        JobRequest jobRequest = oJobRequest.get();

        //verifica se o usuário é realmente o dono do anúncio
        User user = jobRequest.getUser();

        Optional<Individual> oClient = individualService.findByEmail(authentication.getEmail());

        if (!oClient.isPresent()) {
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        jobRequest.setStatus(JobRequest.Status.CLOSED);
        jobRequestService.save(jobRequest);

        //busca pelo job que foi contratado, ao finalizar é adicionado no campo data e horário em que foi concluido.
        Optional<JobContracted> oJobContracted = jobContractedService.findByJobRequest(jobRequest);
        if(!oJobContracted.isPresent()) {
            throw new EntityNotFoundException("O serviço não pode ser finalizado!");
        }

        JobContracted jobContracted = oJobContracted.get();
        jobContracted.setFinishDate(new Date());
        jobContractedService.save(jobContracted);

        return "redirect:/minha-conta/cliente#executados";
    }

    /**
     * Altera o estado de um serviço para TO_HIRED, ou seja, o cliente contratou um serviço mas
     * fica no estado de espera da confirmação do profissional.
     * @param jobId
     * @param individualId
     * @param redirectAttributes
     * @return
     * @throws IOException
     */
    @PatchMapping("/contrata/{individualId}/para/{jobId}")
    @RolesAllowed({RoleType.USER, RoleType.COMPANY})
    public String markAsHided(@PathVariable Long jobId, @PathVariable Long individualId, RedirectAttributes redirectAttributes) throws IOException {
        Optional<JobCandidate> oJobCandidate = jobCandidateService.findById(jobId, individualId);

        if (!oJobCandidate.isPresent()) {
            throw new EntityNotFoundException("Candidato não encontrado");
        }

        Optional<JobRequest> oJobRequest = jobRequestService.findById(jobId);

        if(!oJobRequest.isPresent()) {
            throw new EntityNotFoundException("Pedido não encontrado!");
        }

        //muda o estado para contratado, mas a espera de confirmação do profissional
        JobRequest jobRequest = oJobRequest.get();
        jobRequest.setStatus(JobRequest.Status.TO_HIRED);
        jobRequestService.save(jobRequest);

        //guarda a data de contratação
        Optional<JobContracted> oJobContracted = jobContractedService.findByJobRequest(jobRequest);
        if(!oJobContracted.isPresent()) {
            throw new EntityNotFoundException("O profissional não pode ser contratado!");
        }

        JobContracted jobContracted = oJobContracted.get();
        jobContracted.setHiredDate(new Date());
        jobContractedService.save(jobContracted);

        return "redirect:/minha-conta/cliente/meus-pedidos/"+jobId;
    }
}