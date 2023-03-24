package br.edu.utfpr.servicebook.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.*;

@Data
@Table(name = "job_requests")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude={"individual", "jobCandidates", "jobContracted", "expertise", "jobImages"})
@ToString(exclude={"individual", "jobCandidates", "jobContracted", "expertise", "jobImages"})
@Entity
public class JobRequest {
	/**
	 * AVAILABLE: disponível para candidaturas
	 * BUDGET: muda para este estado quando o cliente escolhe pelo menos um candidato para orçamento ou quando alcançado o total de candidaturas esperado ou quando o cliente encerra o recebimento de candidaturas
	 * TO_HIRED: o cliente escolhe um candidato para realizar o serviço, mas aguarda a confirmação
	 * TO_DO: o profissional confirmou e o serviço ainda não foi realizado
	 * DOING: a data para a realização do serviço chegou
	 * CLOSED: o serviço foi realizado
	 * CANCELED: o serviço foi cancelado
	 */
	public enum Status {
		AVAILABLE, BUDGET, TO_HIRED, TO_DO, DOING, CLOSED, CANCELED
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Cliente que criou o anúncio do serviço, podendo ser um indivíduo ou empresa.
	 */
	@ManyToOne
	@JoinColumn(name = "client_id")
	private User user;
	
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

	/**
	 * Data que o cliente deseja que o serviço seja realizado, mas não necessariamente será a data de realização.
	 * Esta data será negociada com o profissional, o qual definirá a data oficial no JobContracted
	 */
	@Temporal(TemporalType.DATE)
	@NonNull
	private Date dateTarget;

	private boolean clientConfirmation;
	
	private boolean professionalConfirmation;

	@OneToMany(mappedBy = "jobRequest", cascade = CascadeType.REMOVE)
	private Set<JobImages> jobImages = new HashSet<>();
	
	@OneToOne(mappedBy = "jobRequest", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private JobContracted jobContracted;

	@OneToMany(mappedBy = "jobRequest", cascade = CascadeType.REMOVE)
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