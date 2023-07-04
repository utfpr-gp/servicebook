package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.Category;
import br.edu.utfpr.servicebook.model.entity.JobOpenings;
import br.edu.utfpr.servicebook.model.repository.JobOpeningsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobOpeningsService {
    @Autowired
    private JobOpeningsRepository jobOpeningsRepository;

    public JobOpenings save(JobOpenings entity){ return jobOpeningsRepository.save(entity); }

    public void delete(Long id) {
        this.jobOpeningsRepository.deleteById(id);
    }
    public Page<JobOpenings> findAll(PageRequest pageRequest) { return this.jobOpeningsRepository.findAll(pageRequest); }

    public Optional<JobOpenings> findById(Long id) {
        return this.jobOpeningsRepository.findById(id);
    }
}
