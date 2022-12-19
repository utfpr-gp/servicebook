package br.edu.utfpr.servicebook.follower;

import br.edu.utfpr.servicebook.sse.EventSse;
import br.edu.utfpr.servicebook.sse.EventSseDTO;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.cloudinary.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/follow")
@Controller
public class FollowController {

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


    @GetMapping("/{id}")
    public ModelAndView showFollows(@PathVariable Optional<Long> id) throws Exception {
        ModelAndView mv = new ModelAndView("?");

        //metodo que gera lista
        return mv;
    }


//    List<EventSse> eventSsesList = sseService.findPendingEventsByEmail(CurrentUserUtil.getCurrentUserEmail());
//    List<EventSseDTO> eventSseDTOS = eventSsesList.stream()
//            .map(eventSse -> {
//                return eventSseMapper.toFullDto(eventSse);
//            })
//            .collect(Collectors.toList());
//        mv.addObject("eventsse", eventSseDTOS);
    //get pagina lista de seguidores - pega seguidorees do banco e encaminha para uma pagina especifica o json e desmenbra o json no js
}
