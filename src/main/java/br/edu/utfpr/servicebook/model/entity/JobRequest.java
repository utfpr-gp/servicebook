package br.edu.utfpr.servicebook.model.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	private String status;
	
	@NonNull
	private String description;
	
	@NonNull
	private int quantityCandidatorsMax;
	
	@NonNull
	private Date dateProximity;
	
	@NonNull
	private Date dateCreated;
	
	@NonNull
	private Date dateExpired;
	
	@NonNull
	private boolean clientConfirmation;
	
	@NonNull
	private boolean professionalConfirmation;
	
	@OneToMany(mappedBy = "jobRequest")
	private Set<JobImages> jobImages = new HashSet<>();
	
	@OneToOne(mappedBy = "jobRequest")
	private JobContracted jobContracted;
	
}