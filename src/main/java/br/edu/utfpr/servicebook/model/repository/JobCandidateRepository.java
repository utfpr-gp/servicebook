package br.edu.utfpr.servicebook.model.repository;

import br.edu.utfpr.servicebook.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobCandidateRepository extends JpaRepository<JobCandidate, JobCandidatePK> {

    /**
     * Retorna uma lista de candidaturas de um dado profissional acessado pelo id
     * @param id
     * @return
     */
    //List<JobCandidate> findById_Professional_Id(Long id);

    /**
     * Retorna uma lista de candidaturas de um dado profissional
     * @param professional
     * @return
     */
    //List<JobCandidate> findById_Professional(Professional professional);

    /**
     * Retorna uma lista de candidaturas de um profissional que foram escolhidas para orçamento
     * @param professional
     * @param chosen
     * @return
     */
    //List<JobCandidate> findById_ProfessionalAndChosenByBudget(Professional professional, boolean chosen);

    /**
     * Retorna uma lista de candidaturas de um profissional para requisições de um certo estado.
     * @param status
     * @param professional
     * @return
     */
    //List<JobCandidate> findById_JobRequestStatusAndId_Professional(JobRequest.Status status, Professional professional);

    /**
     * Busca por uma lista de candidaturas para uma requisição em um certo estado para uma certa especialidade, indiferente do profissional
     * @param status
     * @param expertise
     * @return
     */
   // List<JobCandidate> findById_JobRequest_StatusAndAndId_JobRequest_Expertise(JobRequest.Status status, Expertise expertise);

    /**
     * Busca por uma lista de candidaturas de um profissional para uma requisição em um certo estado referente a uma especialidade
     * @param status
     * @param expertise
     * @param professional
     * @return
     */
   // List<JobCandidate> findById_JobRequest_StatusAndAndId_JobRequest_ExpertiseAndId_Professional(JobRequest.Status status, Expertise expertise, Professional professional);
}