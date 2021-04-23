package br.edu.utfpr.servicebook.model.dto;

import lombok.*;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private Long id;
    @NotEmpty(message = "O nome da cidade é obrigatório")
    private String name;

    private Long idState;

}
