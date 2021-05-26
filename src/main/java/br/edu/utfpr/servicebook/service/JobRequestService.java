package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.model.repository.JobRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JobRequestService {

    @Autowired
    private JobRequestRepository jobRequestRepository;

    public JobRequest save(JobRequest entity){
        return jobRequestRepository.save(entity);
    }

    public void delete(Long id){
        jobRequestRepository.deleteById(id);
    }

    public List<JobRequest> findAll(){
        return this.jobRequestRepository.findAll();
    }

    public Optional<JobRequest> findById(Long id){
        return this.jobRequestRepository.findById(id);
    }

    public void init() {

    }
}
