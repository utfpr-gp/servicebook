package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpertiseRepository extends JpaRepository<Expertise, Long> {

    /**
     * Busca uma especialidade pelo nome
     * @param name
     * @return
     */
    Optional<Expertise> findByName(String name);

//    @Query("SELECT p FROM Expertise p WHERE p.name = :name")
//    Optional<Expertise> findByName(@Param("name") String name);


}