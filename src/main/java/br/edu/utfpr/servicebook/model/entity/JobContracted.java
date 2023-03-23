package br.edu.utfpr.servicebook.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;

@Data
@Table(name = "job_contracted")
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "jobRequest")
@EqualsAndHashCode(exclude = "jobRequest")
@Entity
public class JobContracted {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String comments;

	private int rating;

	@NonNull
	@OneToOne
	@JoinColumn(name = "job_request")
	private JobRequest jobRequest;

	@NonNull
	@OneToOne
	@JoinColumn(name = "professional")
	private Individual individual;

	@NonNull
	@OneToOne
	@JoinColumn(name = "company")
	private Company company;
}