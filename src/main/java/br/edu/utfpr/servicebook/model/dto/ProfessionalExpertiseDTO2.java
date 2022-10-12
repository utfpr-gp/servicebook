package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertisePK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalExpertiseDTO2 {

    private ProfessionalExpertisePK id;
    private String name;
}
