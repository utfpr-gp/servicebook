package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface JobCandidateRepository extends JpaRepository<JobCandidate, JobCandidatePK> {

    /**
     * Retorna uma lista de candidaturas de um dado profissional acessado pelo id
     * @param id
     * @return
     */
    List<JobCandidate> findByUser_Id(Long id);

    /**
     * Retorna uma lista de candidaturas de um dado profissional
     * @param user
     * @return
     */
    List<JobCandidate> findByUser(User user);

    /**
     * Retorna uma lista de candidaturas de um profissional que foram escolhidas para orçamento
     * @param user
     * @param chosen
     * @return
     */
    List<JobCandidate> findByUserAndChosenByBudget(User user, boolean chosen);

    /**
     * Retorna uma lista de candidaturas de um profissional para requisições de um certo estado.
     * @param status
     * @param user
     * @return
     */
    List<JobCandidate> findByJobRequest_StatusAndUser(JobRequest.Status status, User user);

    /**
     * Retorna uma lista de candidaturas de um profissional para requisições de um certo estado.
     * Retorna com paginação.
     *
     * @param status
     * @param user
     * @param pageable
     *
     * @returnPage<JobCandidate>
     */
    Page<JobCandidate> findByJobRequest_StatusAndUser(JobRequest.Status status, User user, Pageable pageable);

    /**
     * Retorna uma lista de candidaturas de um profissional para requisições de um certo estado.
     * Retorna com paginação.
     *
     * @param status
     * @param expertise
     * @param user
     * @param pageable
     *
     * @returnPage<JobCandidate>
     */
    Page<JobCandidate> findByJobRequest_StatusAndJobRequest_ExpertiseAndUser(JobRequest.Status status, Expertise expertise, User user, Pageable pageable);

    Optional<Long> countByJobRequest(JobRequest jobRequest);

    List<JobCandidate> findByJobRequest(JobRequest jobRequest);

    List<JobCandidate> findByJobRequestOrderByChosenByBudgetDesc(JobRequest jobRequest);

    /**
     * Busca os candidatos de um job que foram ou não escolhidos para orçamento.
     * @param jobRequest
     * @param chosenByBudget
     * @return
     */
    List<JobCandidate> findByJobRequestAndChosenByBudget(JobRequest jobRequest, boolean chosenByBudget);

    Page<JobCandidate> findByJobRequest_StatusAndJobRequest_User(JobRequest.Status status, User user, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from JobCandidate j where j.jobRequest.id = ?1 and j.user.id = ?2")
    void deleteById(Long jobId, Long userId);

    @Query("select j from JobCandidate j where j.jobRequest.id = ?1 and j.user.id = ?2")
    Optional<JobCandidate> findByJobIdAndUserId(Long jobId, Long userId);


}