package br.edu.utfpr.servicebook.sse;

import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
public class EventSSE {

    public enum Status {
        NEW_CANDIDATURE, JOB_CONFIRMED, JOB_CANCELED
    };

    private String id;
    private String message;

    private LocalDateTime localDateTime = LocalDateTime.now();

    public EventSSE(Status status){
        this.message = getMessage(status);
    }

    private String getMessage(Status status){
        if(status == Status.NEW_CANDIDATURE){
            return "Uma nova candidatura";
        }
        else if (status == Status.JOB_CONFIRMED){
            return "Profissional confirmou a realização do serviço";
        }
        return "Mensagem desconhecida";
    }
}
