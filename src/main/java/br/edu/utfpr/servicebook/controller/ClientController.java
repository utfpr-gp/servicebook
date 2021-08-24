package br.edu.utfpr.servicebook.controller;

import br.edu.utfpr.servicebook.model.dto.CityDTO;
import br.edu.utfpr.servicebook.model.dto.ClientDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestMinDTO;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.ClientMapper;
import br.edu.utfpr.servicebook.model.mapper.JobRequestMapper;
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
import br.edu.utfpr.servicebook.model.entity.Client;
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
    private JobRequestService jobRequestService;

    @Autowired
    private JobCandidateService jobCandidateService;

    @Autowired
    private JobRequestMapper jobRequestMapper;

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

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) throws IOException {

        Optional<Client> client = Optional.ofNullable(clientService.findByEmailAddress(CurrentUserUtil.getCurrentUserEmail()));

        if (!client.isPresent()) {
            throw new IOException("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }

        Optional<JobRequest> jobRequest = this.jobRequestService.findById(id);

        if(!jobRequest.isPresent()) {
            throw new EntityNotFoundException("Solicitação não foi encontrada pelo id informado.");
        }

        Long jobRequestClientId = jobRequest.get().getClient().getId();
        Long clientId = client.get().getId();

        if(jobRequestClientId != clientId){
            throw new EntityNotFoundException("Você não ter permissão para deletar essa solicitação.");
        }

        this.jobRequestService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "Solicitação deletada!");

        return "redirect:/minha-conta/meus-pedidos";

    }

}

