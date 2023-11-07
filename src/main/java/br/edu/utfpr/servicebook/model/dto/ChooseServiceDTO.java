package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO usado para escolher um profissional escolher um serviço para o seu portfólio.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChooseServiceDTO implements Serializable {

    @NotBlank(message = "Você deve escolher um serviço")
    private Long id;

    private String name;

    private String description;

    @NotNull(message = "O nome da especialidade é obrigatório")
    private Long expertiseId;

    /**
     * Usado para apresentação na listagem
     */
    private ExpertiseDTO expertise;

}
