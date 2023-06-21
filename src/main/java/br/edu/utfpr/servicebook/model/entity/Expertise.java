package br.edu.utfpr.servicebook.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Table(name = "expertises")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Expertise implements Serializable {

	private static final long serialVersionUID = 1L;

	public Expertise(String name, String description, String pathIcon){
		this.name = name;
		this.description = description;
		this.pathIcon = pathIcon;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	@Column(unique = true)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String pathIcon;

//  1 categoria x n especialidades
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "expertise", cascade = CascadeType.REMOVE)
	private Set<Service> services
			= new HashSet<>();

}
