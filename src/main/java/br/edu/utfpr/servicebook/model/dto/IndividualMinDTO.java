package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualMinDTO extends UserDTO {

    private Long id;
    private String description;
    private Integer rating;
    private int followsAmount;

}
