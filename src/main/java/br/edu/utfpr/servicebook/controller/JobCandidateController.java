package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.JobCandidateDTO;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.JobCandidateMapper;
import br.edu.utfpr.servicebook.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.*;


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
    private ProfessionalService professionalService;

    @PostMapping
    public ModelAndView save(JobCandidateDTO dto, RedirectAttributes redirectAttributes) {
        JobCandidate jobCandidate = jobCandidateMapper.toEntity(dto);

        Professional professional = professionalService.findById(1L).get();
        jobCandidate.setProfessional(professional);

        JobRequest jobRequest = jobRequestService.findById(dto.getId()).get();
        jobCandidate.setJobRequest(jobRequest);

        Set<JobCandidate> jobCandidates = jobRequest.getJobCandidates();
        jobCandidates.add(jobCandidate);

        jobRequest.setJobCandidates(jobCandidates);

        jobRequestService.save(jobRequest);
        jobCandidateService.save(jobCandidate);

        redirectAttributes.addFlashAttribute("msg", "Candidatura realizada com sucesso!");

        return new ModelAndView("redirect:minha-conta/profissional");
    }
}
