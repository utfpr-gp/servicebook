package br.edu.utfpr.servicebook.jobs;

import br.edu.utfpr.servicebook.service.EmailSenderService;
import ch.qos.logback.classic.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Date;

@Component
public class EmailSenderJob implements Job {
    @Autowired
    private EmailSenderService emailSenderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String emailDestinatario = (String) jobDataMap.get("emailDestinatario");
        String code = (String) jobDataMap.get("Codigo");

        try {
            emailSenderService.sendEmailToServer(emailDestinatario, "Service Book", code);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
