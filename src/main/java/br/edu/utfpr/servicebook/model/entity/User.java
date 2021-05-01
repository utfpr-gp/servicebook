package br.edu.utfpr.servicebook.model.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Table(name = "users")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NonNull
	@Column(unique = true)
	private String name;
	
	@NonNull
	private String email;
	
	@NonNull
	private String type;
	
	@NonNull
	private String gender;
	
	@NonNull
	private String profile_picture;
	
	@NonNull
	private Date birth_date;
	
	@NonNull
	private String phone_number;
	
	private boolean phone_verified;
	
	private boolean email_verified;
	
	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private UserToken userToken;
	
	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private Professional professional;
	
	@OneToOne(mappedBy = "user")
	private Client client;
	
}
