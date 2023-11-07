package br.edu.utfpr.servicebook.model.entity;

import br.edu.utfpr.servicebook.security.ProfileEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"jobRequest", "candidatures"})
@ToString(exclude = {"candidatures", "jobRequest"})

@Table(name = "individuals")
@Entity
public class Individual extends User {

    private static final long serialVersionUID = 1L;

    public enum Gender {
        MASCULINE, FEMININE
    };

    @NonNull
    @Column(unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    public Individual(String name, String email, String password, String phoneNumber, String cpf){
        super(name, email, password, phoneNumber);
        setCpf(cpf);
    }

    /**
     * Cadastro parcial usando quando o usuário está cadastrando um pedido de serviço sem estar logado.
     * Ele passa apenas uns dados principais. Depois ele continua o cadastro.
     * @param name
     * @param email
     * @param phoneNumber
     * @param cpf
     */
    public Individual(String name, String email, String phoneNumber, String cpf){
        super(name, email, phoneNumber);
        setCpf(cpf);
    }

}
