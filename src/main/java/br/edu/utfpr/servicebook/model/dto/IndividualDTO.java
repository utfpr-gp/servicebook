package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.util.IWizardDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IndividualDTO extends UserDTO implements IWizardDTO, Serializable {

    private Long id;
    @CPF(message = "CPF inválido! Por favor, insira um CPF válido.", groups = UserDTO.RequestUserNameAndCPFInfoGroupValidation.class)
    private String cpf;
}
