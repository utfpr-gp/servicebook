
package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobRequestMinDTO implements Serializable {

    private Long id;

    private ExpertiseDTO expertiseDTO;

    private String dateCreated;

    private String dateExpired;

    public String status;

    private String description;

    private Long amountOfCandidates;


}
