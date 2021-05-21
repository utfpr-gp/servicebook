package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise, Long> {
    @Query("select p from Expertise p where p.id = ?1")
    Expertise findByID(Long id);
}