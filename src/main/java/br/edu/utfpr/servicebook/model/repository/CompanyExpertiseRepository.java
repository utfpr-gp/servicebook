package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyExpertiseRepository extends JpaRepository<CompanyExpertise, CompanyExpertisePK> {
    /**
     * Retorna a avaliação da especialidade de um profissional
     *
     * @param company_id
     * @param expertise_id
     * @return Optional<Integer>
     */
    @Query("SELECT pe.rating FROM CompanyExpertise pe WHERE pe.company.id = :company_id AND pe.expertise.id = :expertise_id")
    Optional<Integer> selectRatingByCompanyAndExpertise(@Param("company_id") Long company_id, @Param("expertise_id") Long expertise_id);

    /**
     * Retorna uma especialidade profissional, dado um profissional e sua especialidade
     *
     * @param company
     * @param expertise
     * @return Optional<CompanyExpertise>
     */
    Optional<CompanyExpertise> findByCompanyAndExpertise(Company company, Expertise expertise);

    /**
     * Retorna uma lista de especialidades de um profissional
     *
     * @param company
     * @return
     */

    List<CompanyExpertise> findByCompany(Company company);
}
