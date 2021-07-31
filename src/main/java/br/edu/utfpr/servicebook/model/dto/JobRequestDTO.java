package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDTO implements Serializable {

    private Long id;
    private Long expertiseId;
    private Long clientId;
    private Integer dateProximity;
    private LocalDate dateCreated;
    public String status;
    @Future(message = "Valor inválido para a requisição", groups = RequestExpirationGroupValidation.class)
    private LocalDate dateExpired;

    @DecimalMin(value = "1", message = "", groups = RequestMaxCandidatesGroupValidation.class)
    @DecimalMax(value = "20.00", message = "", groups = RequestMaxCandidatesGroupValidation.class)
    @Positive
    private Integer quantityCandidatorsMax;

    @NotBlank(message = "A Descrição não pode ser vazia.", groups = RequestDescriptionGroupValidation.class)
    private String description;

    private MultipartFile imageFile;
    private String imageSession;

    @NotBlank(message = "O CEP não pode ser vazio", groups = RequestClientInfoGroupValidation.class)
    @Pattern(regexp="^[0-9]{2}[0-9]{3}-[0-9]{3}$",message="CEP Inválido", groups = RequestClientInfoGroupValidation.class)
    private String cep;

    @NotBlank(message = "Preencha o nome", groups = RequestClientInfoGroupValidation.class)
    @Pattern(regexp="^(?![ ])(?!.*[ ]{2})((?:e|de|da|do|das|dos)\\s*?|(?:[A-Z][^\\s]*\\s*?)(?!.*[ ]$))+$",message="Nome Inválido", groups = RequestClientInfoGroupValidation.class)
    private String nameClient;

    @Email(message = "O Email é inválido", groups = RequestClientInfoGroupValidation.class)
    @Pattern(regexp="^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message="E-mail Inválido", groups = RequestClientInfoGroupValidation.class)
    private String emailClient;

    @NotBlank(message = "Preencha o celular", groups = RequestClientInfoGroupValidation.class)
    @Pattern(regexp="\\(\\d{2}\\)\\s9?\\d{4}-\\d{4}",message="Celular Inválido", groups = RequestClientInfoGroupValidation.class)
    private String phone;
    private Boolean clientConfirmation;



    public interface RequestMaxCandidatesGroupValidation{

    }

    public interface RequestClientInfoGroupValidation {

    }

    public interface RequestDescriptionGroupValidation {

    }
    public interface RequestExpirationGroupValidation {

    }

}
