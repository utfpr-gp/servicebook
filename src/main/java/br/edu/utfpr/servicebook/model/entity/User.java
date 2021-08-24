package br.edu.utfpr.servicebook.model.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String name;

	@NonNull
	private String email;

	@NonNull
	private String type;
	
	private String gender;
	
	private String profilePicture;
	
	private Date birthDate;

	@NonNull
	private String phoneNumber;

	private boolean phoneVerified;

	private boolean emailVerified;

	private boolean profileVerified;

	@OneToOne(mappedBy = "user")
	private UserToken userToken;

	@OneToOne
	private Address address;
	
}

}