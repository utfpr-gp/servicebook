package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Client;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.entity.Professional;
import br.edu.utfpr.servicebook.model.repository.JobRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public List<JobRequest> findByClientOrderByDateCreatedDesc(Client client) {
        return this.jobRequestRepository.findByClientOrderByDateCreatedDesc(client);
    }

    public Page<JobRequest> findByStatusAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(JobRequest.Status status, Professional professional, Pageable pageable) {
        return this.jobRequestRepository.findByStatusAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(status, professional, pageable);
    }

    public Page<JobRequest> findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(JobRequest.Status status, Expertise expertise, Professional professional, Pageable pageable) {
        return this.jobRequestRepository.findByStatusAndExpertiseAndJobCandidatesIsNullOrJobCandidates_ProfessionalNot(status, expertise, professional, pageable);
    }

    public Page<JobRequest> findByStatusAndJobContracted_Professional(JobRequest.Status status, Professional professional, Pageable pageable) {
        return this.jobRequestRepository.findByStatusAndJobContracted_Professional(status, professional, pageable);
    }

    public Page<JobRequest> findByStatusAndExpertiseAndJobContracted_Professional(JobRequest.Status status, Expertise expertise, Professional professional, Pageable pageable) {
        return this.jobRequestRepository.findByStatusAndExpertiseAndJobContracted_Professional(status, expertise, professional, pageable);
    }
    public Page<JobRequest> findByStatusAndClient(JobRequest.Status status,  Client client, Pageable pageable) {
        return this.jobRequestRepository.findByStatusAndClient(status, client, pageable);
    }
    public void init() {

    }

}
