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
public class EventSseDTO implements Serializable {

    private Long id;

    private String message;

    private String descriptionServ;

    private String toUserEmail;

    private String fromProfessionalEmail;

    private String fromProfessionalName;

    private Boolean readStatus;


}
