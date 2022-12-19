package br.edu.utfpr.servicebook.follower;

import br.edu.utfpr.servicebook.model.dto.*;
import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.mapper.IndividualMapper;
import br.edu.utfpr.servicebook.service.IndividualService;
import br.edu.utfpr.servicebook.service.UserService;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelIndividualDTO;
import br.edu.utfpr.servicebook.util.sidePanel.SidePanelUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.cloudinary.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/follow")
@Controller
public class FollowController {

    @Autowired
    private UserService userService;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    FollowService followService;

    @Autowired
    FollowRepository followRepository;

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

        //pegando objeto seguidores.
        Follow followObj = this.followService.getFollowedById(id.get());

        //lista de id seguidores
        JSONArray idFollowerList = new JSONArray(followObj.getFollowers_json());

//        List<Optional> userList = null;
        List<Optional> individualList = new ArrayList<>();

        for (int i = 0; i < idFollowerList.length(); i++) {
            System.out.println("(" + i + ") " + idFollowerList.getInt(i));
            String aux = String.valueOf(idFollowerList.getInt(i));

            Optional<Individual> individual =individualService.findById(Long.parseLong(aux));
            System.err.println("PRINTANDO OBJ USER..." + individual.get().toString());


            individualList.add(individualService.findById(Long.parseLong(aux)));
        }

        List<IndividualDTO> individualDTOS = individualList.stream()
                    .map(individual ->  individualMapper.toDto(individual))
                    .collect(Collectors.toList());

            mv.addObject("individuallist", individualDTOS);

        return mv;
    }


    //TODO fazer rota pra acessar pagina de um usuario especifico (no clientcontroller tem rota aprecida)


    private SidePanelIndividualDTO getSidePanelUser() throws Exception {
        Optional<Individual> client = (individualService.findByEmail(CurrentUserUtil.getCurrentUserEmail()));

        if (!client.isPresent()) {
            throw new Exception("Usuário não autenticado! Por favor, realize sua autenticação no sistema.");
        }
        IndividualDTO individualDTO = individualMapper.toDto(client.get());

        return SidePanelUtil.getSidePanelDTO(individualDTO);
    }
}
