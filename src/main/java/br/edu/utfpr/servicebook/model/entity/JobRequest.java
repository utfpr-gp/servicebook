package br.edu.utfpr.servicebook.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import br.edu.utfpr.servicebook.util.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Table(name = "job_requests")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class JobRequest {

	public enum Status {
		AVAILABLE, DISPUTE, TO_DO, CLOSED
	};



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "expertise_id")
	private Expertise expertise;
	
	@NonNull
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@NonNull
	private String description;
	
	@NonNull
	private int quantityCandidatorsMax;

	private LocalDate dateCreated;
	
	@NonNull
	private LocalDate dateExpired;

	private boolean clientConfirmation;
	
	private boolean professionalConfirmation;
	
	@OneToMany(mappedBy = "jobRequest")
	private Set<JobImages> jobImages = new HashSet<>();
	
	@OneToOne(mappedBy = "jobRequest")
	private JobContracted jobContracted;

	@PrePersist
	public void onPersist(){
		this.dateCreated = DateUtil.getToday();
		this.status = Status.AVAILABLE;
	}

	@PreUpdate
	public void onUpdate(){

	}
	
}