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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class CloseJobsRequestPass30DaysFromHiredJob implements Job {
    @Autowired
    private JobCandidateService jobCandidateService;

    @Autowired
    private JobRequestService jobRequestService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Date now = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.DATE, 30);
            Date currentDatePlus30 = c.getTime();

            List<JobCandidate> jobCandidate = jobCandidateService.findAllThatPass30DaysFromHired(currentDatePlus30);
            for (JobCandidate s : jobCandidate) {
                s.setQuit(true);
                jobCandidateService.save(s);

                JobRequest jobRequest = s.getJobRequest();
                jobRequest.setStatus(JobRequest.Status.CLOSED);
                jobRequestService.save(jobRequest);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}