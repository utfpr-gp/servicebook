package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceDTO implements Serializable {

    private Long id;

    @NotBlank(message = "O nome do serviço é obrigatório")
    private String name;

    private String description;

    @NotNull(message = "O nome da especialidade é obrigatório")
    private Long expertiseId;

    /**
     * Usado para apresentação na listagem
     */
    private ExpertiseDTO expertise;

}
