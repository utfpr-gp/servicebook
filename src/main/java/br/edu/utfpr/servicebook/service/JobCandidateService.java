package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.JobCandidateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JobCandidateService {

    @Autowired
    private JobCandidateRepository jobCandidateRepository;

    public Optional<Long> countByJobRequest(JobRequest jobRequest) {
        return this.jobCandidateRepository.countByJobRequest(jobRequest);
    }

    public Page<JobCandidate> findByJobRequest_StatusAndProfessional(JobRequest.Status status, Individual individual, Pageable pageable) {
        return this.jobCandidateRepository.findByJobRequest_StatusAndIndividual(status, individual, pageable);
    }

    public List<JobCandidate> findByJobRequest(JobRequest jobRequest) {
        return this.jobCandidateRepository.findByJobRequest(jobRequest);
    }

    public Page<JobCandidate> findByJobRequest_StatusAndJobRequest_Client(JobRequest.Status status, Individual client, Pageable pageable) {
        return this.jobCandidateRepository.findByJobRequest_StatusAndJobRequest_Individual(status, client, pageable);
    }

    public Page<JobCandidate> findByJobRequest_StatusAndJobRequest_ExpertiseAndProfessional(JobRequest.Status status, Expertise expertise, Individual individual, Pageable pageable) {
        return this.jobCandidateRepository.findByJobRequest_StatusAndJobRequest_ExpertiseAndIndividual(status, expertise, individual, pageable);
    }

    public JobCandidate save(JobCandidate entity){
        return jobCandidateRepository.save(entity);
    }

    public void delete(Long jobId, Long individualId) {
        jobCandidateRepository.deleteById(jobId, individualId);
    }

    public Optional<JobCandidate> findById(Long jobId, Long individualId) {
        return jobCandidateRepository.findByJobIdAndIndividualId(jobId, individualId);
    }

    public List<JobCandidate> findByJobRequestOrderByChosenByBudgetDesc(JobRequest jobRequest) {
      return this.jobCandidateRepository.findByJobRequestOrderByChosenByBudgetDesc(jobRequest);
  }
}
