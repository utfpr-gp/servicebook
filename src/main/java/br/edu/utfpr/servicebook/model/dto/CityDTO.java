package br.edu.utfpr.servicebook.model.dto;

import lombok.*;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private Long id;

    @NotNull(message = "O nome da cidade é obrigatório")
    @NotEmpty(message = "O nome da cidade é obrigatório")
    private String name;

    @NotNull(message = "Selecionar um estado é obrigatório")
    private Long idState;

}
