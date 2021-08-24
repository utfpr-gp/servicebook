package br.edu.utfpr.servicebook.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.*;

@Data
@EqualsAndHashCode(exclude={"professional"})
@Table(name = "professionals")
@NoArgsConstructor
@RequiredArgsConstructor
//@EqualsAndHashCode(exclude={"candidatures"})
@ToString(exclude={"candidatures"})
@Entity
public class Professional extends User {

	private static final long serialVersionUID = 1L;

	public Professional(String name, String email, String phoneNumber, String cpf){
		super(name,email, phoneNumber);
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
