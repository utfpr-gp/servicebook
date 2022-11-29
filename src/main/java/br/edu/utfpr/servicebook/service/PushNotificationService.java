package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Individual;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import org.cloudinary.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
 * CLASSE PARA REGISTRAAR USUARIO E MANDAR NOTIFICAÇÕES
 *
 *
 * PODE SER CHAMADO EM QUALQUER CLASSE/CONTROLER
 * MONTA QUALQUER NOTIFICAÇÃO(OUTRO METOODO QUE ESTA CHAMANDO MANDA O TITULO, TEXTO E USUARIO)
 * SE O USUARIO DE DESTINO ESTIVER COM A SESÃO LOGADA RECEBE A NOTIFICAÇÃO.
 * OBJETO NOTIFICAÇÃO TERIA OS PARAMETROS (USUARIO DESTINO, TITULO, TEXTO, LIDO OU NÃO)
 *
 * */

@Service
public class PushNotificationService {

    public Map<String, SseEmitter> emittersList = new HashMap<>();


    // method for client subsciption
    //verificar paramentro de indentificação se é email ou sesão
    public SseEmitter enableNotifier(@RequestParam String email){
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sendInitEvent(sseEmitter);


        //TESTE
        System.err.println("SSEEMITER INICIADO COM EMAIL>>> ...  " + email);

        emittersList.put(email, sseEmitter);
        sseEmitter.onCompletion(()-> emittersList.remove(sseEmitter));
        sseEmitter.onTimeout(()-> emittersList.remove(sseEmitter));
        sseEmitter.onError((e)-> emittersList.remove(sseEmitter));
        return sseEmitter;
    }

    //method for dispatching events to all clients
    //@PostMapping("/dispachEvent")
//    public void dispatchToAllClients (@RequestParam String title, @RequestParam String text){
//
//        String eventFormatted = new JSONObject().put("title", title).put("text", text).toString();
//        for (SseEmitter emitter: emitters) {
//            try {
//                emitter.send(SseEmitter.event().name("latestNews").data(eventFormatted));
//            } catch (IOException e){
//                emitters.remove(emitter);
//            }
//
//        }
//
//    }


    //method for dispatchig to specific client
    //@PostMapping("/dispachEvent")
    public void dispatchToClient (@RequestParam Optional<Individual> individual, @RequestParam Optional<JobRequest> jobRequest){

        String emailClient = jobRequest.get().getIndividual().getEmail();
        String descricaoServ = jobRequest.get().getDescription();
        String nomePrestador = individual.get().getName();

        String title = "Você tem uma nova candidatura para o serviço: " + descricaoServ;
        String text = nomePrestador + "< se candidatou para o serviço";

        String eventFormatted = new JSONObject().put("title", title).put("text", text).toString();

        //String eventFormatted = title + "\nl" + text;

        System.err.println("CRIADNO SSEMITER....L80 " + eventFormatted);

        //sourceNotificação(eventFormatted, emailClient);
        SseEmitter sseEmitter = emittersList.get(emailClient);
        if(sseEmitter != null){
            try {
                sseEmitter.send(SseEmitter.event().name("pushNotifications").data(eventFormatted));
            } catch (IOException e){
                emittersList.remove(sseEmitter);
            }
        }
    }

    /*public void sourceNotificação(@RequestParam String eventFormatted, @RequestParam String email){
        SseEmitter sseEmitter = emittersList.get(email);
        if(sseEmitter != null){
            try {
                sseEmitter.send(SseEmitter.event().name("pushNotifications").data(eventFormatted));
            } catch (IOException e){
                emittersList.remove(sseEmitter);
            }
        }
    }*/


    private void sendInitEvent(SseEmitter sseEmitter){
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
