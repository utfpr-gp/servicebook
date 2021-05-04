package br.edu.utfpr.servicebook.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfessionDTO {
    private Long id;

    @NotBlank(message = "O nome da profissão é obrigatório")
    private String name;
}
