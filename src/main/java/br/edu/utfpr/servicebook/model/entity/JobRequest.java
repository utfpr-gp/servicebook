package br.edu.utfpr.servicebook.model.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import br.edu.utfpr.servicebook.util.DateUtil;
import lombok.*;

@Data
@Table(name = "job_requests")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude={"client", "jobCandidates", "jobContracted", "expertise", "jobImages"})
@ToString(exclude={"client", "jobCandidates", "jobContracted", "expertise", "jobImages"})
@Entity
public class JobRequest {

	/**
	 * AVAILABLE: disponível para candidaturas e permanece neste estado também durante o recebimento de candidaturas
	 * BUDGET: passa para este estado quando alcançado o total de candidaturas esperado ou quando o cliente encerra o recebimento de candidaturas
	 * TO_DO: o profissional foi escolhido para fazer o serviço e o serviço ainda não foi realizado
	 * CLOSED: o serviço foi realizado
	 */
	public enum Status {
		AVAILABLE, BUDGET, TO_DO, CLOSED
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

	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@Temporal(TemporalType.DATE)
	@NonNull
	private Date dateExpired;

	private boolean clientConfirmation;
	
	private boolean professionalConfirmation;
	
	@OneToMany(mappedBy = "jobRequest")
	private Set<JobImages> jobImages = new HashSet<>();
	
	@OneToOne(mappedBy = "jobRequest", cascade = CascadeType.PERSIST)
	private JobContracted jobContracted;

	@OneToMany(mappedBy = "jobRequest")
	Set<JobCandidate> jobCandidates = new HashSet<>();

	@PrePersist
	public void onPersist(){
		final Date now = new Date();
		this.dateCreated = now;
		this.status = Status.AVAILABLE;
	}

	@PreUpdate
	public void onUpdate(){

	}
	
}