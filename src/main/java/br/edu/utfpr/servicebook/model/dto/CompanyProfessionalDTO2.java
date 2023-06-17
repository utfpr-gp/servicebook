package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.model.entity.CompanyProfessionalPK;
import br.edu.utfpr.servicebook.util.IWizardDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyProfessionalDTO2 implements IWizardDTO, Serializable {

    private CompanyProfessionalPK id;
    private String name;
    private String email;
    private String profilePicture;
    private String isConfirmed;
}
