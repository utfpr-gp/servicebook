package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRequestRepository extends JpaRepository<JobRequest, Long> {

    /**
     * Busca por JobRequest em um determinado estado e para uma dada especialidade.
     * @param status
     * @param expertise
     * @return
     */
    List<JobRequest> findByStatusAndExpertise(JobRequest.Status status, Expertise expertise);

    /**
     * Busca as requisições por status, indiferente da especialidade.
     * @param status
     * @return
     */
    List<JobRequest> findByStatus(JobRequest.Status status);

    /**
     * Retorna uma lista de requisições em um certo estado e de uma certa classe de especialidade e ainda, referente a um dado profissional
     * @param status
     * @param expertise
     * @param individual
     * @return
     */
    List<JobRequest> findByStatusAndExpertiseAndJobCandidates_Individual(JobRequest.Status status, Expertise expertise, Individual individual);

    /**
     * Retorna uma lista de requisições em um certo estado de todas as especialidades e ainda, referente a um dado profissional
     * @param status
     * @param individual
     * @return
     */
    List<JobRequest> findByStatusAndJobCandidates_Individual(JobRequest.Status status, Individual individual);

    /**
     * Retorna uma lista de requisições de um determinado Status e Especialidade que ainda não receberam candidaturas.
     * Quando um JobRequest ainda não receber candidaturas, a lista de JobCandidate é null
     * @param status
     * @param expertise
     * @return
     */
    List<JobRequest> findByStatusAndExpertiseAndJobCandidatesIsNull(JobRequest.Status status, Expertise expertise);

    /**
     * Retorna uma lista de requisições de um determinado Status e todas Especialidades que ainda não receberam candidaturas.
     * Quando um JobRequest ainda não receber candidaturas, a lista de JobCandidate é null
     * @param status
     * @return
     */
    List<JobRequest> findByStatusAndJobCandidatesIsNull(JobRequest.Status status);

    /**
     * Retorna uma lista de requisições de um determinado Status e Especialidade que um dado profissional ainda não se
     * candidatou.
     * Notar que quando um JobRequest náo recebeu ainda a primeira candidatura, a lista de JobCandidate é null e
     * portanto, não tem como alcançar a propriedade Professional
     * @param status
     * @param expertise
     * @param individual
     * @return
     */
    List<JobRequest> findByStatusAndExpertiseAndJobCandidates_IndividualNot(JobRequest.Status status, Expertise expertise, Individual individual);

    /**
     * Retorna uma lista de requisições de um determinado Status e de todas Especialidades que um dado profissional ainda não se
     * candidatou.
     * @param status
     * @param individual
     * @return
     */
    List<JobRequest> findByStatusAndJobCandidates_IndividualNot(JobRequest.Status status, Individual individual);

    /**
     * Retorna uma lista de requisições de um determinado Status e Especialidade que ainda não tiveram candidaturas ou
     * que um determinado profissional ainda não se candidatou.
     * @param status
     * @param expertise
     * @param individual
     * @return
     */
    List<JobRequest> findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_IndividualNot(JobRequest.Status status, Expertise expertise, Individual individual);

    /**
     * Retorna uma lista de requisições de um determinado Status e Especialidade que ainda não tiveram candidaturas ou
     * que um determinado profissional ainda não se candidatou.
     * @param status
     * @param expertise
     * @param individual
     * @return
     */
    Page<JobRequest> findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_IndividualNot(JobRequest.Status status, Expertise expertise, Individual individual, Pageable pageable);

    /**
     * Retorna uma lista de requisições de um determinado Status e todas especialidades que ainda não tiveram candidaturas ou
     * que um determinado profissional ainda não se candidatou.
     * @param status
     * @param individual
     * @return
     */
    List<JobRequest> findByStatusAndJobCandidatesIsNullOrJobCandidates_IndividualNot(JobRequest.Status status, Individual individual);

    /**
     * Retorna uma lista de requisições de um determinado Status e todas especialidades que ainda não tiveram candidaturas ou
     * que um determinado profissional ainda não se candidatou.
     * Retorna com paginação.
     * @param status
     * @param individual
     * @return
     */
    Page<JobRequest> findByStatusAndJobCandidatesIsNullOrJobCandidates_IndividualNot(JobRequest.Status status, Individual individual, Pageable pageable);

    /**
     * Retorna uma lista de requisições em certo Status e Especialidade cujo o profissional foi contratado para realizar
     * Neste caso, o Status pode ser CLOSED ou TO_DO, quando um JobContracted é criado após um orçamento.
     * @param status
     * @param expertise
     * @param individual
     * @return
     */
    List<JobRequest> findByStatusAndExpertiseAndJobContracted_Individual(JobRequest.Status status, Expertise expertise, Individual individual);

    Page<JobRequest> findByStatusAndJobContracted_Individual(JobRequest.Status status, Individual individual, Pageable pageable);

    Page<JobRequest> findByStatusAndExpertiseAndJobContracted_Individual(JobRequest.Status status, Expertise expertise, Individual individual, Pageable pageable);

    List<JobRequest> findByIndividualOrderByDateCreatedDesc(Individual client);

    Page<JobRequest> findByStatusAndIndividual(JobRequest.Status status, Individual client, Pageable pageable);

}