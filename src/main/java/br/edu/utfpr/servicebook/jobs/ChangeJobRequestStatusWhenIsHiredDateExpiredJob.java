package br.edu.utfpr.servicebook.jobs;

import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.model.entity.JobContracted;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.service.JobCandidateService;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.service.JobRequestService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

/**
 * Muda o estado do JobRequest para DOING quando chega a data prevista para fazer o serviço.
 */
@Component
public class ChangeJobRequestStatusWhenIsHiredDateExpiredJob implements Job {
    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobRequestService jobRequestService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            List<JobContracted> contractedJobs = jobContractedService.findAllJobRequestsToDoing();
            for (JobContracted s : contractedJobs) {
               JobRequest jobRequest = s.getJobRequest();
               jobRequest.setStatus(JobRequest.Status.DOING);
               jobRequestService.save(jobRequest);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}