package br.edu.utfpr.servicebook.model.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Classe auxiliar para chave composta em um relacionamento n x n.
 * Guarda temporariamente os ids de serviços que o profissional omitiu, não quer mais ver.
 * Será disparado um Job para apagar registros antigos, após x dias.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"jobRequest","user"})
@Table(name = "job_available_to_hide")
@Entity
public class JobAvailableToHide {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private JobRequestUserPK id;

	private Date date;

	@ManyToOne
	@MapsId("jobRequestId")
	@JoinColumn(name = "job_id")
	private JobRequest jobRequest;

	@ManyToOne
	@MapsId("professionalId")
	@JoinColumn(name = "user_id")
	private User user;

	public JobAvailableToHide(JobRequest jobRequest, User user) {
		this.jobRequest = jobRequest;
		this.user = user;
		this.id = new JobRequestUserPK(jobRequest.getId(), user.getId());
	}

	@PrePersist
	public void onPersist() {
		this.date = new Date();
	}
}
