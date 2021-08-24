package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.entity.Professional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobCandidateDTO {
    private Long id;

    private JobRequest jobRequest;

    private Professional professional;


}
