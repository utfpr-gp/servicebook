package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    /**
     * Busca todas as especialidades que o usuário não possui.
     * @param user
     * @return
     */
    @Query("SELECT e FROM Expertise e WHERE NOT EXISTS (SELECT pe.id.expertiseId FROM ProfessionalExpertise pe " +
        "WHERE e.id = pe.id.expertiseId " +
        "AND pe.id.professionalId = :user)")
    List<Expertise> findExpertiseNotExist(@Param("user") Long user);

    /**
     * Busca todas as especialidades que o usuário não possui para uma dada categoria.
     * @param user
     * @param category
     * @return
     */
    @Query("SELECT e FROM Expertise e WHERE e.category.id = :category AND NOT EXISTS (SELECT pe.id.expertiseId FROM ProfessionalExpertise pe " +
            "WHERE e.id = pe.id.expertiseId " +
            "AND e.category.id = :category " +
            "AND pe.id.professionalId = :user)")
    List<Expertise> findExpertiseNotExistByUserAndCategory(@Param("user") Long user, @Param("category") Long category);

    @Query("SELECT COUNT(*) FROM Expertise")
    Long countAll();

    @Query("SELECT e FROM Expertise e WHERE e.name = :name AND e.category = :category")
    Optional<Expertise> findByNameAndCategory(@Param("name") String name, @Param("category") Category category);

    /**
     * Busca todas as especialidades de uma categoria
     * @param category
     * @return
     */
    public List<Expertise> findByCategory(Category category);

    /**
     * Busca todas as especialidades de uma categoria
     * @param categoryId
     * @return
     */
    public List<Expertise> findByCategory_Id(Long categoryId);
}