package br.edu.utfpr.servicebook.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * Classe para armazenar as informações necessárias para a classe auxiliar n x n
 */
@Data
@NoArgsConstructor

@Table(name = "company_professionals")
@Entity
public class CompanyProfessional {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CompanyProfessionalPK id;
    private Integer rating;

    @ManyToOne()
    @MapsId("professionalId")
    @JoinColumn(name = "professional_id")
    private User professional;

    @ManyToOne()
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private User company;

    public CompanyProfessional(User company, User professional){
        this.professional = professional;
        this.company = company;
        this.id = new CompanyProfessionalPK(company.getId(), professional.getId());
    }
}