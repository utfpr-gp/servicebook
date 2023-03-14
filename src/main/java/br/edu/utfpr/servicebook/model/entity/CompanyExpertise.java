package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Classe para armazenar as informações necessárias para a classe auxiliar n x n
 */
@Data
@NoArgsConstructor

@Table(name = "company_expertises")
@Entity
public class CompanyExpertise {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CompanyExpertisePK id;

    private Integer rating;

    @ManyToOne
    @MapsId("expertiseId")
    @JoinColumn(name = "expertise_id")
    private Expertise expertise;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private Company company;

    public CompanyExpertise(Company company, Expertise expertise){
        this.expertise = expertise;
        this.company = company;
        this.id = new CompanyExpertisePK(expertise.getId(), company.getId());
    }
}
