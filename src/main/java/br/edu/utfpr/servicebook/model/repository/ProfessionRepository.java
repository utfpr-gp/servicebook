package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfessionRepository extends JpaRepository<Profession, Long> {
    @Query("SELECT p FROM Profession p WHERE p.name = :name")
    Optional<Profession> findByName(@Param("name") String name);
}
