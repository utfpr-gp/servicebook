package br.edu.utfpr.servicebook.model.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

/**
 * Classe para armazenar as informações necessárias para a classe auxiliar n x n
 */

@Data
@NoArgsConstructor
@Table(name = "follows")
@Entity
public class Follows {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FollowsPK id;

	private Date date;

	@ManyToOne
	@MapsId("clientId")
	@JoinColumn(name = "client_id")
	private User client;

	@ManyToOne
	@MapsId("professionalId")
	@JoinColumn(name = "professional_id")
	private User professional;

	public Follows(User client, User professional) {
		this.client = client;
		this.professional = professional;
		this.id = new FollowsPK(client.getId(), professional.getId());
	}

	@PrePersist
	public void onPersist() {
		this.date = new Date();
	}
}