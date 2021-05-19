package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDTO extends UserDTO {

    private Long id;

    private String cpf;

    private String description;

    private int rating;

    private int denounceAmount;
}