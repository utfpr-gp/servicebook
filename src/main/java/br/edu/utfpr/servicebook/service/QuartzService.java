package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.jobs.EmailSenderJob;
import br.edu.utfpr.servicebook.jobs.VerifyExpiredTokenEmailJob;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.text.html.Option;

import java.util.Date;
import java.util.Optional;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Service
public class QuartzService {
    @Autowired
    private SpringBeanJobFactory jobFactoryCDI;
    private Scheduler scheduler;
    private SchedulerFactory factory;
    private static final String RECIPIENT = "recipient";
    private static final String TOKEN = "token";
    private static final String GROUP = "group1";
    private static final String USER_CODE = "userCode";

    @PostConstruct
    public void init() {
        scheduler = null;
        factory = new StdSchedulerFactory();
        try {
            scheduler = factory.getScheduler();
            scheduler.setJobFactory(jobFactoryCDI);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendEmailToConfirmationCode(String email, String emailCode) {
        try {
            JobDetail job = JobBuilder.newJob(EmailSenderJob.class)
                    .withIdentity(EmailSenderJob.class.getSimpleName(), GROUP).build();
            job.getJobDataMap().put(RECIPIENT, email);
            job.getJobDataMap().put(TOKEN, emailCode);
            Trigger trigger = getTrigger(EmailSenderJob.class.getSimpleName(), GROUP);

            scheduler.scheduleJob(job, trigger);

            if (!scheduler.isStarted()) {
                scheduler.start();
            }

        }catch (SchedulerException e){
            System.out.println(e.getMessage());
        }
    }

    public void verifyExpiredTokenEmailJob(String code) {
        try{
            JobDetail job = JobBuilder.newJob(VerifyExpiredTokenEmailJob.class)
                    .withIdentity(VerifyExpiredTokenEmailJob.class.getSimpleName(), GROUP).build();
            job.getJobDataMap().put(USER_CODE, code);
            Trigger trigger = getTriggerPerDay(VerifyExpiredTokenEmailJob.class.getSimpleName(), GROUP);
            scheduler.scheduleJob(job, trigger);

        }catch (SchedulerException e){
            System.out.println(e.getMessage());
        }
    }

    private Trigger getTrigger(String name, String group) {
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                // FIXME startnow ativado somente para teste
                //.startNow()
                //.withSchedule(CronScheduleBuilder.cronSchedule(cron))
                //.withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever())
                //.withSchedule(simpleSchedule().withIntervalInHours(24 * 3).repeatForever())
                .build();
        return trigger;
    }

    private Trigger getTriggerPerDay(String name, String group) {
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                .startAt(tomorrow)
                //.withSchedule(CronScheduleBuilder.cronSchedule(cron))
                //.withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever())
                //.withSchedule(simpleSchedule().withIntervalInSeconds(24))
                .build();
        return trigger;
    }
}
