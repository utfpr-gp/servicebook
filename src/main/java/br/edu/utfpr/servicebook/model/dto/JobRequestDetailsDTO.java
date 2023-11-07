package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDetailsDTO implements Serializable {

    private Long id;
    private ExpertiseMinDTO expertise;
    private String dateCreated;
    private UserDTO user;
    private String dateTarget;
    private Integer quantityCandidatorsMax;
    private String description;
    private Long totalCandidates;
    private String textualDate;
    private Long amountOfCandidates;

}
