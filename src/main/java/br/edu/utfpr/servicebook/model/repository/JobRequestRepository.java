package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.entity.Professional;
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
     * Retorna uma lista de requisições em um certo estado e de uma certa classe de especialidade e ainda, referente a um dado profissional
     * @param status
     * @param expertise
     * @param professional
     * @return
     */
    List<JobRequest> findByStatusAndExpertiseAndJobCandidates_Professional(JobRequest.Status status, Expertise expertise, Professional professional);
}