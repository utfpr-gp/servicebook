package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * Classe para armazenar as informações necessárias para a classe auxiliar n x n
 */
@Data
@Table(name = "professional_expertises")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class ProfessionalExpertise {

	private static final long serialVersionUID = 1L;

	@NonNull
	@EmbeddedId
	private ProfessionalExpertisePK id;

	private Integer rating;

	@ManyToOne
	@MapsId("expertiseId")
	@JoinColumn(name = "expertise_id")
	private Expertise expertise;

	@ManyToOne
	@MapsId("professionalId")
	@JoinColumn(name = "professional_id")
	private Professional professional;
}
