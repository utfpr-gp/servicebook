package br.edu.utfpr.servicebook.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import br.edu.utfpr.servicebook.util.PasswordUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "users")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String name;

	@NonNull
	@Column(unique = true)
	private String email;

	private String password;

	@NonNull
	private String phoneNumber;

	private String profilePicture;

	private boolean phoneVerified;

	private boolean emailVerified;

	private boolean profileVerified;

	private Integer rating;

	@OneToOne(mappedBy = "user")
	private UserToken userToken;

	@OneToOne(cascade = CascadeType.PERSIST)
	private Address address;

	@PrePersist
	@PreUpdate
	public void onSave() {
		if(this.password == null) {
			return;
		}

		final String hashed = PasswordUtil.generateBCrypt(getPassword());
		setPassword(hashed);
	}

	public User(String name, String email, String password, String phoneNumber){
		setName(name);
		setEmail(email);
		setPassword(password);
		setPhoneNumber(phoneNumber);
	}

	public User(String name, String email, String phoneNumber){
		setName(name);
		setEmail(email);
		setPhoneNumber(phoneNumber);
	}

}
