package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertise;
import br.edu.utfpr.servicebook.model.entity.ProfessionalExpertisePK;
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
     * Retorna uma lista de especialidades de um profissional
     *
     * @param professional
     * @return
     */
    List<ProfessionalExpertise> findByProfessional(Professional professional);

}
