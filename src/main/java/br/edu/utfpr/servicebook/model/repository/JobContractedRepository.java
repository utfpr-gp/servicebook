package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.JobContracted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobContractedRepository extends JpaRepository<JobContracted, Long> {

    @Query("SELECT j FROM JobContracted j WHERE j.professional.id = :professional_id")
    List<JobContracted> findByIdProfessional(@Param("professional_id") Long professional_id);

}