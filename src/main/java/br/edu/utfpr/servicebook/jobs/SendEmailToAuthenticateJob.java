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
public class SendEmailToAuthenticateJob implements Job {

    public static final String RECIPIENT_KEY = "recipient";
    public static final String CODE_KEY = "token";
    public static final String LINK_KEY = "link";
    public static final String USER = "user";

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String email = (String) jobDataMap.get(SendEmailToAuthenticateJob.RECIPIENT_KEY);
        String code = (String) jobDataMap.get(SendEmailToAuthenticateJob.CODE_KEY);
        String link = (String) jobDataMap.get(SendEmailToAuthenticateJob.LINK_KEY);
        String user = (String) jobDataMap.get(SendEmailToAuthenticateJob.USER);

        String text = "Olá " + user + "!, <br> <p>Segue o código de acesso: " + code + ".</p> " +
                "<p> Você pode também se autenticar clicando no link: " + link + "</p>";

        try {
            emailSenderService.sendHTMLEmail(email, "Service Book", text);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
