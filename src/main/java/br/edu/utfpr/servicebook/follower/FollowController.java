package br.edu.utfpr.servicebook.follower;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.cloudinary.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
        System.err.println("ENTROU NA ROTA SEGUIR...." + followedId + "  " + followerId);

        try {
            Follow followObj = followRepository.findFollowsByidFollowed(followedId);

            if (followObj == null ){
                System.err.println("ENTROU NO IF DO CONTROLER... ");
                //metodo para criar uma nova inscrição
                Follow followNew = followService.followNewClient(followedId, followerId);
                followService.save(followNew);
            }else {
                System.err.println("ENTROU NO ELSE DO CONTROLER... ");
                //metodo para fazeer uma inscrição existente update
                Follow followisClient = followService.followisClient(followObj, followerId);
                //this.followService.save(f);
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

    //get pagina lista de seguidores - pega seguidorees do banco e encaminha para uma pagina especifica o json e desmenbra o json no js
    //metodo deseguir
}
