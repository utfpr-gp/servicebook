package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Individual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IndividualRepository extends JpaRepository<Individual, Long> {


    /**
     * Retorna o usuário por cpf.
     *
     * @param cpf
     * @return Optional<User>
     */
    Optional<Individual> findByCpf(String cpf);


    /**
     * Retorna o usuário por email.
     *
     * @param email
     * @return Optional<User>
     */
    Optional<Individual> findByEmail(String email);

    /**
     * Retorna o usuário por telefone.
     *
     * @param phoneNumber
     * @return Optional<User>
     */
    Optional<Individual> findByPhoneNumber(String phoneNumber);

    Optional<Individual> findByName(@Param("name") String name);

    @Query("select distinct p from Individual p left join ProfessionalExpertise pe on p.id = pe.professional.id where " +
            "lower(p.name) like lower(concat('%', :term, '%'))" +
            "or lower(p.description) like lower(concat('%', :term, '%')) " +
            "or lower(pe.expertise.name) like lower(concat('%', :term, '%'))")
    Page<Individual> findDistinctByTermIgnoreCaseWithPagination(
            String term,
            Pageable pageable);

    @Query("select distinct p from Individual p left join ProfessionalExpertise pe on p.id = pe.professional.id where " +
            "lower(p.name) like lower(concat('%', :term, '%'))" +
            "or lower(p.description) like lower(concat('%', :term, '%')) " +
            "or lower(pe.expertise.name) like lower(concat('%', :term, '%'))")
    List<Individual> findDistinctByTermIgnoreCase(String term);
}