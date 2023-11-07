package br.edu.utfpr.servicebook.model.entity;

import javax.persistence.*;

import br.edu.utfpr.servicebook.util.PasswordUtil;
import lombok.*;

import java.time.LocalDate;

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

	/**
	 * Comentário realizado pelo cliente que contratou o serviço, depois que o mesmo foi finalizado.
	 */
	private String comments;

	/**
	 * Quantidade de estrelas que o profissional recebeu pela realização do serviço para o cliente.
	 */
	private int rating;

	/**
	 * Data em que o profissional foi contratado pelo cliente.
	 */
	private LocalDate hiredDate;

	/**
	 * Data que o profissional marcou para realizar o serviço.
	 */
	private LocalDate todoDate;

	/**
	 * Data em que o profissional finalizou o serviço.
	 */
	private LocalDate finishDate;

	@NonNull
	@OneToOne
	@JoinColumn(name = "job_request")
	private JobRequest jobRequest;

	/**
	 * Profissional indivíduo ou empresa contratado.
	 */
	@NonNull
	@OneToOne
	@JoinColumn(name = "professional")
	private User user;

	@PrePersist
	public void onSave() {
		this.hiredDate = LocalDate.now();
	}
	@NonNull
	@OneToOne
	@JoinColumn(name = "company")
	private Company company;
}