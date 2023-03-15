package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobCandidateMinDTO implements Serializable {

    private Long id;
    private String date;
    private String hiredDate;
    private Boolean chosenByBudget;
    private JobRequestFullDTO jobRequest;

}
