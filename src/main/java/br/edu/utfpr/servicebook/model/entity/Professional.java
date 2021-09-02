package br.edu.utfpr.servicebook.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"professional"})
@Table(name = "professionals")
@NoArgsConstructor
//@RequiredArgsConstructor
//@EqualsAndHashCode(exclude={"candidatures"})
@ToString(exclude = {"candidatures"})
@Entity
public class Professional extends User {

    private static final long serialVersionUID = 1L;

	public Professional(String name, String email, String phoneNumber, String cpf){
		super(name,email, phoneNumber, cpf);
	}

//	@NonNull
//	@Column(unique = true)
//	private String cpf;

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
