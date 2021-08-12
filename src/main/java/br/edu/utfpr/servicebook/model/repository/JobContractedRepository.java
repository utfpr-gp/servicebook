package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.JobContracted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobContractedRepository extends JpaRepository<JobContracted, Long> {

    @Query("SELECT j FROM JobContracted j WHERE j.professional.id = :professional_id")
    List<JobContracted> findByIdProfessional(@Param("professional_id") Long professional_id);

    @Query("SELECT COUNT(j) FROM JobContracted j WHERE j.professional.id = :professional_id")
    Integer countByIdProfessional(@Param("professional_id") Long professional_id);

    @Query("SELECT COUNT(j.rating) FROM JobContracted j WHERE j.professional.id = :professional_id AND j.rating > 0")
    Integer countRatingByIdProfessional(@Param("professional_id") Long professional_id);

    @Query("SELECT COUNT(j.comments) FROM JobContracted j WHERE j.professional.id = :professional_id AND j.comments <> ''")
    Integer countCommentsByIdProfessional(@Param("professional_id") Long professional_id);

}
