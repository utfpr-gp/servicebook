package br.edu.utfpr.servicebook.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Table(name = "clients")
@Entity
@Data
@PrimaryKeyJoinColumn(name="usuario_id")
public class Client extends User {

	private static final long serialVersionUID = 1L;

	public Client(){}

	public Client(String name, String email, String phone, String cep){
		super.setName(name);
		super.setEmail(email);
		super.setPhoneNumber(phone);
	}
	
	@OneToMany(mappedBy = "client")
	private Set<JobRequest> jobRequest = new HashSet<>();
	
}
