package br.edu.utfpr.servicebook.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExpertiseDTO {
    private Long id;

    @NotBlank(message = "O nome da especialidade é obrigatório")
    private String name;
}
