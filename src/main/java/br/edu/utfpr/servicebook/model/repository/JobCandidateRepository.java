package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface JobCandidateRepository extends JpaRepository<JobCandidate, JobCandidatePK> {

    /**
     * Retorna uma lista de candidaturas de um dado profissional acessado pelo id
     * @param id
     * @return
     */
    List<JobCandidate> findByIndividual_Id(Long id);

    /**
     * Retorna uma lista de candidaturas de um dado profissional
     * @param individual
     * @return
     */
    List<JobCandidate> findByIndividual(Individual individual);

    /**
     * Retorna uma lista de candidaturas de um profissional que foram escolhidas para orçamento
     * @param individual
     * @param chosen
     * @return
     */
    List<JobCandidate> findByIndividualAndChosenByBudget(Individual individual, boolean chosen);

    /**
     * Retorna uma lista de candidaturas de um profissional para requisições de um certo estado.
     * @param status
     * @param individual
     * @return
     */
    List<JobCandidate> findByJobRequest_StatusAndIndividual(JobRequest.Status status, Individual individual);

    /**
     * Retorna uma lista de candidaturas de um profissional para requisições de um certo estado.
     * Retorna com paginação.
     *
     * @param status
     * @param individual
     * @param pageable
     *
     * @returnPage<JobCandidate>
     */
    Page<JobCandidate> findByJobRequest_StatusAndIndividual(JobRequest.Status status, Individual individual, Pageable pageable);

    /**
     * Retorna uma lista de candidaturas de um profissional para requisições de um certo estado.
     * Retorna com paginação.
     *
     * @param status
     * @param expertise
     * @param individual
     * @param pageable
     *
     * @returnPage<JobCandidate>
     */
    Page<JobCandidate> findByJobRequest_StatusAndJobRequest_ExpertiseAndIndividual(JobRequest.Status status, Expertise expertise, Individual individual, Pageable pageable);

    Optional<Long> countByJobRequest(JobRequest jobRequest);

    List<JobCandidate> findByJobRequest(JobRequest jobRequest);

    List<JobCandidate> findByJobRequestOrderByChosenByBudgetDesc(JobRequest jobRequest);

    Page<JobCandidate> findByJobRequest_StatusAndJobRequest_Individual(JobRequest.Status status, Individual individual, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from JobCandidate j where j.jobRequest.id = ?1 and j.individual.id = ?2")
    void deleteById(Long jobId, Long individualId);


    @Query("select j from JobCandidate j where j.jobRequest.id = ?1 and j.individual.id = ?2")
    Optional<JobCandidate> findByJobIdAndIndividualId(Long jobId, Long individualId);

    @Query("select j from JobCandidate j where j.hiredDate <= ?1")
    List<JobCandidate> findAllByHiredDateLessThan(Date date);
}