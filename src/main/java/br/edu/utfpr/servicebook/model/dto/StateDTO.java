package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StateDTO {
    private Long id;

    @NotEmpty(message = "O nome do estado é obrigatório")
    private String name;

    private String uf;
}
