package br.edu.utfpr.servicebook.sse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Service
public class SSEService {

    @Autowired
    private InMemorySseEmitterRepository inMemorySseEmitterRepository;

    @Autowired
    private EventSeeRepository eventSeeRepository;

    /**
     * Cria um SseEmitter para o canal com o usuário.
     * Antes de retornar, salva a referência ao SseEmitter para pode enviar as mensagens posteriormente.
     * @param username
     * @return
     */
    public SseEmitter createChannel(String username) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);//verificar o uso do long para não dar timeout
        inMemorySseEmitterRepository.addEmitter(username, emitter);

        // On Client connection completion, unregister client specific emitter
        emitter.onCompletion(() -> this.inMemorySseEmitterRepository.remove(username));

        // On Client connection timeout, unregister and mark complete client specific emitter
        emitter.onTimeout(() -> {
            emitter.complete();
            inMemorySseEmitterRepository.remove(username);
            System.err.println("SSEService> emitter.ontimeout... removeu: " + username );
        });

        return emitter;
    }

    /**
     * Tenta enviar a mensagem para o cliente. Caso o cliente esteja offline, remove o emissor e guarda
     * a mensagem no banco de dados.
     * @param eventSSEDTO
     */
    public void send(EventSSE eventSSEDTO) {
        inMemorySseEmitterRepository.get(eventSSEDTO.getToEmail()).ifPresent(emitter -> {
            try {
                emitter.send(eventSSEDTO);
                System.err.println("enviando emiter: " + eventSSEDTO);

                eventSeeRepository.save(eventSSEDTO);
                //salvar o evento como lido no banco para futuramente criar pagina notificações antigas
                System.err.println("user online...salvando em banco emiter: " + eventSSEDTO);
            } catch (Exception e) {
                //esta removendo pois não esta logado
                inMemorySseEmitterRepository.remove(eventSSEDTO.getToEmail());
                eventSeeRepository.save(eventSSEDTO);
                System.err.println("user offline...salvando em banco emiter: " + eventSSEDTO);
            }
        });
    }

    /**
     * Busca os eventos de notificação pendentes para um dado usuário.
     * @param toEmail
     * @return
     */
    public List<EventSSE> findPendingEventsByEmail(String toEmail) {
        return this.eventSeeRepository.findPendingEventsByEmail(toEmail);
    }

    /**
     * Modifica o status de leitura de uma notificação referente a um dado usuário.
     * @param id
     */
    public void modifyStatusById(Long id) {
        this.eventSeeRepository.modifyStatusById(id);
    }
}
