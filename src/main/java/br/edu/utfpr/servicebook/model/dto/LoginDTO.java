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
    private String email;
    private UserCodeDTO token;
    @NotBlank(message = "Email inválido! Por favor, insira o email.", groups = LoginDTO.RequestUserEmailInfoGroupValidation.class)
    @Email(message = "Email inválido! Por favor, insira um email válido.", groups = LoginDTO.RequestUserEmailInfoGroupValidation.class)

    public interface RequestUserEmailInfoGroupValidation {

    }
}
