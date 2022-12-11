package br.edu.utfpr.servicebook.sse;

import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.*;

@RequestMapping("/sse")
@Controller
public class SSEController {

    public static final Logger log =
            LoggerFactory.getLogger(SSEController.class);
    @Autowired
    SSEService sseService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        String username = CurrentUserUtil.getCurrentUserEmail();
        System.err.println("Subscrevendo: " + username);
        return sseService.createChannel(username);
    }

    //marca como lido a notificação
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        sseService.modifyStatusById(id);
        redirectAttributes.addFlashAttribute("msg", "Notificação Lida!");
        return "redirect:/minha-conta/cliente#disponiveis";
    }
}

