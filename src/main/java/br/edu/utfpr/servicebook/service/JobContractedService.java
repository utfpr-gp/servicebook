package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.JobContractedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        return this.jobContractedRepository.findByIdIndividual(id);
    }

    public Optional<Long> countByProfessional(Individual individual) {
        return this.jobContractedRepository.countByIndividual(individual);
    }

    public Optional<Long> countRatingByProfessional(Individual individual) {
        return this.jobContractedRepository.countRatingByIndividual(individual);
    }

    public Optional<Long> countCommentsByProfessional(Individual individual) {
        return this.jobContractedRepository.countCommentsByIndividual(individual);
    }

    public Optional<Long> countByProfessionalAndJobRequest_Expertise(Individual individual, Expertise expertise) {
        return this.jobContractedRepository.countByIndividualAndJobRequest_Expertise(individual, expertise);
    }

    public Optional<Long> countRatingByProfessionalAndJobRequest_Expertise(Individual individual, Expertise expertise) {
        return this.jobContractedRepository.countRatingByIndividualAndJobRequest_Expertise(individual, expertise);
    }

    public Optional<Long> countCommentsByProfessionalAndJobRequest_Expertise(Individual individual, Expertise expertise) {
        return this.jobContractedRepository.countCommentsByIndividualAndJobRequest_Expertise(individual, expertise);
    }

    public Page<JobContracted> findByJobRequest_StatusAndProfessional (JobRequest.Status status, Individual individual, Pageable pageable){
        return this.jobContractedRepository.findByJobRequest_StatusAndIndividual(status, individual, pageable);
    }

    public Page<JobContracted> findByJobRequest_StatusAndJobRequest_ExpertiseAndProfessional (JobRequest.Status status, Expertise expertise, Individual individual, Pageable pageable){
        return this.jobContractedRepository.findByJobRequest_StatusAndJobRequest_ExpertiseAndIndividual(status, expertise, individual, pageable);
    }
    public Page<JobContracted> findByJobRequest_StatusAndJobRequest_Client(JobRequest.Status status, Individual client, Pageable pageable){
        return this.jobContractedRepository.findByJobRequest_StatusAndJobRequest_Individual(status, client, pageable);
    }

    public Optional<Long> countByCompany(Company company) {
        return this.jobContractedRepository.countByCompany(company);
    }

    public Optional<Long> countRatingByCompany(Company company) {
        return this.jobContractedRepository.countRatingByCompany(company);
    }

    public Optional<Long> countCommentsByCompany(Company company) {
        return this.jobContractedRepository.countCommentsByCompany(company);
    }

    public Optional<Long> countByCompanyAndJobRequest_Expertise(Company company, Expertise expertise) {
        return this.jobContractedRepository.countByCompanyAndJobRequest_Expertise(company, expertise);
    }
    public Optional<Long> countRatingByCompanyAndJobRequest_Expertise(Company company, Expertise expertise) {
        return this.jobContractedRepository.countRatingByCompanyAndJobRequest_Expertise(company, expertise);
    }
    public Optional<Long> countCommentsByCompanyAndJobRequest_Expertise(Company company, Expertise expertise) {
        return this.jobContractedRepository.countCommentsByCompanyAndJobRequest_Expertise(company, expertise);
    }

}
