package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCodeDTO implements Serializable {

    private String email;

    @Pattern(regexp = "^([0-9]{6})$", message = "Código inválido! Por favor, insira o código de autenticação.", groups = UserCodeDTO.RequestUserCodeInfoGroupValidation.class)
    private String code;

    public interface RequestUserCodeInfoGroupValidation {

    }


}
