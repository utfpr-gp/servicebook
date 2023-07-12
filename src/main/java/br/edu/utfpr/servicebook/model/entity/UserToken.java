package br.edu.utfpr.servicebook.model.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Table(name = "users_token")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class UserToken {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NonNull
	@OneToOne
	@JoinColumn(name = "userToken", referencedColumnName = "id")
	private User user;
	
	@NonNull
	@Column(unique = true)
	private String token;
	
	@NonNull
	private String type;
	
	@NonNull
	private LocalDate expiredDate;
}
