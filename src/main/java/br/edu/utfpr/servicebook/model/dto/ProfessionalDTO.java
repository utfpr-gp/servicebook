package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDTO extends UserDTO {

    private Long id;

    @CPF(message = "CPF inválido!")
    private String cpf;

    @NotBlank(message = "O campo descrição é obrigatório")
    private String description;

    private int rating;

    private int denounceAmount;
}