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

    @Column(name = "fromProfessionalName")
    private String fromProfessionalName;

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

    public String getDescriptionServ() {
        return descriptionServ;
    }

    public void setDescriptionServ(String descriptionServ) {
        this.descriptionServ = descriptionServ;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getToUserEmail() {
        return toUserEmail;
    }

    public void setToUserEmail(String toUserEmail) {
        this.toUserEmail = toUserEmail;
    }

    public String getFromProfessionalEmail() {
        return fromProfessionalEmail;
    }

    public void setFromProfessionalEmail(String fromProfessionalEmail) {
        this.fromProfessionalEmail = fromProfessionalEmail;
    }

    public String getFromProfessionalName() {
        return fromProfessionalName;
    }

    public void setFromProfessionalName(String fromProfessionalName) {
        this.fromProfessionalName = fromProfessionalName;
    }

    public Boolean getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public EventSse(Status message, String toUserEmail) {
        setMessage(getMessage(message)); // salva pegando o enum e convertendo para string
        setToUserEmail(toUserEmail);
        setReadStatus(false);
    }

    public EventSse(Status message, String descriptionServ, String fromProfessionalEmail, String fromProfessionalName, String toUserEmail) {
        setMessage(getMessage(message)); // salva pegando o enum e convertendo para string
        setDescriptionServ(descriptionServ);
        setFromProfessionalEmail(fromProfessionalEmail);
        setFromProfessionalName(fromProfessionalName);
        setToUserEmail(toUserEmail);
        setReadStatus(false);
    }

}
