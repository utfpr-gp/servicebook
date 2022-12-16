package br.edu.utfpr.servicebook.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class JobRequestUserPK implements Serializable {

	Long jobRequestId;
	Long professionalId;
}