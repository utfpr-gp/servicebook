package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.security.ProfileEnum;
import br.edu.utfpr.servicebook.util.IWizardDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO implements IWizardDTO, Serializable {

    protected Long id;

    @Pattern(regexp = "^(\\s?[A-ZÀ-Ú][a-zà-ú]*)+(\\s[a-zà-ú]*)?(\\s[A-ZÀ-Ú][a-zà-ú]*)+$", message = "Nome inválido! Por favor, insira o nome completo.", groups = {UserDTO.RequestUserNameAndCPFInfoGroupValidation.class, UserDTO.RequestUserNameAndCNPJInfoGroupValidation.class, UserDTO.RequestUpdatePersonalInfo.class})
    protected String name;

    @CPF(message = "CPF inválido! Por favor, insira um CPF válido.", groups = {UserDTO.RequestUserNameAndCPFInfoGroupValidation.class, UserDTO.RequestUpdatePersonalInfo.class})
    protected String cpf;

    @CNPJ(message = "CNPJ inválido! Por favor, insira um CNPJ válido.", groups = {UserDTO.RequestUserNameAndCNPJInfoGroupValidation.class, UserDTO.RequestUpdatePersonalInfo.class})
    protected String cnpj;

    //@Pattern(regexp = "^\\d{4}-(1[012]|0[1-9])-(3[01]|[12]\\d|0[0-9])$", message = "A data deve estar no formato yyyy-MM-dd", groups = UserDTO.RequestUpdatePersonalInfo.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Data de nascimento inválida! Por favor, insira uma data de nascimento válida.", groups = UserDTO.RequestUpdatePersonalInfo.class)
    protected LocalDate birthDate;


    @NotBlank(message = "Email inválido! Por favor, insira o email.", groups = UserDTO.RequestUserEmailInfoGroupValidation.class)
    @Email(message = "Email inválido! Por favor, insira um email válido.", groups = UserDTO.RequestUserEmailInfoGroupValidation.class)
    protected String email;

    @NotBlank(message = "Senha inválida! Por favor, insira uma senha.", groups = UserDTO.RequestUserPasswordInfoGroupValidation.class)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "A senha precisa conter no mínimo 8 caracteres e pelo menos uma letra e um número", groups = UserDTO.RequestUserPasswordInfoGroupValidation.class)
    protected String password;

    protected String repassword;

    protected String gender;

    protected String profilePicture;
    protected ProfileEnum profile;

    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?(\\d{4,5})-?(\\d{4})$", message = "Telefone inválido! Por favor, insira um número de telefone válido.", groups = UserDTO.RequestUserPhoneInfoGroupValidation.class)
    protected String phoneNumber;

    /**
     * Retorna apenas os números do telefone
     * @return
     */
    public String getOnlyNumbersFromPhone() {
      return getPhoneNumber().replaceAll("[^0-9]", "");
  }

    protected boolean phoneVerified;
    protected boolean emailVerified;
    protected boolean profileVerified;

    private String description;

    private int rating;

    private int denounceAmount;

    private Long followingAmount;

    @Valid
    protected AddressFullDTO address;

    public interface RequestUserNameAndCPFInfoGroupValidation {

    }

    public interface RequestUserNameAndCNPJInfoGroupValidation {
    }

    public interface RequestUserEmailInfoGroupValidation {

    }

    public interface RequestUserPasswordInfoGroupValidation {

    }

    public interface RequestUserPhoneInfoGroupValidation {

    }

    public interface RequestUpdatePersonalInfo {

    }
}
