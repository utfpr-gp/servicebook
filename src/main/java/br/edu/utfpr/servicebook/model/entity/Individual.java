package br.edu.utfpr.servicebook.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"professional"})
@ToString(exclude = {"candidatures"})

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

    private Date birthDate;

    @OneToMany(mappedBy = "client")
    private Set<JobRequest> jobRequest = new HashSet<>();

    @OneToMany(mappedBy = "professional")
    Set<JobCandidate> candidatures;

    private String description;

    private Integer denounceAmount;

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
