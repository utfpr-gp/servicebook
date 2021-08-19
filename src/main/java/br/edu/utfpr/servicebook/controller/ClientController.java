package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.JobRequestMinDTO;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
import br.edu.utfpr.servicebook.service.*;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.edu.utfpr.servicebook.model.entity.Client;
import org.springframework.web.servlet.ModelAndView;
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
    private JobRequestService jobRequestService;

    @Autowired
    private JobCandidateService jobCandidateService;

    @Autowired
    private JobRequestMapper jobRequestMapper;

    @GetMapping
    public ModelAndView show() {
        ModelAndView mv = new ModelAndView("client/my-requests");

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

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


}


