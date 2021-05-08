package br.edu.utfpr.servicebook.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Table(name = "professionals")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name="usuario_id")
public class Professional extends User {

	private static final long serialVersionUID = 1L;
	
	@ManyToMany
	@JoinTable(name = "professional_expertise",
			  joinColumns = @JoinColumn(name = "professional_id"),
			  inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Expertise> expertises = new HashSet<>();
	
	@NonNull
	@Column(unique = true)
	private String cpf;
	
	@OneToMany(mappedBy = "professional")
	private Set<Avaliation> avaliations = new HashSet<>();
	
	@OneToOne(mappedBy = "professional")
	private JobContracted jobContracted;
	
}
