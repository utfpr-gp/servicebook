
package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

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

    private Optional<Boolean> reviewable = Optional.of(false);
}
