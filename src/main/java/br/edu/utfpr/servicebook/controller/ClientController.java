package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.*;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/minha-conta/meus-pedidos")
@Controller
public class ClientController {

    public static final Logger log =
            LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

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
    private ProfessionalService professionalService;

    @Autowired
    private ProfessionalMapper professionalMapper;
    @GetMapping
    public ModelAndView show() throws Exception {
        ModelAndView mv = new ModelAndView("client/my-requests");

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        ClientDTO clientDTO = clientMapper.toDto(client.get());
        mv.addObject("client", clientDTO);

        List<JobRequest> jobRequests = jobRequestService.findByClientOrderByDateCreatedDesc(client.get());

        List<JobRequestMinDTO> jobRequestDTOs = jobRequests.stream()
                .map(job -> {
                    Optional <Long> amountOfCandidates = jobCandidateService.countByJobRequest(job);

                    if(amountOfCandidates.isPresent()){
                        return jobRequestMapper.toMinDto(job, amountOfCandidates);
                    }
                    return jobRequestMapper.toMinDto(job, Optional.ofNullable(0L));
                })
                .collect(Collectors.toList());

        mv.addObject("jobRequests", jobRequestDTOs);

        return mv;
    }
























    @GetMapping("/{id}")
    public ModelAndView showDetailsRequest(@PathVariable Optional<Long> id) throws Exception {
        ModelAndView mv = new ModelAndView("client/details-request");

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        Optional<JobRequest> job = jobRequestService.findById(id.get());

        if (!job.isPresent()) {
            throw new EntityNotFoundException("Solicitação de serviço não encontrado. Por favor, tente novamente.");
        }

        JobRequestFullDTO jobDTO = jobRequestMapper.toFullDto(job.get());
        mv.addObject("jobRequest", jobDTO);

        Long expertiseId = job.get().getExpertise().getId();

        Optional<Expertise> Expertise = expertiseService.findById(expertiseId);

        if (!Expertise.isPresent()) {
            throw new EntityNotFoundException("A especialidade não foi encontrada. Por favor, tente novamente.");
        }

        ExpertiseMinDTO expertiseDTO = expertiseMapper.toMinDto(Expertise.get());
        mv.addObject("expertise", expertiseDTO);

        List<JobCandidate> jobCandidates = jobCandidateService.findByJobRequest(job.get());

        List<JobCandidateDTO> jobCandidatesDTOs = jobCandidates.stream()
                .map(candidate -> jobCandidateMapper.toDto(candidate))
                .collect(Collectors.toList());

        mv.addObject("candidates", jobCandidatesDTOs);


        return mv;
    }
}


