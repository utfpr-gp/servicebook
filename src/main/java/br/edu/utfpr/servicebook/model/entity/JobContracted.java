package br.edu.utfpr.servicebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Table(name = "job_contracted")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class JobContracted {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	private String status;
	
	@NonNull
	private String comments;
	
	@NonNull
	private int rating;
	
	@NonNull
	@OneToOne
	@JoinColumn(name = "jobRequest", referencedColumnName = "id")
	private JobRequest jobRequest;
	
	@NonNull
	@OneToOne
	@JoinColumn(name = "professional", referencedColumnName = "id")
	private Professional professional;
	
}
