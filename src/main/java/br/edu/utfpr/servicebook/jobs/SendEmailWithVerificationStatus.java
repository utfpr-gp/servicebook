package br.edu.utfpr.servicebook.jobs;

import br.edu.utfpr.servicebook.service.EmailSenderService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class SendEmailWithVerificationStatus implements Job {

    public static final String JOB_REQUEST_ID = "job_request_id";

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private JobRequestService jobRequestService;
    @Autowired
    private JobCandidateService jobCandidateService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String jobRequestId = (String) jobDataMap.get(SendEmailWithVerificationStatus.JOB_REQUEST_ID);
        Optional<JobRequest> oJobRequest = jobRequestService.findById(Long.valueOf(jobRequestId));
        
        if (oJobRequest.isPresent()) {
            JobRequest jobRequest = oJobRequest.get();
            String status = String.valueOf(jobRequest.getStatus());

            if (status.equals("AVAILABLE") || status.equals("BUDGET") || status.equals("TO_HIRED")){
                List<JobCandidate> jobCandidates = jobCandidateService.findByJobRequest(jobRequest);

                for (JobCandidate jobCandidate : jobCandidates) {
                    String email = jobCandidate.getIndividual().getEmail();
                    String text = "Olá anuncio tal foi removido";

                    System.out.println("Foi excluido!");

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


//disponiveis
//orçamento
//contratado