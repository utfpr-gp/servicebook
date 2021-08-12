package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.JobContracted;
import br.edu.utfpr.servicebook.model.repository.JobContractedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return this.jobContractedRepository.findByIdProfessional(id);
    }

    public Integer countByIdProfessional(Long id) {
        return this.jobContractedRepository.countByIdProfessional(id);
    }

    public Integer countRatingByIdProfessional(Long id) {
        return this.jobContractedRepository.countRatingByIdProfessional(id);
    }

    public Integer countCommentsByIdProfessional(Long id) {
        return this.jobContractedRepository.countCommentsByIdProfessional(id);
    }

}
