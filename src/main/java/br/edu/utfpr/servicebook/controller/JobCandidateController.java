package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.IndividualMinDTO;
import br.edu.utfpr.servicebook.model.dto.JobCandidateDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView save(JobCandidateDTO dto, RedirectAttributes redirectAttributes) {
        //Dados fixos por enquanto, pois ainda nao existe TOKEN de autenticaçao para pegar os dados
        Long idIndividual = 1L;
        Optional<Long> maxCandidates = Optional.of(20L);
        //---------------------------------------

        JobRequest jobRequest = jobRequestService.findById(dto.getId()).get();
        Individual individual = individualService.findById(idIndividual).get();

        JobRequestMinDTO jobRequestMinDTO = jobRequestMapper.toMinDto(jobRequest, maxCandidates);
        dto.setJobRequest(jobRequestMinDTO);

        IndividualMinDTO individualMinDTO = individualMapper.toMinDto(individual);
        dto.setIndividual(individualMinDTO);

        JobCandidate jobCandidate = jobCandidateMapper.toEntity(dto);
        jobCandidate.setJobRequest(jobRequest);
        jobCandidate.setIndividual(individual);

        Set<JobCandidate> jobCandidates = jobRequest.getJobCandidates();
        jobCandidates.add(jobCandidate);

        jobRequest.setJobCandidates(jobCandidates);

        jobRequestService.save(jobRequest);
        jobCandidateService.save(jobCandidate);

        redirectAttributes.addFlashAttribute("msg", "Candidatura realizada com sucesso!");

        return new ModelAndView("redirect:minha-conta/profissional");
    }
}