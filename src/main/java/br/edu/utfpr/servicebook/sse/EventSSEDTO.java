package br.edu.utfpr.servicebook.sse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EventSSEDTO implements Serializable {

    private Long id;

    private String message;

    private String descriptionServ;

    /**
     * Email de destino que é usado como identificador do canal SSE.
     */
    private String toEmail;

    /**
     * Email do usuário que originou um evento.
     */
    private String fromEmail;

    /**
     * Nome do usuário que originou um evento.
     */
    private String fromName;

    /**
     * Informa se a notificação foi lida ou não pelo usuário.
     */
    private Boolean readStatus;


}
