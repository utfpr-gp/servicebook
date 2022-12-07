package br.edu.utfpr.servicebook.sse;

import javax.persistence.*;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "eventsses")
@Entity
@NoArgsConstructor
public class EventSse {

    public enum Status {
        NEW_CANDIDATURE, JOB_CONFIRMED, JOB_CANCELED
    };

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "descriptionServ")
    private String descriptionServ;

    @Column(name = "localDateTime")
    private LocalDateTime localDateTime = LocalDateTime.now();

    @Column(name = "toUserEmail")
    private String toUserEmail;

    @Column(name = "fromProfessionalEmail")
    private String fromProfessionalEmail;

    @Column(name = "readStatus")
    private Boolean readStatus;

    /*public EventSse(Status status){
        this.message = getMessage(status);
    }*/

    private String getMessage(Status status){
        if(status == Status.NEW_CANDIDATURE){
            return "Uma nova candidatura";
        }
        else if (status == Status.JOB_CONFIRMED){
            return "Profissional confirmou a realização do serviço";
        }
        return "Mensagem desconhecida";
    }

    public EventSse(Status message, String toUserEmail) {
        setMessage(getMessage(message)); // salva pegando o enum e convertendo para string
        setToUserEmail(toUserEmail);
        setReadStatus(false);
    }

    public EventSse(Status message, String descriptionServ, String fromProfessionalEmail, String toUserEmail) {
        setMessage(getMessage(message)); // salva pegando o enum e convertendo para string
        setDescriptionServ(descriptionServ);
        setFromProfessionalEmail(fromProfessionalEmail);
        setToUserEmail(toUserEmail);
        setReadStatus(false);
    }

}
