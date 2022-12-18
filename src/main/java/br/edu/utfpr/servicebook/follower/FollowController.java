package br.edu.utfpr.servicebook.follower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/follow")
@Controller
public class FollowController {

    @Autowired
    FollowService followService;


    @PostMapping("/subscribe/{idFollowed}/{idFollower}")
    public String delete(@PathVariable("idFollowed") Long followedId, @PathVariable("idFollower") Long followerId, RedirectAttributes redirectAttributes) {

        //recebendo como parametro id profissional e cliente
        System.err.println("ENTROU NA ROTA SEGUIR...." + followedId + "  " + followerId);
        followService.follow(followedId, followerId);

        //JSON do bd simulando
//        {"idFollower" : 1,
//        "idFollower" : 2,}

        //implementar

        redirectAttributes.addFlashAttribute("msg", "Está seguindo!");
        return "redirect:/minha-conta/profissional";
    }

    //metodo seguir - com redirecionameto
    //get pagina lista de seguidores - pega seguidorees do banco e encaminha para uma pagina especifica o json e desmenbra o json no js
    //metodo deseguir
}
