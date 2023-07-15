package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Classe que representa a entidade "professional_expertises" da tabela no banco de dados
 * Cada instância representa um relacionamento NxN entre profissional e expertise.
 */
@Data
@NoArgsConstructor

@Table(name = "professional_expertises")
@Entity
public class ProfessionalExpertise {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProfessionalExpertisePK id;

	/**
	 * Avaliação do profissional na expertise
	 */
	private Integer rating;

	/**
	 * Descrição particular do profissional sobre a expertise.
	 * É usada para descrever o nível de conhecimento do profissional na expertise.
	 * Também, será usado para apresentar no portfólio do profissional.
	 * Caso não haja esta descrição, será usada a descrição genérica da expertise cadastrada pelo administrador.
	 */
	private String description;

	/**
	 * Uma especialidade pode ser oferecida por vários profissionais
	 */
	@ManyToOne
	@MapsId("expertiseId")
	@JoinColumn(name = "expertise_id")
	private Expertise expertise;

	/**
	 * Um profissional pode oferecer várias especialidades
	 */
	@ManyToOne
	@MapsId("professionalId")
	@JoinColumn(name = "professional_id")
	private User professional;

	public ProfessionalExpertise(User professional, Expertise expertise){
		this.expertise = expertise;
		this.professional = professional;
		this.id = new ProfessionalExpertisePK(expertise.getId(), professional.getId());
	}
}