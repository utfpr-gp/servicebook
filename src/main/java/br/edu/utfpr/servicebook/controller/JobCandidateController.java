package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.Company;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.model.entity.JobContracted;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.JobCandidateMapper;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
import br.edu.utfpr.servicebook.security.IAuthentication;
import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.service.CompanyService;
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
import java.util.Optional;

@Controller
@RequestMapping("/candidaturas")
public class JobCandidateController {
    public static final Logger log = LoggerFactory.getLogger(JobCandidateController.class);

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

    @PostMapping
    @RolesAllowed({RoleType.USER})
    public ModelAndView save(JobCandidateDTO dto, RedirectAttributes redirectAttributes) {

        //simula um usuário autenticado
        String currentUserEmail = authentication.getEmail();

        Optional<Individual> oindividual = individualService.findByEmail(currentUserEmail);

        if(!oindividual.isPresent()){
            throw new EntityNotFoundException("O usuário não foi encontrado!");
        }

        Optional<JobRequest> oJobRequest = jobRequestService.findById(dto.getId());

        if(!oJobRequest.isPresent()){
            throw new EntityNotFoundException("A ordem de serviço não foi encontrada!");
        }

        int numberOfCandidacies = oJobRequest.get().getJobCandidates().size();
        int maxCandidaciesAllowed = oJobRequest.get().getQuantityCandidatorsMax();
        if (numberOfCandidacies == maxCandidaciesAllowed) {
            ModelAndView samePageView = new ModelAndView("redirect:minha-conta/profissional/detalhes-servico/" + dto.getId());
            redirectAttributes.addFlashAttribute("candidacyApplicationErrorMessage", "Essa ordem de serviço já atingiu o número máximo de candidaturas.");
            return samePageView;
        }

        JobRequestDetailsDTO jobFull = jobRequestMapper.jobRequestDetailsDTO(oJobRequest.get());

        //envia a notificação SSE
        EventSSE eventSse = new EventSSE(EventSSE.Status.NEW_CANDIDATURE, jobFull.getDescription().toString(), currentUserEmail, jobFull.getUser().getName(), jobFull.getUser().getEmail());
        sseService.send(eventSse);

        redirectAttributes.addFlashAttribute("msg", "Candidatura realizada com sucesso!");

        return new ModelAndView("redirect:minha-conta/profissional");
    }

    /**
     * Usuário cancela a candidatura a um serviço.
     * @param id
     * @param redirectAttributes
     * @return
     */
    @DeleteMapping("/{id}")
    @RolesAllowed({RoleType.USER})
    public String deleteJobRequest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String currentUserEmail = authentication.getEmail();

        Optional<Individual> oindividual = individualService.findByEmail(currentUserEmail);
        if(!oindividual.isPresent()){
            throw new EntityNotFoundException("O usuário não foi encontrado!");
        }

        Optional<JobCandidate> oJobCandidate = jobCandidateService.findById(id, oindividual.get().getId());
        if(!oJobCandidate.isPresent()) {
            throw new EntityNotFoundException("Candidatura não encontrada!");
        }

        jobCandidateService.delete(id, oindividual.get().getId());
        redirectAttributes.addFlashAttribute("msg", "Candidatura cancelada com sucesso!");

        return "redirect:/minha-conta/profissional#emDisputa";
    }

    /**
     * Usuário cancela a candidatura a um serviço
     * FIXME Não pode deletar o JobRequest, apenas a candidatura.
     * @param id
     * @param redirectAttributes
     * @return
     */
    @DeleteMapping("/desistir/{id}")
    @RolesAllowed({RoleType.USER})
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String currentUserEmail = authentication.getEmail();

        Optional<Individual> oindividual = individualService.findByEmail(currentUserEmail);
        if(!oindividual.isPresent()){
            throw new EntityNotFoundException("O usuário não foi encontrado!");
        }

        Optional<JobRequest> oJobRequest = jobRequestService.findById(id);
        if(!oJobRequest.isPresent()) {
            throw new EntityNotFoundException("Candidatura não encontrada!");
        }
        jobRequestService.delete(id);

        redirectAttributes.addFlashAttribute("msg", "Candidatura cancelada com sucesso!");

        return "redirect:/minha-conta/profissional#disponiveis";
    }



}