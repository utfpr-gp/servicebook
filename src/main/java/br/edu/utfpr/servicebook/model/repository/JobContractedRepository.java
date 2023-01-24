package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobContractedRepository extends JpaRepository<JobContracted, Long> {

    @Query("SELECT j FROM JobContracted j WHERE j.individual.id = :professional_id")
    List<JobContracted> findByIdIndividual(@Param("professional_id") Long professional_id);

    /**
     * Retorna o total de trabalhos contratados de um profissional.
     *
     * @param individual
     * @return Optional<Long>
     */
    Optional<Long> countByIndividual(Individual individual);

    /**
     * Retorna o total de avaliações dos trabalhos contratados de um profissional.
     *
     * @param individual
     * @return Optional<Long>
     */
    Optional<Long> countRatingByIndividual(Individual individual);

    /**
     * Retorna o total de comentários dos trabalhos contratados de um profissional.
     *
     * @param individual
     * @return Optional<Long>
     */
    Optional<Long> countCommentsByIndividual(Individual individual);

    /**
     * Retorna o total de trabalhos contratados de um profissional para uma dada especialidade.
     *
     * @param individual
     * @param expertise
     * @return Optional<Long>
     */
    Optional<Long> countByIndividualAndJobRequest_Expertise(Individual professional, Expertise expertise);

    /**
     * Retorna o total de avaliações dos trabalhos contratados de um profissional para uma dada especialidade.
     *
     * @param individual
     * @param expertise
     * @return Optional<Long>
     */
    Optional<Long> countRatingByIndividualAndJobRequest_Expertise(Individual professional, Expertise expertise);

    /**
     * Retorna o total de comentários dos trabalhos contratados de um profissional para uma dada especialidade.
     *
     * @param individual
     * @param expertise
     * @return Optional<Long>
     */
    Optional<Long> countCommentsByIndividualAndJobRequest_Expertise(Individual individual, Expertise expertise);

    Page<JobContracted> findByJobRequest_StatusAndIndividual(JobRequest.Status status, Individual individual, Pageable pageable);

    Page<JobContracted> findByJobRequest_StatusAndJobRequest_Individual(JobRequest.Status status, Individual individual, Pageable pageable);

    Page<JobContracted> findByJobRequest_StatusAndJobRequest_ExpertiseAndIndividual(JobRequest.Status status, Expertise expertise, Individual individual, Pageable pageable);

    @Query("SELECT j FROM JobContracted j WHERE j.jobRequest.id = :job_request_id")
    List<JobContracted> findByRequestId(@Param("job_request") Long job_request_id);

    @Query("select j from JobContracted j where j.jobRequest.id = ?1")
    Optional<JobContracted> findByJobIdAndIndividualId(Long jobId);

}
