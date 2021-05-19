package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
    @Query("select p from Professional p where p.email = ?1")
    Professional findByEmailAddress(String emailAddress);
}