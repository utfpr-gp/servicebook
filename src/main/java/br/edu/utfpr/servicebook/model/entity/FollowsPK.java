package br.edu.utfpr.servicebook.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Classe auxiliar para chave composta em um relacionamento n x n
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FollowsPK implements Serializable {

	Long clientId;
	Long professionalId;

	Long companyId;
}