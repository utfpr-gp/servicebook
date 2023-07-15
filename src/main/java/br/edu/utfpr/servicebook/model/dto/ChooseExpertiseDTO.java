
package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO usado para escolher as especialidades que o usuário possui.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChooseExpertiseDTO implements Serializable {

    @NotNull(message = "O id da especialidade é obrigatório")
    private Long id;

    @NotBlank(message = "A descrição é obrigatória")
    private String description;
}
