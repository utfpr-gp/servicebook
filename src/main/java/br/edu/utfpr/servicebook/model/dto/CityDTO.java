package br.edu.utfpr.servicebook.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDTO {
    private Long id;

    @NotEmpty(message = "O nome da cidade é obrigatório")
    private String name;

    private MultipartFile image;

    private String pathImage;

    @NotNull(message = "O nome do estado é obrigatório")
    private Long idState;

}
