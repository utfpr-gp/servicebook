package br.edu.utfpr.servicebook.model.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Classe auxiliar para chave composta em um relacionamento n x n
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Embeddable
public class JobCandidatePK implements Serializable {

	@ManyToOne
	@JoinColumn(name = "job_id")
	@NonNull
	private JobRequest jobRequest;
	
	@ManyToOne
	@JoinColumn(name = "professional_id")
	@NonNull
	private Professional professional;

	@Override
	public String toString() {
		return "JobCandidatePK{" +
				"jobRequest=" + jobRequest +
				", professional=" + professional +
				'}';
	}
}
