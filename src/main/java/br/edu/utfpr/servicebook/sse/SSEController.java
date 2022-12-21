package br.edu.utfpr.servicebook.sse;

import br.edu.utfpr.servicebook.security.IAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/sse")
@Controller
public class SSEController {

    public static final Logger log =
            LoggerFactory.getLogger(SSEController.class);

    @Autowired
    SSEService sseService;

    @Autowired
    private IAuthentication authentication;

    /**
     * Realiza a criação de um canal para o envio de notificações para o cliente.
     * @return
     */
    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        String username = authentication.getEmail();
        System.err.println("Subscrevendo: " + username);
        return sseService.createChannel(username);
    }

    /**
     * Marca uma notificação como lida.
     * @param id
     * @param redirectAttributes
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        sseService.modifyStatusById(id);
        redirectAttributes.addFlashAttribute("msg", "Notificação Lida!");
        return "redirect:/minha-conta/cliente#disponiveis";
    }
}

