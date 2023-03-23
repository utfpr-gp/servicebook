package br.edu.utfpr.servicebook.model.entity;



import javax.persistence.*;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * Classe para armazenar as informações necessárias para a classe auxiliar n x n
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"jobRequest","professional"})

@Table(name = "job_candidates")
@Entity
public class JobCandidate {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private JobCandidatePK id;

	private boolean isQuit;

	private boolean chosenByBudget;
	private Boolean isHired;
	private Date date;

	private Date hiredDate;

	@ManyToOne
	@MapsId("jobRequestId")
	@JoinColumn(name = "job_id")
	private JobRequest jobRequest;

	@ManyToOne
	@MapsId("professionalId")
	@JoinColumn(name = "professional_id")
	private Individual individual;


	public JobCandidate(JobRequest jobRequest, Individual individual) {
		this.jobRequest = jobRequest;
		this.individual = individual;
		this.id = new JobCandidatePK(jobRequest.getId(), individual.getId());
	}

	@PrePersist
	public void onPersist() {
		this.date = new Date();
	}
}
