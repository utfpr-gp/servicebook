package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.JobContractedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class JobContractedService {

    @Autowired
    private JobContractedRepository jobContractedRepository;

    public JobContracted save(JobContracted entity) {
        return jobContractedRepository.save(entity);
    }

    public void delete(Long id) {
        jobContractedRepository.deleteById(id);
    }

    public List<JobContracted> findAll() {
        return this.jobContractedRepository.findAll();
    }

    public List<JobContracted> findByIdProfessional(Long id) {
        return this.jobContractedRepository.findByUserId(id);
    }

    public Optional<Long> countByProfessional(User user) {
        return this.jobContractedRepository.countByUser(user);
    }

    public Optional<Long> countRatingByProfessional(User user) {
        return this.jobContractedRepository.countRatingByUser(user);
    }

    public Optional<Long> countCommentsByProfessional(User user) {
        return this.jobContractedRepository.countCommentsByUser(user);
    }

    public Optional<Long> countByProfessionalAndJobRequest_Expertise(User user, Expertise expertise) {
        return this.jobContractedRepository.countByUserAndJobRequest_Expertise(user, expertise);
    }

    public Optional<Long> countRatingByProfessionalAndJobRequest_Expertise(User user, Expertise expertise) {
        return this.jobContractedRepository.countRatingByUserAndJobRequest_Expertise(user, expertise);
    }

    public Optional<Long> countCommentsByProfessionalAndJobRequest_Expertise(User user, Expertise expertise) {
        return this.jobContractedRepository.countCommentsByUserAndJobRequest_Expertise(user, expertise);
    }

    public Page<JobContracted> findByJobRequest_StatusAndProfessional (JobRequest.Status status, User user, Pageable pageable){
        return this.jobContractedRepository.findByJobRequest_StatusAndUser(status, user, pageable);
    }

    public Page<JobContracted> findByJobRequest_StatusAndJobRequest_ExpertiseAndProfessional (JobRequest.Status status, Expertise expertise, User user, Pageable pageable){
        return this.jobContractedRepository.findByJobRequest_StatusAndJobRequest_ExpertiseAndUser(status, expertise, user, pageable);
    }
    public Page<JobContracted> findByJobRequest_StatusAndJobRequest_Client(JobRequest.Status status, User client, Pageable pageable){
        return this.jobContractedRepository.findByJobRequest_StatusAndJobRequest_User(status, client, pageable);
    }

    /**
     * Retorna o JobContracted para o respectivo JobRequest, sendo que a relação é 1x1.
     * @param jobRequest
     * @return
     */
    public Optional<JobContracted> findByJobRequest(JobRequest jobRequest){
        return this.jobContractedRepository.findByJobRequest(jobRequest);
    }

    public Optional<JobContracted> getJobContractedByJobRequestId(Long jobRequestId){
        return this.jobContractedRepository.getJobContractedByJobRequest_Id(jobRequestId);
    }

    /**
     * Busca os JobRequests no estado de DOING para finalizar.
     * @param days
     * @return
     */
    public List<JobContracted> findAllJobRequestsToClose(int days) {
        return this.jobContractedRepository.findAllJobContractedExpired(new Date(), days, JobRequest.Status.DOING);
    }

    /**
     * Busca os JobContracted com data para iniciar o serviço, ou seja, para mudar para DOING.
     * @return
     */
    public List<JobContracted> findAllJobRequestsToDoing() {
        return this.jobContractedRepository.findAllJobContractedToDoing(new Date(), JobRequest.Status.TO_DO);
    }

    /**
     * Busca os JobRequests expirados no estado de TO_HIRED para cancelar.
     * @param days
     * @return
     */
    public List<JobContracted> findAllJobRequestsToCancel(int days) {
        return this.jobContractedRepository.findAllJobContractedExpired(new Date(), days, JobRequest.Status.TO_HIRED);
    }




}
