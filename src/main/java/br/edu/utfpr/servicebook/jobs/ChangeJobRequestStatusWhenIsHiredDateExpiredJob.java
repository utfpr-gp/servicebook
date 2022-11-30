package br.edu.utfpr.servicebook.jobs;

import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.service.JobCandidateService;
import br.edu.utfpr.servicebook.service.JobRequestService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ChangeJobRequestStatusWhenIsHiredDateExpiredJob implements Job {
    @Autowired
    private JobCandidateService jobCandidateService;
    @Autowired
    private JobRequestService jobRequestService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Date now = new Date(new Date().getTime());
            List<JobCandidate> jobCandidates = jobCandidateService.findAllByHiredDateLessThan(now);
            for (JobCandidate s : jobCandidates) {
               Optional<JobRequest> oJobRequest = jobRequestService.findById(s.getJobRequest().getId());

               if (oJobRequest.isPresent()) {
                   JobRequest jobRequest = oJobRequest.get();
                   jobRequest.setStatus(JobRequest.Status.DOING);
                   jobRequestService.save(jobRequest);
               }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}