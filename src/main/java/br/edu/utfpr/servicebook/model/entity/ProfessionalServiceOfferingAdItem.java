package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * A classe ProfessionalServiceOfferingAdItem representa a classe auxiliar n x n entre as classes
 * ProfessionalServiceOffering e ProfessionalServiceOfferingAd.
 */
@Data
@NoArgsConstructor

@Table(name = "professional_service_offering_ad_items")
@Entity
public class ProfessionalServiceOfferingAdItem {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProfessionalServiceOfferingAdItemPK id;

	/**
	 * Quantidade de serviços adicionados no anúncio.
	 * Por exemplo: se o serviço for uma sessão de fisioterapia, a quantidade pode ser 1 ou mais.
	 */
	private Integer amount;

	@ManyToOne
	@MapsId("professionalServiceOfferingId")
	@JoinColumn(name = "professional_service_offering_id")
	private ProfessionalServiceOffering professionalServiceOffering;

	@ManyToOne
	@MapsId("professionalServiceOfferingAdId")
	@JoinColumn(name = "professional_service_offering_Ad")
	private ProfessionalServiceOfferingAd professionalServiceOfferingAd;

	public ProfessionalServiceOfferingAdItem(ProfessionalServiceOffering professionalServiceOffering, ProfessionalServiceOfferingAd professionalServiceOfferingAd){
		this.professionalServiceOffering = professionalServiceOffering;
		this.professionalServiceOfferingAd = professionalServiceOfferingAd;
		this.id = new ProfessionalServiceOfferingAdItemPK(professionalServiceOffering.getId(), professionalServiceOfferingAd.getId());
	}
}