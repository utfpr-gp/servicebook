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
public class SendEmailWithConfirmationUser implements Job {
    public static final String RECIPIENT_KEY = "recipient";
    public static final String CODE_KEY = "token";
    public static final String LINK_KEY = "link";
    public static final String USER_KEY = "user";

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String email = (String) jobDataMap.get(SendEmailWithConfirmationUser.RECIPIENT_KEY);
        String code = (String) jobDataMap.get(SendEmailWithConfirmationUser.CODE_KEY);
        String link = (String) jobDataMap.get(SendEmailWithConfirmationUser.LINK_KEY);
        String company = (String) jobDataMap.get(SendEmailWithConfirmationUser.USER_KEY);

        String text = "Olá " + "! <br> <p> a empresa "+ company + ", deseja incluir você ao quadro de funcionários." +
                " Se desejar fazer parte da empresa basta confirmar acessando o link: " + link + ".</p> ";

        try {
            emailSenderService.sendHTMLEmail(email, "Service Book", text);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
