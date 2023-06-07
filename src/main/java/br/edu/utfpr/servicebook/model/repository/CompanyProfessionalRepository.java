package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface CompanyProfessionalRepository extends JpaRepository<CompanyProfessional, CompanyProfessionalPK> {


    /**
     * Retorna uma especialidade profissional, dado um profissional e sua especialidade
     *
     * @param user
     * @param company
     * @return Optional<CompanyProfessional>
     */
    Optional<CompanyProfessional> findByProfessionalAndCompany(User company, User user);

    @Query("SELECT pe FROM CompanyProfessional pe WHERE pe.professional.id = :professional_id AND pe.company.id = :company_id")
    Optional<Integer> findByProfessionalAndCompany1(@Param("professional_id") Long professional_id, @Param("company_id") Long company_id);


    @Query("select distinct p from User p left join CompanyProfessional pe on p.id = pe.professional.id where " +
            "lower(p.name) like lower(concat('%', :term, '%'))" +
            "or lower(p.email) like lower(concat('%', :term, '%')) " )
    List<User> findDistinctByTermIgnoreCase(String term);

    List<CompanyProfessional> findByProfessional(User company);

    /**
     * Retorna a avaliação da especialidade de um profissional
     *
     * @param professional_id
     * @param company_id
     * @return Optional<Integer>
     */
    @Query("SELECT pe.rating FROM CompanyProfessional pe WHERE pe.professional.id = :professional_id AND pe.company.id = :company_id")
    Optional<Integer> selectRatingByProfessionalAndExpertise(@Param("professional_id") Long professional_id, @Param("company_id") Long company_id);


    @Query("SELECT pe FROM CompanyProfessional pe WHERE pe.professional.isConfirmed = true")
    List<CompanyProfessional> findByCompany(User user);

    /**
     * Retorna uma especialidade profissional, dado um profissional e sua especialidade
     *
     * @param company
     * @param professional
     * @return Optional<CompanyProfessional>
     */
    Optional<CompanyProfessional> findByCompanyAndProfessional(User company, User professional);


}
