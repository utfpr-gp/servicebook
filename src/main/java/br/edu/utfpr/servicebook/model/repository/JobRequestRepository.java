package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
     * @param user
     * @return
     */
    List<JobRequest> findByStatusAndExpertiseAndJobCandidates_User(JobRequest.Status status, Expertise expertise, User user);

    /**
     * Retorna uma lista de requisições em um certo estado de todas as especialidades e ainda, referente a um dado profissional
     * @param status
     * @param user
     * @return
     */
    List<JobRequest> findByStatusAndJobCandidates_User(JobRequest.Status status, User user);

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
     * @param user
     * @return
     */
    List<JobRequest> findByStatusAndExpertiseAndJobCandidates_UserNot(JobRequest.Status status, Expertise expertise, User user);

    /**
     * Retorna uma lista de requisições de um determinado Status e de todas Especialidades que um dado profissional ainda não se
     * candidatou.
     * @param status
     * @param user
     * @return
     */
    List<JobRequest> findByStatusAndJobCandidates_UserNot(JobRequest.Status status, User user);

    /**
     * Retorna uma lista de requisições de um determinado Status e Especialidade que ainda não tiveram candidaturas ou
     * que um determinado profissional ainda não se candidatou.
     * @param status
     * @param expertise
     * @param user
     * @return
     */
    List<JobRequest> findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_UserNot(JobRequest.Status status, Expertise expertise, User user);

    /**
     * Retorna uma lista de requisições de um determinado Status e Especialidade que ainda não tiveram candidaturas ou
     * que um determinado profissional ainda não se candidatou.
     * @param status
     * @param expertise
     * @param user
     * @return
     */
    Page<JobRequest> findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_UserNot(JobRequest.Status status, Expertise expertise, User user, Pageable pageable);

    /**
     * Retorna uma lista de requisições de um determinado Status e Especialidade que não são de autoria do próprio usuário
     * e que não foram marcadas como Não Quero, ou seja, que não foram escondidas pelo profissional.
     * @param status
     * @param expertise
     * @param user
     * @param pageable
     * @return
     */
    @Query("SELECT jr FROM JobRequest jr WHERE jr.status = :status AND jr.expertise = :expertise AND jr.user.id <> :userId AND " +
            "jr.id NOT IN (SELECT jh.jobRequest.id FROM JobAvailableToHide jh WHERE jh.user.id = :userId)")
    Page<JobRequest> findAvailableByExpertise(JobRequest.Status status, Expertise expertise, Long userId, Pageable pageable);

    @Query("SELECT jr FROM JobRequest jr WHERE jr.status = :status AND jr.user.id <> :userId AND " +
            "jr.id NOT IN (SELECT jh.jobRequest.id FROM JobAvailableToHide jh WHERE jh.user.id = :userId)")
    Page<JobRequest> findAvailableAllExpertises(JobRequest.Status status, Long userId, Pageable pageable);

    /**
     * Retorna uma lista de requisições de um determinado Status e todas especialidades que ainda não tiveram candidaturas ou
     * que um determinado profissional ainda não se candidatou.
     * @param status
     * @param user
     * @return
     */
    List<JobRequest> findByStatusAndJobCandidatesIsNullOrJobCandidates_UserNot(JobRequest.Status status, User user);

    /**
     * Retorna uma lista de requisições de um determinado Status e todas especialidades que ainda não tiveram candidaturas ou
     * que um determinado profissional ainda não se candidatou.
     * Retorna com paginação.
     * @param status
     * @param user
     * @return
     */
    Page<JobRequest> findByStatusAndJobCandidatesIsNullOrJobCandidates_UserNot(JobRequest.Status status, User user, Pageable pageable);

    /**
     * Retorna uma lista de requisições em certo Status e Especialidade cujo o profissional foi contratado para realizar
     * Neste caso, o Status pode ser CLOSED ou TO_DO, quando um JobContracted é criado após um orçamento.
     * @param status
     * @param expertise
     * @param user
     * @return
     */
    List<JobRequest> findByStatusAndExpertiseAndJobContracted_User(JobRequest.Status status, Expertise expertise, User user);

    Page<JobRequest> findByStatusAndJobContracted_User(JobRequest.Status status, User user, Pageable pageable);

    Page<JobRequest> findByStatusAndExpertiseAndJobContracted_User(JobRequest.Status status, Expertise expertise, User user, Pageable pageable);

    List<JobRequest> findByUserOrderByDateCreatedDesc(User client);

    List<JobRequest> findOrderByDateCreatedAfter(Date dateFrom);

    Page<JobRequest> findByStatusAndUser(JobRequest.Status status, User client, Pageable pageable);

    @Query("SELECT COUNT(*) FROM JobRequest")
    Long countAll();

    /**
     * Conta o número de requisições de um determinado Status.
     * @param status
     * @return
     */
    Long countByStatus(JobRequest.Status status);

    Long countByDateCreatedIsBetween(Date dateFrom, Date dateTo);

    @Query("SELECT j FROM JobRequest j WHERE j.dateCreated BETWEEN :startDate AND :endDate")
    List<JobRequest> findByDateCreatedBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT j FROM JobRequest j WHERE YEAR(j.dateCreated) = :year AND MONTH(j.dateCreated) = :month")
    List<JobRequest> findByDateCreatedMonth(int year, int month);

    @Query("SELECT COUNT(j) FROM JobRequest j WHERE j.dateCreated BETWEEN :startDate AND :endDate")
    long countByDateCreatedBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(j) FROM JobRequest j WHERE YEAR(j.dateCreated) = :year AND MONTH(j.dateCreated) = :month")
    long countByDateCreatedMonth(int year, int month);

}