package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDTO extends UserDTO implements Serializable {
    @NotBlank(message = "Email inválido! Por favor, insira o email.", groups = LoginDTO.EmailGroupValidation.class)
    @Email(message = "Email inválido! Por favor, insira um email válido.", groups = LoginDTO.EmailGroupValidation.class)
    private String email;

    @Pattern(regexp = "^([0-9]{6})$", message = "Código inválido! Por favor, insira o código de autenticação.", groups = LoginDTO.CodeGroupValidation.class)
    private String code;

    public interface EmailGroupValidation {
    }

    public interface CodeGroupValidation {
    }
}
