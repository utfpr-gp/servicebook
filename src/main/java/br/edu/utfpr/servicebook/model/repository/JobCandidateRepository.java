package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobCandidateRepository extends JpaRepository<JobCandidate, JobCandidatePK> {

    /**
     * Retorna uma lista de candidaturas de um dado profissional acessado pelo id
     * @param id
     * @return
     */
    List<JobCandidate> findByProfessional_Id(Long id);

    /**
     * Retorna uma lista de candidaturas de um dado profissional
     * @param professional
     * @return
     */
    List<JobCandidate> findByProfessional(Professional professional);

    /**
     * Retorna uma lista de candidaturas de um profissional que foram escolhidas para orçamento
     * @param professional
     * @param chosen
     * @return
     */
    List<JobCandidate> findByProfessionalAndChosenByBudget(Professional professional, boolean chosen);

    /**
     * Retorna uma lista de candidaturas de um profissional para requisições de um certo estado.
     * @param status
     * @param professional
     * @return
     */
    List<JobCandidate> findByJobRequest_StatusAndProfessional(JobRequest.Status status, Professional professional);

    /**
     * Retorna uma lista de candidaturas de um profissional para requisições de um certo estado.
     * Retorna com paginação.
     *
     * @param status
     * @param professional
     * @param pageable
     *
     * @returnPage<JobCandidate>
     */
    Page<JobCandidate> findByJobRequest_StatusAndProfessional(JobRequest.Status status, Professional professional, Pageable pageable);

    /**
     * Retorna uma lista de candidaturas de um profissional para requisições de um certo estado.
     * Retorna com paginação.
     *
     * @param status
     * @param expertise
     * @param professional
     * @param pageable
     *
     * @returnPage<JobCandidate>
     */
    Page<JobCandidate> findByJobRequest_StatusAndJobRequest_ExpertiseAndProfessional(JobRequest.Status status, Expertise expertise, Professional professional, Pageable pageable);

    Optional<Long> countByJobRequest(JobRequest jobRequest);

}