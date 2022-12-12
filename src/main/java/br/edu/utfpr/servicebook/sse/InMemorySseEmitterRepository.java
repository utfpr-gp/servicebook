package br.edu.utfpr.servicebook.sse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemorySseEmitterRepository {

    public static final Logger log =
            LoggerFactory.getLogger(InMemorySseEmitterRepository.class);

    /**
     * Lista de emissores de notificação.
     * O identificador é o email e o valor é o objeto de emissão.
     * Apenas haverá um emissor de notificação por email.
     * FIXME quando tiver muitos usuários, esta solução de guardar em memória se tornará imprópria.
     */
    private Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    /**
     * Seta o emissor corrente para o email.
     * @param memberId
     * @param emitter
     */
    public void addEmitter(String memberId, SseEmitter emitter) {
        emitters.put(memberId, emitter);
    }

    public void remove(String memberId) {
        if (emitters != null && emitters.containsKey(memberId)) {
            emitters.remove(memberId);
        } else {
            log.info("No emitter to remove for member: {}", memberId);
        }
    }

    /**
     * Retorna o emissor para o respectivo email, quanso houver.
     * @param memberId
     * @return
     */
    public Optional<SseEmitter> get(String memberId) {
        return Optional.ofNullable(emitters.get(memberId));
    }
}
