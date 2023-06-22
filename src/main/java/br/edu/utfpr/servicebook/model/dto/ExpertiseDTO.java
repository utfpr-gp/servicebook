
package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExpertiseDTO implements Serializable {

    private Long id;

    @NotBlank(message = "O nome da especialidade é obrigatório")
    private String name;

    @NotBlank(message = "A descrição é obrigatória")
    private String description;

    private MultipartFile icon;

    @NotNull(message = "O nome da categoria é obrigatório")
    private Long categoryId;

    /**
     * Campo usado para exibir o nome da categoria na tela de cadastro de especialidade para listagem
     */
    private String categoryName;

    /**
     * Campo usado para exibir o nome da categoria na tela de cadastro de especialidade para edição
     */
    private String pathIcon;
}
