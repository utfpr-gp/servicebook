package br.edu.utfpr.servicebook.sse;

import javax.persistence.*;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "eventsses")
@Entity
@NoArgsConstructor
public class EventSSE {

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

    @Column(name = "toEmail")
    private String toEmail;

    @Column(name = "fromEmail")
    private String fromEmail;

    @Column(name = "fromName")
    private String fromName;

    @Column(name = "readStatus")
    private Boolean readStatus;

    /**
     * Cria um evento de notificação
     * @param message
     * @param serviceDescription descrição do serviço
     * @param fromEmail email do usuário que realizou o evento
     * @param fromName nome do usuário que realizou o evento
     * @param toEmail email do destinatário do evento
     */
    public EventSSE(Status message, String serviceDescription, String fromEmail, String fromName, String toEmail) {
        setMessage(getMessage(message)); // salva pegando o enum e convertendo para string
        setDescriptionServ(serviceDescription);
        setFromEmail(fromEmail);
        setFromName(fromName);
        setToEmail(toEmail);
        setReadStatus(false);
    }

    /**
     * Retorna a mensagem padronizada a ser enviada ao usuário de acordo com o tipo de evento.
     * @param status
     * @return
     */
    private String getMessage(Status status){
        switch (status){
            case JOB_CANCELED :
                return "Profissional cancelou a realização do serviço";
            case NEW_CANDIDATURE :
                return "Uma nova candidatura";
            case JOB_CONFIRMED:
                return "Profissional confirmou a realização do serviço";
            default: return "Mensagem desconhecida";
        }
    }

}
