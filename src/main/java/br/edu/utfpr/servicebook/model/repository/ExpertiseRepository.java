package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Company;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise, Long> {

    /**
     * Busca uma especialidade pelo nome
     * @param name
     * @return
     */
    Optional<Expertise> findByName(String name);

//    @Query("select  p from Expertise p where " +
//            "lower(p.name) like lower(concat('%', :keyword, '%'))")
//    List<String> search(@Param("keyword") String keyword);

//    List<Expertise> findDistinctByTermIgnoreCase(@Param("term") String term);

//    @Query("SELECT p FROM Expertise p WHERE p.name = :name")
//    Optional<Expertise> findByName(@Param("name") String name);


    @Query("SELECT e FROM Expertise e WHERE NOT EXISTS (SELECT pe.id.expertiseId FROM ProfessionalExpertise pe " +
        "WHERE e.id = pe.id.expertiseId " +
        "AND pe.id.professionalId = :user)")
    List<Expertise> findExpertiseNotExist(@Param("user") Long user);

    @Query("SELECT COUNT(*) FROM Expertise")
    Optional<Expertise> countAll();
} 