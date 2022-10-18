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
public class SendEmailWithVerificationCodeJob implements Job {

    public static final String RECIPIENT_KEY = "recipient";
    public static final String CODE_KEY = "token";


    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String email = (String) jobDataMap.get(SendEmailWithVerificationCodeJob.RECIPIENT_KEY);
        String code = (String) jobDataMap.get(SendEmailWithVerificationCodeJob.CODE_KEY);

        String text = "Olá <br> <p>Segue o código para confirmar a veracidade de seu email: " + code + ".</p> ";

        try {
            emailSenderService.sendHTMLEmail(email, "Service Book", text);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
