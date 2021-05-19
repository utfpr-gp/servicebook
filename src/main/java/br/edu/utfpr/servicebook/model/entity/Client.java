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

import lombok.NoArgsConstructor;

@Table(name = "clients")
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name="usuario_id")
public class Client extends User {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "client")
	private Set<JobRequest> jobRequest = new HashSet<>();
	
}
