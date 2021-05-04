package br.edu.utfpr.servicebook.model.entity;

import java.sql.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Classe para armazenar as informações necessárias para a classe auxiliar n x n
 */
@Data
@Table(name = "job_candidates")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class JobCandidate {

	private static final long serialVersionUID = 1L; 
	
	@EmbeddedId
	private JobCandidatePK id;
	
	@NonNull
	private boolean isQuit;
	
	@NonNull
	private boolean chosen;
	
	@NonNull
	private boolean chosen_by_budget;
	
	@NonNull
	private Date date; 
	
}
