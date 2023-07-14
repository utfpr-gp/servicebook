package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe que representa um serviço.
 * Um serviço é cadastrado pelo Administrador do sistema.
 * A descrição do serviço é feita em primeira pessoa, como se o prestador estivesse falando diretamente com o cliente sobre
 * o que ele faz. Este texto é usado como texto genérico para montar o portfólio do prestador, caso ele não especifique
 * a descrição particular de um serviço adicionado a sua conta.
 */
@Data
@Table(name = "services")
@NoArgsConstructor
@Entity
public class Service {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do serviço.
     */
    @NonNull
    private String name;

    /**
     * Descrição do serviço em primeira pessoa.
     * Também é usado como texto genérico para montar o portfólio do prestador, caso ele não especifique a descrição deste serviço.
     */
    @NonNull
    private String description;

    /**
     * O administrador informa se será permitido ou não a disponibilização de agendamento para o serviço.
     * Caso seja permitido, o cliente poderá agendar o serviço com o prestador diretamente pela plataforma.
     */
    private boolean allowScheduling;

    /**
     * A especialidade do serviço.
     */
    @ManyToOne
    @JoinColumn(name = "expertise_id")
    private Expertise expertise;
}
