package br.edu.utfpr.servicebook.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Classe que representa a chave primária composta da classe ProfessionalServiceOfferingAdItem.
 * A chave primária é composta pelos atributos professionalServiceOfferingId e professionalServiceOfferingAdId.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProfessionalServiceOfferingAdItemPK implements Serializable {
    Long professionalServiceOfferingId;

    Long professionalServiceOfferingAdId;
}