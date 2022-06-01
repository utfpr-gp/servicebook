package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.jobs.EmailSenderJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class QuartzService {
    @Autowired
    private SpringBeanJobFactory jobFactoryCDI;
    private Scheduler scheduler;
    private SchedulerFactory factory;
    private static final String RECIPIENT = "recipient";
    private static final String TOKEN = "token";
    private static final String GROUP = "group1";
    @PostConstruct
    public void init() {
        scheduler = null;
        factory = new StdSchedulerFactory();
    }

    public void sendEmailToConfirmationCode(String email, String emailCode) {
        try {
            JobDetail job = JobBuilder.newJob(EmailSenderJob.class)
                    .withIdentity(EmailSenderJob.class.getSimpleName(), GROUP).build();
            job.getJobDataMap().put(RECIPIENT, email);
            job.getJobDataMap().put(TOKEN, emailCode);
            Trigger trigger = getTrigger(EmailSenderJob.class.getSimpleName(), GROUP);

            scheduler = factory.getScheduler();
            scheduler.setJobFactory(jobFactoryCDI);
            scheduler.scheduleJob(job, trigger);

            if (!scheduler.isStarted()) {
                scheduler.start();
            }

        }catch (SchedulerException e){
            System.out.println(e.getMessage());
        }
    }

    private Trigger getTrigger(String name, String group) {
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                // FIXME startnow ativado somente para teste
                .startNow()
                //.withSchedule(CronScheduleBuilder.cronSchedule(cron))
                // .withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever())
                // .withSchedule(simpleSchedule().withIntervalInHours(24 * 3).repeatForever())
                .build();
        return trigger;
    }
}
