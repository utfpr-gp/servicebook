package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    @Query("select p from Professional p where p.email = ?1")
    Professional findByEmailAddress(String emailAddress);
    Professional findByEmail(String email);

    @Query("select distinct p from Professional p left join ProfessionalExpertise pe on p.id = pe.professional.id where " +
            "lower(p.name) like lower(concat('%', :term, '%'))" +
            "or lower(p.description) like lower(concat('%', :term, '%')) " +
            "or lower(pe.expertise.name) like lower(concat('%', :term, '%'))")
    List<Professional> findDistinctByTermIgnoreCase(@Param("term") String term);
}