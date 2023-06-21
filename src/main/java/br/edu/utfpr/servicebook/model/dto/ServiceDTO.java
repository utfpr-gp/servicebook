package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceDTO implements Serializable {
    // 1 especialidade x n serviços
    private Long id;

    @NotBlank(message = "O nome do serviço é obrigatório")
    private String name;

    private String description;

    private ExpertiseDTO
            expertise;
    @Valid
    private Long expertise_id;
}
