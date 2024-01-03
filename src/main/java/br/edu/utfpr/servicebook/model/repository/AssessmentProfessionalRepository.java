package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentProfessionalRepository extends JpaRepository<AssessmentProfessional, Long> {

    List<AssessmentProfessional> findAssessmentProfessionalById(User user);

    Optional<AssessmentProfessional> findAssessmentProfessionalByProfessional(Long professionalId);

    Optional<AssessmentProfessional> findAssessmentProfessionalByProfessional(User user);

    List<AssessmentProfessional> findAllByProfessional(User user);

    @Query("SELECT AVG(pe.quality) AS soma_quality FROM AssessmentProfessional pe WHERE pe.professional = :user")
    Double calculateTotalQualityByProfessional(User user);

    @Query("SELECT AVG(pe.punctuality) AS soma_punctuality FROM AssessmentProfessional pe WHERE pe.professional = :user")
    Double calculateTotalPunctualityByProfessional(User user);

    @Query("SELECT (AVG(pe.punctuality) + AVG(pe.quality)) / count(pe.id) FROM AssessmentProfessional pe WHERE pe.professional = :user")
    Double calculateAveragePunctualityAndQualitySumByProfessional(User user);

}
