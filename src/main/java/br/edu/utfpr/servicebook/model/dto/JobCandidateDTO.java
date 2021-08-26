package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.entity.Professional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobCandidateDTO implements Serializable {
    private Long id;

    private JobRequestMinDTO  jobRequest;

    private ProfessionalMinDTO professional;


}
