package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSmsDTO implements Serializable {

    private String phoneNumber;

    @Pattern(regexp = "^([0-9]{6})$", message = "Código inválido! Por favor, insira o código de autenticação.", groups = UserSmsDTO.RequestUserSmsInfoGroupValidation.class)
    private String code;

    public interface RequestUserSmsInfoGroupValidation {

    }

}
