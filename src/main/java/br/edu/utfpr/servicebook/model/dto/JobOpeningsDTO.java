package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobOpeningsDTO implements Serializable {


    private Long id;

    @NotBlank(message = "O título da vaga é obrigatório")
    private String title;

    @NotNull(message = "A especialidade é obrigatória")
    private Long expertiseId;

    @NotBlank(message = "A descrição da vaga é obrigatória")
    private String description;

    private Long salary;

    private ExpertiseDTO expertise;

}