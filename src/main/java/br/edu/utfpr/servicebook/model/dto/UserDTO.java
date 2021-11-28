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
public class UserDTO implements Serializable {

    private Long id;

    @Pattern(regexp = "^(\\s?[A-ZÀ-Ú][a-zà-ú]*)+(\\s[a-zà-ú]*)?(\\s[A-ZÀ-Ú][a-zà-ú]*)+$", message = "Nome inválido! Por favor, insira o nome completo.", groups = UserDTO.RequestUserNameAndCPFInfoGroupValidation.class)
    private String name;

    @CPF(message = "CPF inválido! Por favor, insira um CPF válido.", groups = UserDTO.RequestUserNameAndCPFInfoGroupValidation.class)
    private String cpf;

    @NotBlank(message = "Email inválido! Por favor, insira o email.", groups = UserDTO.RequestUserEmailInfoGroupValidation.class)
    @Email(message = "Email inválido! Por favor, insira um email válido.", groups = UserDTO.RequestUserEmailInfoGroupValidation.class)
    private String email;

    @NotBlank(message = "Senha inválida! Por favor, insira uma senha.", groups = UserDTO.RequestUserPasswordInfoGroupValidation.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "A senha precisa conter no mínimo 8 caracteres e pelo menos uma letra e um número", groups = UserDTO.RequestUserPasswordInfoGroupValidation.class)
    private String password;

    private String repassword;

    private String type;
    private String gender;
    private String profilePicture;
    private Date birthDate;

    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?(\\d{4,5})-?(\\d{4})$", message = "Telefone inválido! Por favor, insira um número de telefone válido.", groups = UserDTO.RequestUserPhoneInfoGroupValidation.class)
    private String phoneNumber;

    private boolean phoneVerified;
    private boolean emailVerified;
    private boolean profileVerified;

    @Valid
    private AddressFullDTO address;

    public interface RequestUserNameAndCPFInfoGroupValidation {

    }

    public interface RequestUserEmailInfoGroupValidation {

    }

    public interface RequestUserPasswordInfoGroupValidation {

    }

    public interface RequestUserPhoneInfoGroupValidation {

    }

}
