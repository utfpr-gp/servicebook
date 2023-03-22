package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
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
    Optional<Long> countByIndividualAndJobRequest_Expertise(Individual individual, Expertise expertise);

    /**
     * Retorna o total de avaliações dos trabalhos contratados de um profissional para uma dada especialidade.
     *
     * @param individual
     * @param expertise
     * @return Optional<Long>
     */
    Optional<Long> countRatingByIndividualAndJobRequest_Expertise(Individual individual, Expertise expertise);

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

    //@Query("SELECT j FROM JobContracted j WHERE j.jobRequest.id = :job_request_id")
    Optional<JobContracted> getJobContractedByJobRequest_Id(Long jobRequestId);

    /**
     * Retorna o JobContracted para o respectivo JobRequest, sendo que a relação é 1x1.
     * @param jobRequest
     * @return
     */
    Optional<JobContracted> findByJobRequest(JobRequest jobRequest);

    /**
     * Retorna um JobContracted para o respectivo JobRequest.
     * @param jobRequestId id do JobRequest
     * @return
     */
    Optional<JobContracted> findByJobRequest_Id(Long jobRequestId);


//    @Query("select j from JobContracted j where j.hiredDate <= ?1")
//    List<JobContracted> findAllByHiredDateLessThan(Date date);

    /**
     * Busca os JobContracted expirados em um número de dias.
     * É útil quando o cliente não finaliza explicitamente um JobRequest. Então, busca por data de contratação
     * acima de 30 dias.
     * @param now data corrente
     * @param days quantidade de dias para ser considerado expirado
     * @return
     */
    @Query("select j from JobContracted j where j.jobRequest.status = :status and (j.hiredDate + :days) <= :now")
    List<JobContracted> findAllJobContractedExpired(Date now, int days, JobRequest.Status status);


    /**
     * Busca os JobContacted no estado de TO_DO para mudar automaticamente para DOING.
     *
     * @param now
     * @param status
     * @return
     */
    @Query("select j from JobContracted j where j.jobRequest.status = :status and j.todoDate >= :now")
    List<JobContracted> findAllJobContractedToDoing(Date now, JobRequest.Status status);

}
