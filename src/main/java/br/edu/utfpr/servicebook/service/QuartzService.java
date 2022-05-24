package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.jobs.EmailSenderJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

@Service
public class QuartzService {
    @Autowired
    private SpringBeanJobFactory jobFactoryCDI;
    private static final String DESTINY_EMAIL = "emailDestinatario";
    private static final String CODE = "Codigo";
    private static final String GROUP = "group1";

    public void sendEmailToConfirmationCode(String email, String emailCode) {
        Scheduler scheduler = null;
        SchedulerFactory factory = new StdSchedulerFactory();
        try {
            JobDetail job = JobBuilder.newJob(EmailSenderJob.class)
                    .withIdentity(EmailSenderJob.class.getSimpleName(), GROUP).build();
            job.getJobDataMap().put(DESTINY_EMAIL, email);
            job.getJobDataMap().put(CODE, emailCode);
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
