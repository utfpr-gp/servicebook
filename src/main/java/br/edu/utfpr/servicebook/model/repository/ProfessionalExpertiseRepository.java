package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfessionalExpertiseRepository extends JpaRepository<ProfessionalExpertise, ProfessionalExpertisePK> {

    /**
     * Retorna a avaliação da especialidade de um profissional
     *
     * @param professional_id
     * @param expertise_id
     * @return Optional<Integer>
     */
    @Query("SELECT pe.rating FROM ProfessionalExpertise pe WHERE pe.professional.id = :professional_id AND pe.expertise.id = :expertise_id")
    Optional<Integer> selectRatingByProfessionalAndExpertise(@Param("professional_id") Long professional_id, @Param("expertise_id") Long expertise_id);

    /**
     * Retorna uma especialidade profissional, dado um profissional e sua especialidade
     *
     * @param user
     * @param expertise
     * @return Optional<ProfessionalExpertise>
     */
    Optional<ProfessionalExpertise> findByProfessionalAndExpertise(User user, Expertise expertise);

    /**
     * Retorna uma lista de especialidades de um profissional
     *
     * @param user
     * @return
     */

    List<ProfessionalExpertise> findByProfessional(User user);

}
