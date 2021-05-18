package br.edu.utfpr.servicebook.model.entity;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Classe auxiliar para chave composta em um relacionamento n x n
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Embeddable
public class ProfessionalExpertisePK implements Serializable {

	@ManyToOne
	@JoinColumn(name = "expertise_id")
	@NonNull
	private Expertise expertise;
	
	@ManyToOne
	@JoinColumn(name = "professional_id")
	@NonNull
	private Professional professional;
	
	
}
