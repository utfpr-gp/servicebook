package br.edu.utfpr.servicebook.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.Data;

@Table(name = "companies")
@Entity
@Data
@PrimaryKeyJoinColumn(name="company_id")
public class Company extends User {

	private static final long serialVersionUID = 1L;

	private String cnpj;
}
