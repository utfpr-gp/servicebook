package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.util.IWizardDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IndividualDTO extends UserDTO implements IWizardDTO, Serializable {

    private Long id;
    @CPF(message = "CPF inválido! Por favor, insira um CPF válido.", groups = UserDTO.RequestUserNameAndCPFInfoGroupValidation.class)
    private String cpf;
}
