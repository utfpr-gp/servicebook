package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
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

    Optional<Long> countByJobRequest(JobRequest jobRequest);

    List<JobCandidate> findByJobRequest(JobRequest jobRequest);

}