
package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExpertiseDTO implements Serializable {

    private Long id;

    @NotBlank(message = "O nome da especialidade é obrigatório")
    private String name;

    @NotBlank(message = "Descrição é obrigatório")
    private String description;

    private MultipartFile icon;

    private String pathIcon;
}
