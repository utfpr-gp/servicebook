package br.edu.utfpr.servicebook.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Table(name = "expertises")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Expertise {

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
}
