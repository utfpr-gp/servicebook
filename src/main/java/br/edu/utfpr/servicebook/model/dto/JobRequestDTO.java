package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDTO implements Serializable {

    private Long id;

    @NotNull(message = "Especialidade inválida! Por favor, selecione uma especialidade de um profissional.", groups = RequestExpertiseGroupValidation.class)
    private Long expertiseId;

    private Long clientId;
    private Integer dateProximity;
    private LocalDate dateCreated;

    public String status;
    @Future(message = "Valor inválido para a requisição", groups = RequestExpirationGroupValidation.class)
    private LocalDate dateTarget;

    @DecimalMin(value = "1", message = "", groups = RequestMaxCandidatesGroupValidation.class)
    @DecimalMax(value = "20.00", message = "", groups = RequestMaxCandidatesGroupValidation.class)
    @Positive
    private Integer quantityCandidatorsMax;

    @NotBlank(message = "A Descrição não pode ser vazia.", groups = RequestDescriptionGroupValidation.class)
    @NotNull(message = "A Descrição não pode ser vazia.", groups = RequestDescriptionGroupValidation.class)
    private String description;

    public List<MultipartFile> images;
    private List<String> imagesSession = new ArrayList<>();

//    @NotBlank(message = "O campo CEP é de preenchimento obrigatório", groups = RequestClientInfoGroupValidation.class)
//    @Pattern(regexp="\\d{5}-?\\d{3}",message="Por favor, preencha um CEP válido", groups = RequestClientInfoGroupValidation.class)
//    private String cep;

//    @NotBlank(message = "O campo Nome é de preenchimento obrigatório", groups = RequestClientInfoGroupValidation.class)
//    @Pattern(regexp="^(\\s?[A-ZÀ-Ú][a-zà-ú]*)+(\\s[a-zà-ú]*)?(\\s[A-ZÀ-Ú][a-zà-ú]*)+",message="Por favor, preencha um nome válido", groups = RequestClientInfoGroupValidation.class)
//    private String nameClient;
//
//    @Email(message = "O campo E-mail é de preenchimento obrigatório", groups = RequestClientInfoGroupValidation.class)
//    @Pattern(regexp="\\w+@\\w+\\.\\w{3}(\\.\\w{2})?",message="Por favor, preencha um e-mail válido", groups = RequestClientInfoGroupValidation.class)
//    private String emailClient;
//
//    @NotBlank(message = "O campo Celular é de preenchimento obrigatório", groups = RequestClientInfoGroupValidation.class)
//    @Pattern(regexp="^\\(?\\d{2}\\)?\\s?(9\\d{4})-?(\\d{4})",message="Por favor, preencha um celular válido, deve possuir o 9 após o DDD", groups = RequestClientInfoGroupValidation.class)
//    private String phone;
//
//    @NotBlank(message = "O campo CPF é de preenchimento obrigatório", groups = RequestClientInfoGroupValidation.class)
//    @CPF(message = "O CPF é inválido!", groups = RequestClientInfoGroupValidation.class)
//    private String cpf;

    private Boolean clientConfirmation;

    public interface RequestExpertiseGroupValidation {

    }

    public interface RequestMaxCandidatesGroupValidation{

    }

    public interface RequestClientInfoGroupValidation {

    }

    public interface RequestDescriptionGroupValidation {

    }
    public interface RequestExpirationGroupValidation {

    }
}
