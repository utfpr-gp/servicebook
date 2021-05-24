package br.edu.utfpr.servicebook.model.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Table(name = "clients")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name="usuario_id")
public class Client extends User {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "client")
	private Set<JobRequest> jobRequest = new HashSet<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@NonNull
	@Column
	private String nameClient;

	@NonNull
	@Column
	private String emailClient;

	@NonNull
	@Column
	private String phone;

	@NonNull
	@Column
	private String cep;

}
