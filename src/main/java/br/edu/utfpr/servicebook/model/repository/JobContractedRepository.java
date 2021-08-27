package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.JobContracted;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.entity.Professional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobContractedRepository extends JpaRepository<JobContracted, Long> {

    @Query("SELECT j FROM JobContracted j WHERE j.professional.id = :professional_id")
    List<JobContracted> findByIdProfessional(@Param("professional_id") Long professional_id);

    /**
     * Retorna o total de trabalhos contratados de um profissional.
     *
     * @param professional
     * @return Optional<Long>
     */
    Optional<Long> countByProfessional(Professional professional);

    /**
     * Retorna o total de avaliações dos trabalhos contratados de um profissional.
     *
     * @param professional
     * @return Optional<Long>
     */
    Optional<Long> countRatingByProfessional(Professional professional);

    /**
     * Retorna o total de comentários dos trabalhos contratados de um profissional.
     *
     * @param professional
     * @return Optional<Long>
     */
    Optional<Long> countCommentsByProfessional(Professional professional);

    /**
     * Retorna o total de trabalhos contratados de um profissional para uma dada especialidade.
     *
     * @param professional
     * @param expertise
     * @return Optional<Long>
     */
    Optional<Long> countByProfessionalAndJobRequest_Expertise(Professional professional, Expertise expertise);

    /**
     * Retorna o total de avaliações dos trabalhos contratados de um profissional para uma dada especialidade.
     *
     * @param professional
     * @param expertise
     * @return Optional<Long>
     */
    Optional<Long> countRatingByProfessionalAndJobRequest_Expertise(Professional professional, Expertise expertise);

    /**
     * Retorna o total de comentários dos trabalhos contratados de um profissional para uma dada especialidade.
     *
     * @param professional
     * @param expertise
     * @return Optional<Long>
     */
    Optional<Long> countCommentsByProfessionalAndJobRequest_Expertise(Professional professional, Expertise expertise);

    Page<JobContracted> findByJobRequest_StatusAndProfessional(JobRequest.Status status, Professional professional, Pageable pageable);

    Page<JobContracted> findByJobRequest_StatusAndJobRequest_ExpertiseAndProfessional(JobRequest.Status status, Expertise expertise, Professional professional, Pageable pageable);

}
