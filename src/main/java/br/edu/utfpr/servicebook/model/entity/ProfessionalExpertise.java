package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
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
}
