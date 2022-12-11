package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDetailsDTO implements Serializable {

    private Long id;
    private ExpertiseMinDTO expertise;
    private String dateCreated;
    private ClientDTO individual;
    private String dateExpired;
    private Integer quantityCandidatorsMax;
    private String description;
    private Long totalCandidates;
    private String textualDate;
    private Long amountOfCandidates;
    private Optional<Boolean> reviewable = Optional.of(false);
}
