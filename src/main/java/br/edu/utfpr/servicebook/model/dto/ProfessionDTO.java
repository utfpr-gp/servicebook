package br.edu.utfpr.servicebook.model.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfessionDTO {
    private long id;

    @NotEmpty(message = "O nome da profissão é obrigatório")
    private String name;
}
