
package br.edu.utfpr.servicebook.service;

        import br.edu.utfpr.servicebook.model.entity.Client;
        import br.edu.utfpr.servicebook.model.entity.JobCandidate;
        import br.edu.utfpr.servicebook.model.entity.JobRequest;
        import br.edu.utfpr.servicebook.model.entity.Professional;
        import br.edu.utfpr.servicebook.model.repository.ClientRepository;
        import br.edu.utfpr.servicebook.model.repository.JobCandidateRepository;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.beans.factory.annotation.Autowired;
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
    public void init() {

    }
}
