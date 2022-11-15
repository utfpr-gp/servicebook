package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.IndividualMinDTO;
import br.edu.utfpr.servicebook.model.dto.JobCandidateDTO;
import br.edu.utfpr.servicebook.model.dto.JobCandidateMinDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestMinDTO;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.model.mapper.JobCandidateMapper;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.service.JobCandidateService;
import br.edu.utfpr.servicebook.service.JobRequestService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/candidaturas")
public class JobCandidateController {
    @Autowired
    private JobCandidateService jobCandidateService;

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

    @PostMapping
    public ModelAndView save(JobCandidateDTO dto, RedirectAttributes redirectAttributes) throws Exception {

        //simula um usuário autenticado
        String currentUserEmail = CurrentUserUtil.getCurrentUserEmail();

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
            redirectAttributes.addFlashAttribute("maxCandidatesReachedMessage", "Essa ordem de serviço já atingiu o número máximo de candidaturas.");
            return samePageView;
        }
    
        JobCandidate jobCandidate = new JobCandidate(oJobRequest.get(), oindividual.get());
        jobCandidateService.save(jobCandidate);

        redirectAttributes.addFlashAttribute("msg", "Candidatura realizada com sucesso!");

        return new ModelAndView("redirect:minha-conta/profissional");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String currentUserEmail = CurrentUserUtil.getCurrentUserEmail();

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
}