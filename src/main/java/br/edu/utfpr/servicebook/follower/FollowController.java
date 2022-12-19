package br.edu.utfpr.servicebook.follower;

import br.edu.utfpr.servicebook.model.dto.ExpertiseMinDTO;
import br.edu.utfpr.servicebook.model.dto.IndividualDTO;
import br.edu.utfpr.servicebook.model.dto.JobCandidateDTO;
import br.edu.utfpr.servicebook.model.dto.JobRequestFullDTO;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelIndividualDTO;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/follow")
@Controller
public class FollowController {

    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    FollowService followService;

    @Autowired
    FollowRepository followRepository;


    //metodo seguir - com redirecionameto
    @PostMapping("/subscribe/{idFollowed}/{idFollower}")
    public String save(@PathVariable("idFollowed") Long followedId, @PathVariable("idFollower") Long followerId, RedirectAttributes redirectAttributes) throws JsonProcessingException {

        try {
            Follow followObj = followRepository.findFollowsByidFollowed(followedId);

            if (followObj == null ){
                Follow followNew = followService.followNewClient(followedId, followerId);
                followService.save(followNew);
            }else {
                Follow followisClient = followService.followisClient(followObj, followerId);
                followService.save(followisClient);
            }
        }catch (RuntimeException e){
            System.err.println("ERRO EXECPTION USUARIO JA INSCRITO");
            redirectAttributes.addFlashAttribute("msgError", "Já Está seguindo!");
            return "redirect:/minha-conta/profissional";
        }

        redirectAttributes.addFlashAttribute("msg", "Está seguindo!");
        return "redirect:/minha-conta/profissional";
    }

    @DeleteMapping("/unfollow/{idFollowed}/{idFollower}")
    public String delete(@PathVariable("idFollowed") Long followedId, @PathVariable("idFollower") Long followerId, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        try {
            Follow followObj = followRepository.findFollowsByidFollowed(followedId);

            if (followObj != null ){
                Follow followisClient = followService.unFollowisClient(followObj, followerId);
                followService.save(followisClient);
            }else {
                throw new RuntimeException("ID NÃO ENCOTRADO");
            }

        }catch (RuntimeException e){
            System.err.println("ERRO EXECPTION - USUARIO NÃO ENCONTRADO");
            redirectAttributes.addFlashAttribute("msgError", "Não encontrado!");
            return "redirect:/minha-conta/profissional";
        }

        redirectAttributes.addFlashAttribute("msg", "Deixou de seguir!");
        return "redirect:/minha-conta/profissional";
    }


    //TODO rota para listar os seguidores tendo com oparametro da rota o id do usuario seguido (followed)
    @GetMapping("/{id}")
    public ModelAndView showFollows(@PathVariable Optional<Long> id) throws Exception {
        System.err.println("entoru na rota listagem seguidores....");
        ModelAndView mv = new ModelAndView("professional/details-followers");


            mv.addObject("user", this.getSidePanelUser());

            // painel retornando ok

          /* Optional<JobRequest> job = jobRequestService.findById(id.get());

            if (!job.isPresent()) {
                throw new EntityNotFoundException("Solicitação de serviço não encontrado. Por favor, tente novamente.");
            }

            JobRequestFullDTO jobDTO = jobRequestMapper.toFullDto(job.get());
            mv.addObject("jobRequest", jobDTO);

            Long expertiseId = job.get().getExpertise().getId();

            Optional<Expertise> expertise = expertiseService.findById(expertiseId);

            if (!expertise.isPresent()) {
                throw new EntityNotFoundException("A especialidade não foi encontrada. Por favor, tente novamente.");
            }

            ExpertiseMinDTO expertiseDTO = expertiseMapper.toMinDto(expertise.get());
            mv.addObject("expertise", expertiseDTO);*/

//            List<JobCandidate> jobCandidates = jobCandidateService.findByJobRequestOrderByChosenByBudgetDesc(job.get());
//
//            List<JobCandidateDTO> jobCandidatesDTOs = jobCandidates.stream()
//                    .map(candidate -> jobCandidateMapper.toDto(candidate))
//                    .collect(Collectors.toList());
//
//            mv.addObject("candidates", jobCandidatesDTOs);
//            boolean isClient = true;
//            mv.addObject("isClient", isClient);


        return mv;
    }






    //TODO fazer rota pra acessar pagina de um usuario especifico (no clientcontroller tem rota aprecida)
    //metodo que gera lista
    // ver metodo show do ClientController
    //        Optional<Individual> individual = individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()); - suar findbyID -passar o id do json


//    List<EventSse> eventSsesList = sseService.findPendingEventsByEmail(CurrentUserUtil.getCurrentUserEmail());
//    List<EventSseDTO> eventSseDTOS = eventSsesList.stream()
//            .map(eventSse -> {
//                return eventSseMapper.toFullDto(eventSse);
//            })
//            .collect(Collectors.toList());
//        mv.addObject("eventsse", eventSseDTOS);
    //get pagina lista de seguidores - pega seguidorees do banco e encaminha para uma pagina especifica o json e desmenbra o json no js


    private SidePanelIndividualDTO getSidePanelUser() throws Exception {
        Optional<Individual> client = (individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }
        IndividualDTO individualDTO = individualMapper.toDto(client.get());

        return SidePanelUtil.getSidePanelDTO(individualDTO);
    }
}
