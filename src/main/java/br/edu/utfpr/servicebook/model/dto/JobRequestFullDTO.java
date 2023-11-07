package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestFullDTO implements Serializable {

    private Long id;
    private ExpertiseMinDTO expertise;
    private String dateCreated;
    private ClientMinDTO individual;
    private String dateTarget;
    private Integer quantityCandidatorsMax;
    private String description;
    private Long totalCandidates;
    private String textualDate;
    private Long amountOfCandidates;

    public String status;

}
