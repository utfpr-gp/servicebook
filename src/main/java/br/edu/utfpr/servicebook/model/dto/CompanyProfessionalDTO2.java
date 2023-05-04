package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.model.entity.CompanyProfessionalPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyProfessionalDTO2 {

    private CompanyProfessionalPK id;
    private String name;
    private String email;
    private String profilePicture;
}
