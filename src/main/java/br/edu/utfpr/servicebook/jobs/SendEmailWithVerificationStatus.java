package br.edu.utfpr.servicebook.jobs;

import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.service.EmailSenderService;
import br.edu.utfpr.servicebook.service.JobCandidateService;
import br.edu.utfpr.servicebook.service.JobRequestService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@Component
public class SendEmailWithVerificationStatus implements Job {

    public static final Long JOB_REQUEST_ID = Long.valueOf(0);

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private JobRequestService jobRequestService;
    @Autowired
    private JobCandidateService jobCandidateService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        Long jobRequestId = (Long) jobDataMap.get(SendEmailWithVerificationStatus.JOB_REQUEST_ID);

        System.out.println("Foi excluido!");
        
        if ( jobRequestId != null) {
            Optional<JobRequest> oJobRequest = jobRequestService.findById(jobRequestId);
            if (oJobRequest.isPresent()) {
                JobRequest jobRequest = oJobRequest.get();
                String status = String.valueOf(jobRequest.getStatus());
    
                if (status.equals("AVAILABLE") || status.equals("BUDGET") || status.equals("TO_HIRED")){
                    List<JobCandidate> jobCandidates = jobCandidateService.findByJobRequest(jobRequest);
    
                    for (JobCandidate jobCandidate : jobCandidates) {
                        String email = jobCandidate.getUser().getEmail();
                        String text = "Olá, o anuncio foi removido!";
    
                        try {
                            emailSenderService.sendHTMLEmail(email, "Service Book", text);
                        } catch (MessagingException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    
                }
            }
        }
        
    }
}


//disponiveis
//orçamento
//contratado