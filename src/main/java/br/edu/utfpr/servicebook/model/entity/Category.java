package br.edu.utfpr.servicebook.model.entity;

import java.sql.Date;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Table(name = "categories")
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Category {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NonNull
	@Column(unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "categories")
	private Set<Professional> professionals = new HashSet<>();
	
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private Set<JobRequest> jobRequests = new HashSet<>();
	
}
