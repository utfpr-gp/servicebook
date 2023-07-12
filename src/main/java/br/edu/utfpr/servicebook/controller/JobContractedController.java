package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.JobCandidateDTO;
import br.edu.utfpr.servicebook.model.dto.JobContractedConfirmDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestDetailsDTO;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.model.entity.JobContracted;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.JobCandidateMapper;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.service.JobCandidateService;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.service.JobRequestService;
import br.edu.utfpr.servicebook.sse.EventSSE;
import br.edu.utfpr.servicebook.sse.SSEService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/contratados")
public class JobContractedController {
    public static final Logger log = LoggerFactory.getLogger(JobContractedController.class);

    @Autowired
    private JobCandidateService jobCandidateService;

    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobRequestService jobRequestService;

    @Autowired
    private JobCandidateMapper jobCandidateMapper;

    @Autowired
    private JobRequestMapper jobRequestMapper;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private SSEService sseService;

    @Autowired
    private IAuthentication authentication;

    /**
     * O profissional confirma que realizará o serviço e define uma data.
     * O JobRequest passa para o estado de TO_DO.
     * O profissional também pode se negar a realizar o serviço, quando o estado passa para BUDGET.
     *
     * @param id
     * @param dto
     * @param redirectAttributes
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @PostMapping("/confirma/{id}")
    @RolesAllowed({RoleType.USER})
    public String markAsToDo(
            @PathVariable Long id,
            JobContractedConfirmDTO dto,
            RedirectAttributes redirectAttributes
    ) throws IOException, ParseException {
        String currentUserEmail = authentication.getEmail();

        Optional<Individual> oindividual = individualService.findByEmail(currentUserEmail);

        if(!oindividual.isPresent()){
            throw new EntityNotFoundException("O usuário não foi encontrado!");
        }

        Optional<JobContracted> oJobContracted = jobContractedService.getJobContractedByJobRequestId(id);
        if(!oJobContracted.isPresent()) {
            throw new EntityNotFoundException("Contrato não encontrado!");
        }

        JobContracted jobContracted = oJobContracted.get();

        if (dto.isConfirm()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            JobRequest jobRequest = jobContracted.getJobRequest();
            jobRequest.setStatus(JobRequest.Status.TO_DO);
            jobRequestService.save(jobRequest);

            jobContracted.setTodoDate(LocalDate.parse(dto.getTodoDate(), dateTimeFormatter));
            jobContractedService.save(jobContracted);
        } else {
            JobRequest jobRequest = jobContracted.getJobRequest();
            jobRequest.setStatus(JobRequest.Status.BUDGET);
            jobRequestService.save(jobRequest);
        }
//                * @param message
//                * @param serviceDescription descrição do serviço
//                * @param fromEmail email do usuário que realizou o evento
//                * @param fromName nome do usuário que realizou o evento
//                * @param toEmail email do destinatário do evento
        EventSSE eventSse = new EventSSE(EventSSE.Status.JOB_CONFIRMED,
                jobContracted.getJobRequest().getDescription(),
                currentUserEmail,
                authentication.getAuthentication().getName(),
                jobContracted.getJobRequest().getUser().getEmail());
        sseService.send(eventSse);

        redirectAttributes.addFlashAttribute("msg", "A atualização foi salva com sucesso!");

        return "redirect:/minha-conta/profissional/detalhes-servico/" + id;
    }
}