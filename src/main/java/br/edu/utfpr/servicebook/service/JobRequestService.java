package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.repository.JobRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JobRequestService {

    @Autowired
    private JobRequestRepository jobRequestRepository;

    public JobRequest save(JobRequest entity) {
        return jobRequestRepository.save(entity);
    }

    public void delete(Long id) {
        jobRequestRepository.deleteById(id);
    }

    public List<JobRequest> findAll() {
        return this.jobRequestRepository.findAll();
    }

    public Optional<JobRequest> findById(Long id) {
        return this.jobRequestRepository.findById(id);
    }

    public List<JobRequest> findByStatusAndExpertise(JobRequest.Status status, Expertise expertise) {
        return this.jobRequestRepository.findByStatusAndExpertise(status, expertise);
    }

    public List<JobRequest> findByStatus(JobRequest.Status status) {
        return this.jobRequestRepository.findByStatus(status);
    }

    public List<JobRequest> findByClientOrderByDateCreatedDesc(User client) {
        return this.jobRequestRepository.findByUserOrderByDateCreatedDesc(client);
    }
    public Page<JobRequest> findByStatusAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(JobRequest.Status status, User user, Pageable pageable) {
        return this.jobRequestRepository.findByStatusAndJobCandidatesIsNullOrJobCandidates_UserNot(status, user, pageable);
    }

    public Page<JobRequest> findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(JobRequest.Status status, Expertise expertise, User user, Pageable pageable) {
        return this.jobRequestRepository.findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_UserNot(status, expertise, user, pageable);
    }

    public Page<JobRequest> findAvailableByExpertise(JobRequest.Status status, Expertise expertise, Long userId, Pageable pageable) {
        return this.jobRequestRepository.findAvailableByExpertise(status, expertise, userId, pageable);
    }

    public Page<JobRequest> findAvailableAllExpertises(JobRequest.Status status, Long userId, Pageable pageable) {
        return this.jobRequestRepository.findAvailableAllExpertises(status, userId, pageable);
    }

    public Page<JobRequest> findByStatusAndJobContracted_User(JobRequest.Status status, User user, Pageable pageable) {
        return this.jobRequestRepository.findByStatusAndJobContracted_User(status, user, pageable);
    }

    public Page<JobRequest> findByStatusAndExpertiseAndJobContracted_Professional(JobRequest.Status status, Expertise expertise, User user, Pageable pageable) {
        return this.jobRequestRepository.findByStatusAndExpertiseAndJobContracted_User(status, expertise, user, pageable);
    }
    public Page<JobRequest> findByStatusAndClient(JobRequest.Status status,  User client, Pageable pageable) {
        return this.jobRequestRepository.findByStatusAndUser(status, client, pageable);
    }

    public Long countAll(){
        return this.jobRequestRepository.countAll();
    }

    /**
     * Conta a quantidade de requisições de serviço por status.
     * @param status
     * @return
     */
    public long countByStatus(JobRequest.Status status){
        return this.jobRequestRepository.countByStatus(status);
    }

    public List<JobRequest> findByDateCreatedBetween(java.sql.Date startDate, java.sql.Date endDate){
        return this.jobRequestRepository.findByDateCreatedBetween(startDate, endDate);
    }

    public List<JobRequest> findByDateCreatedMonth(int year, int month){
        return this.jobRequestRepository.findByDateCreatedMonth(year, month);
    }

    public long countByDateCreatedBetween(LocalDate startDate, LocalDate endDate){
        return this.jobRequestRepository.countByDateCreatedBetween(startDate, endDate);
    }

    public long countByDateCreatedMonth(int year, int month){
        return this.jobRequestRepository.countByDateCreatedMonth(year, month);
    }

    public long countByStatusAndDateCreatedBetween(JobRequest.Status status, LocalDate startDate, LocalDate endDate){
        return this.jobRequestRepository.countByStatusAndDateCreatedBetween(status, startDate, endDate);
    }

    public long countByExpertiseAndDateCreatedBetween(Expertise expertise, LocalDate startDate, LocalDate endDate){
        return this.jobRequestRepository.countByExpertiseAndDateCreatedBetween(expertise, startDate, endDate);
    }

    public long countByStatusAndExpertiseAndDateCreatedBetween(JobRequest.Status status, Expertise expertise, LocalDate startDate, LocalDate endDate){
        return this.jobRequestRepository.countByStatusAndExpertiseAndDateCreatedBetween(status, expertise, startDate, endDate);
    }

    public long countByExpertise(Expertise expertise){
        return this.jobRequestRepository.countByExpertise(expertise);
    }

    public long countByStatusAndExpertise(JobRequest.Status status, Expertise expertise){
        return this.jobRequestRepository.countByStatusAndExpertise(status, expertise);
    }
}