package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class JobRequestDTO implements Serializable {

    private Long category_id;

    private Integer date_proximidade; //Usar date para definir até quando fica em aberto

    private LocalDate created_date;

    @Future(message = "Valor inválido para a requisição", groups = RequestExpirationGroupValidation.class)
    private LocalDate request_expiration;

    @DecimalMin(value = "1", message = "", groups = RequestMaxCandidatesGroupValidation.class)
    @DecimalMax(value = "20.00", message = "", groups = RequestMaxCandidatesGroupValidation.class)
    @Positive
    private Integer quantity_candidators_max;

    @NotBlank(message = "A Descrição não pode ser vazia.", groups = RequestDescriptionGroupValidation.class)
    private String description;

    private String image;

    @NotBlank(message = "O CEP não pode ser vazio", groups = RequestClientInfoGroupValidation.class)
    @Pattern(regexp="\\d{5}-?\\d{3}",message="CEP Inválido")
    private String cep;

    @NotBlank(message = "Preencha o nome", groups = RequestClientInfoGroupValidation.class)
    private String name;

    @Email(message = "O Email é inválido", groups = RequestClientInfoGroupValidation.class)
    private String email;

    @NotBlank(message = "Preencha o celular", groups = RequestClientInfoGroupValidation.class)
    @Pattern(regexp="/^(0[1-2][1-9]9\\d{8})|(0[3-9][1-9]\\d{8})$/",message="Telefone Inválido", groups = RequestClientInfoGroupValidation.class)
    private String phone;

    private Boolean client_confirmation;

    public interface RequestMaxCandidatesGroupValidation{

    }

    public interface RequestClientInfoGroupValidation {

    }

    public interface RequestDescriptionGroupValidation {

    }
    public interface RequestExpirationGroupValidation {

    }

}
