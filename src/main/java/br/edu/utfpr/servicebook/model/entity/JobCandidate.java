package br.edu.utfpr.servicebook.model.entity;



import javax.persistence.*;

import br.edu.utfpr.servicebook.util.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

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

	@NonNull
	@EmbeddedId
	private JobCandidatePK id;

	private boolean isQuit;

	private boolean chosenByBudget;

	private Date date;

	@NonNull
	@ManyToOne
	@MapsId("jobRequestId")
	@JoinColumn(name = "job_id")
	private JobRequest jobRequest;

	@NonNull
	@ManyToOne
	@MapsId("professionalId")
	@JoinColumn(name = "professional_id")
	private Professional professional;

	@PrePersist
	public void onPersist(){
		this.date = new Date();
	}


}
