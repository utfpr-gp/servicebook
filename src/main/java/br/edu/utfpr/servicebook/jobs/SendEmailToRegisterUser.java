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
public class SendEmailToRegisterUser implements Job {
    public static final String RECIPIENT_KEY = "recipient";
    public static final String CODE_KEY = "token";
    public static final String LINK_KEY = "link";
    public static final String USER_KEY = "user";

    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String email = (String) jobDataMap.get(SendEmailWithVerificationCodeJob.RECIPIENT_KEY);
        String code = (String) jobDataMap.get(SendEmailWithVerificationCodeJob.CODE_KEY);
        String company = (String) jobDataMap.get(SendEmailToRegisterUser.CODE_KEY);
        String link = (String) jobDataMap.get(SendEmailToAuthenticateJob.LINK_KEY);
        String user = (String) jobDataMap.get(SendEmailToAuthenticateJob.USER_KEY);

        String text = "Olá <br> <p> A empresa "+ company + ", deseja incluir você ao quadro de funcionários." +
                " <br> Para isto é necessário realizar o cadastro na plataforma, você pode se cadastrar usando o link: " + link + ".</p> ";

        try {
            emailSenderService.sendHTMLEmail(email, "Service Book", text);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
