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

    @Query("select p from Professional p where lower(p.name) like lower(concat('%', :name, '%'))")
    List<Professional> findAllByNameIgnoreCase(@Param("name") String name);
}