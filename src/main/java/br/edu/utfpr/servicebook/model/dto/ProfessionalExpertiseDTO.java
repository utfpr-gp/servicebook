package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.util.IWizardDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalExpertiseDTO implements IWizardDTO, Serializable {
    private Set<Integer> ids = new HashSet<>();

}
