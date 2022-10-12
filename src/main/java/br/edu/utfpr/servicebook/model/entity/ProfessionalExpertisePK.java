package br.edu.utfpr.servicebook.model.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Classe auxiliar para chave composta em um relacionamento n x n
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProfessionalExpertisePK implements Serializable {

    Long expertiseId;
    Long professionalId;
}