package br.edu.utfpr.servicebook.model.dto;

import lombok.*;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CityDTO {

    @NotEmpty(message = "O nome da cidade é obrigatório")
    private String name;

}
