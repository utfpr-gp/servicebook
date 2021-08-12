package br.edu.utfpr.servicebook.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Table(name = "professionals")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Professional extends User {

	private static final long serialVersionUID = 1L;

	public Professional(String name, String email, String type, String phoneNumber, String cpf){
		super(name,email, type, phoneNumber);
		this.cpf =  cpf;
	}
	
	@NonNull
	@Column(unique = true)
	private String cpf;
	
//	@OneToMany(mappedBy = "professional")
//	private Set<JobContracted> jobContracted = new HashSet<>();
//	@OneToMany(mappedBy = "professional")
//	Set<ProfessionalExpertise> expertises;

	@OneToMany(mappedBy = "professional")
	Set<JobCandidate> candidatures;

	private String description;

	private int rating;

	private int denounceAmount;

}
