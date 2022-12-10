package br.edu.utfpr.servicebook.sse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
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
     * @param eventSseDto
     */
    public void send(EventSse eventSseDto) {
        inMemorySseEmitterRepository.get(eventSseDto.getToUserEmail()).ifPresent(emitter -> {
            try {
                emitter.send(eventSseDto);
                System.err.println("enviando emiter: " + eventSseDto);

                eventSeeRepository.save(eventSseDto);
                System.err.println("user online...salvando em banco emiter: " + eventSseDto);
            } catch (IOException e) {
                inMemorySseEmitterRepository.remove(eventSseDto.getToUserEmail());//esta removendo pois não esta logado
            }
        });
        if (!inMemorySseEmitterRepository.get(eventSseDto.getToUserEmail()).isPresent()){
            eventSeeRepository.save(eventSseDto);
            System.err.println("user offline...salvando em banco emiter: " + eventSseDto);
        }
    }

    public List<EventSse> findByEmail(String toUserEmail) {
        return this.eventSeeRepository.findByEmail(toUserEmail);
    }

    public void modifyStatusById(Long id) {
        this.eventSeeRepository.modifyStatusById(id);
    }
}
