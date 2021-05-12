package br.edu.utfpr.servicebook.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
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
    private LocalDate request_expiration;

    private Integer quantity_candidators_max;

    @NotBlank(message = "A Descrição não pode ser vazia.", groups = RequestDescriptionGroupValidation.class)
    private String description;

    private String image;

    @NotBlank(message = "O CEP é inválido", groups = RequestClientInfoGroupValidation.class)
    private String cep;

    @NotBlank(message = "Preencha o nome", groups = RequestClientInfoGroupValidation.class)
    private String name;

    @Email(message = "O Email é inválido", groups = RequestClientInfoGroupValidation.class)
    private String email;

    @NotBlank(message = "Preencha o celular", groups = RequestClientInfoGroupValidation.class)
    private String phone;

    private Boolean client_confirmation;

    public interface RequestCategoryIdGroupValidation {

    }

    public interface RequestDateProximidadeGroupValidation{

    }
    public interface RequestMaxCandidatesGroupValidation{

    }

    public interface RequestClientInfoGroupValidation {

    }

    public interface RequestDescriptionGroupValidation {

    }

}
