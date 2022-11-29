package br.edu.utfpr.servicebook.sse;

import br.edu.utfpr.servicebook.util.CurrentUserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;

@RequestMapping("/sse")
@Controller
public class SSEController {

    public static final Logger log =
            LoggerFactory.getLogger(SSEController.class);

    public Map<String, SseEmitter> sseEmitters = new HashMap<>();

    @Autowired
    SSEService sseService;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        String username = CurrentUserUtil.getCurrentUserEmail();
        System.err.println("Subscrevendo: " + username);
        return sseService.createChannel(username);
    }

    private void sendInitEvent(SseEmitter sseEmitter){
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

