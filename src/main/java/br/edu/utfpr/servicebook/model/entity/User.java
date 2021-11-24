package br.edu.utfpr.servicebook.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Gender {
		MASCULINE, FEMININE
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String name;

	@NonNull
	@Column(unique = true)
	private String cpf;

	@NonNull
	@Column(unique = true)
	private String email;

	@NonNull
	private String password;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String profilePicture;

	private Date birthDate;

	@NonNull
	@Column(unique = true)
	private String phoneNumber;

	private boolean phoneVerified;

	private boolean emailVerified;

	private boolean profileVerified;

	@OneToOne(mappedBy = "user")
	private UserToken userToken;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Address address;

}
