package br.edu.utfpr.servicebook.model.dto;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IndividualDTO extends UserDTO implements Serializable {

    @CPF(message = "CPF inválido! Por favor, insira um CPF válido.", groups = IndividualDTO.RequestUserNameAndCPFInfoGroupValidation.class)
    private String cpf;

     private String gender;

    private Date birthDate;

    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?(\\d{5})-?(\\d{4})$", message = "Telefone inválido! Por favor, insira um número de telefone válido.", groups = IndividualDTO.RequestUserPhoneInfoGroupValidation.class)
    private String phoneNumber;

    private String description;

    private int rating;

    private int denounceAmount;

    public String getOnlyNumbersFromPhone() {
        return getPhoneNumber().replaceAll("[^0-9]", "");
    }
}
