package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Company;
import br.edu.utfpr.servicebook.model.entity.Expertise;
import br.edu.utfpr.servicebook.model.entity.Job;
import br.edu.utfpr.servicebook.model.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public Job save(Job entity){ return jobRepository.save(entity); }

    public void delete(Long id) {
        this.jobRepository.deleteById(id);
    }
    public Page<Job> findAll(PageRequest pageRequest) { return this.jobRepository.findAll(pageRequest); }

    public Page<Job> findByCompany(Long company, PageRequest pageRequest) {
        return this.jobRepository.findByCompanyId(company, pageRequest);
    }

    public Optional<Job> findById(Long id) {
        return this.jobRepository.findById(id);
    }

    public Page<Job> findByCompanyAndExpertise(Company company, Expertise expertise, PageRequest pageRequest){
        return this.jobRepository.findByCompanyAndExpertise(company, expertise,pageRequest);
    }

    public Page<Job> findByCompanyIdAndExpertiseId(Long companyId, Long expertiseId, PageRequest pageRequest){
        return this.jobRepository.findByCompanyIdAndExpertiseId(companyId, expertiseId, pageRequest);
    }
}
