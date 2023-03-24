package br.edu.utfpr.servicebook.model.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.*;

/**
 * Classe auxiliar para chave composta em um relacionamento n x n.
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class JobCandidatePK implements Serializable {

	Long jobRequestId;

	Long professionalId;
}