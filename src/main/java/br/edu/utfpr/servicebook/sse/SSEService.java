package br.edu.utfpr.servicebook.sse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
public class SSEService {

    @Autowired
    private InMemorySseEmitterRepository emitterRepository;

    /**
     * Cria um SseEmitter para o canal com o usuário.
     * Antes de retornar, salva a referência ao SseEmitter para pode enviar as mensagens posteriormente.
     * @param username
     * @return
     */

    public SseEmitter createChannel(String username) {
        SseEmitter emitter = new SseEmitter();
        emitterRepository.addEmitter(username, emitter);

        // On Client connection completion, unregister client specific emitter
        emitter.onCompletion(() -> this.emitterRepository.remove(username));

        // On Client connection timeout, unregister and mark complete client specific emitter
        emitter.onTimeout(() -> {
            emitter.complete();
            emitterRepository.remove(username);
        });

        return emitter;
    }

    /**
     * Tenta enviar a mensagem para o cliente. Caso o cliente esteja offline, remove o emissor e guarda
     * a mensagem no banco de dados.
     * @param memberId
     * @param dto
     */
    public void send(String memberId, EventSSE dto) {
        emitterRepository.get(memberId).ifPresent(emitter -> {
            try {
                emitter.send(dto);
            } catch (IOException e) {
                //TODO guarda a notificação no banco para enviar quando o usuário logar
                emitterRepository.remove(memberId);
            }
        });
    }
}
