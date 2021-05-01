package br.edu.utfpr.servicebook.model.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Data
@Table(name = "users_token")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class UserToken {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NonNull
	@OneToOne
	@JoinColumn(name = "userToken", referencedColumnName = "id")
	@JsonIgnore
	private User user;
	
	@NonNull
	@Column(unique = true)
	private String token;
	
	@NonNull
	private String type;
	
	@NonNull
	private Date expired_date;
	
}
