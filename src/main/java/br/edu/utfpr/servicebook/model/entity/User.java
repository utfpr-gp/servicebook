package br.edu.utfpr.servicebook.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import br.edu.utfpr.servicebook.security.ProfileEnum;
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
	protected Long id;

	@NonNull
	protected String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	protected ProfileEnum profile;

	@NonNull
	@Column(unique = true)
	protected String email;

	protected String password;

	@NonNull
	protected String phoneNumber;

	protected String profilePicture;

	protected boolean phoneVerified;

	protected boolean emailVerified;

	protected boolean profileVerified;

	protected Integer rating;

	@OneToOne(mappedBy = "user")
	protected UserToken userToken;

	@OneToOne(cascade = CascadeType.PERSIST)
	protected Address address;

	@OneToMany(mappedBy = "individual")
	protected Set<JobRequest> jobRequest = new HashSet<>();

	@OneToMany(mappedBy = "individual")
	Set<JobCandidate> candidatures;

	protected String description;

	protected Integer denounceAmount;

	protected Long followsAmount;

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
