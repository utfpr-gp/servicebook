package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobContractedFullDTO {

    private Long id;
    private String comments;
    private int rating;
    private JobRequestFullDTO jobRequest;

}
