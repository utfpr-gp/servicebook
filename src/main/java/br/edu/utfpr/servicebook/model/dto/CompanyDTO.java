package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompanyDTO extends UserDTO implements Serializable {
    private Long id;
    @CNPJ(message = "CNPJ inválido! Por favor, insira um CNPJ válido.", groups = CompanyDTO.RequestUserNameAndCNPJInfoGroupValidation.class)
    private String cnpj;

}
