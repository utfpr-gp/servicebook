package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.model.entity.*;
import br.edu.utfpr.servicebook.model.repository.JobAvailableToHideRepository;
import br.edu.utfpr.servicebook.model.repository.JobCandidateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Slf4j
@Service
public class JobAvailableToHideService {

    @Autowired
    private JobAvailableToHideRepository jobAvailableToHideRepository;

    public JobAvailableToHide save(JobAvailableToHide entity){
        return jobAvailableToHideRepository.save(entity);
    }

    public List<JobAvailableToHide> findAllByDateLessThan(Date date) {
        return this.jobAvailableToHideRepository.findAllByDateLessThan(date);
    }

    public void deleteById(JobRequestUserPK id) {
        this.jobAvailableToHideRepository.deleteById(id);
    }

    public List<JobRequest> findAllByJobRequest(Long id){
        return this.jobAvailableToHideRepository.findAllByJobRequest(id);
    }
}
