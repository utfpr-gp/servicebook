package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalSearchItemDTO extends UserDTO {

    private Long id;

    private String cpf;

    private String description;

    private int rating;

    private int denounceAmount;

    public String getOnlyNumbersFromPhone() {
        return getPhoneNumber().replaceAll("[^0-9]", "");
    }

    public List<ExpertiseDTO> expertises;
}