package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobCandidateDTO implements Serializable {

    private Long id;
    private boolean isQuit;
    private boolean chosenByBudget;
    private JobRequestFullDTO jobRequest;
    private ProfessionalMinDTO professional;

}
