package br.edu.utfpr.servicebook.service;

import br.edu.utfpr.servicebook.jobs.ChangeJobRequestStatusWhenIsHiredDateExpiredJob;
import br.edu.utfpr.servicebook.jobs.DeleteJobAvailableToHideJob;
import br.edu.utfpr.servicebook.jobs.SendEmailToAuthenticateJob;
import br.edu.utfpr.servicebook.jobs.SendEmailWithVerificationCodeJob;
import br.edu.utfpr.servicebook.jobs.VerifyExpiredTokenEmailJob;
import br.edu.utfpr.servicebook.jobs.*;
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

    private static final String GROUP = "group1";

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

    /**
     * Envio do email com o código de validação da conta.
     * @param email
     * @param emailCode
     * @param link
     */
    public void sendEmailToConfirmationCode(String email, String emailCode, String link) {
        try {
            JobDetail job = JobBuilder.newJob(SendEmailWithVerificationCodeJob.class)
                    .withIdentity(SendEmailWithVerificationCodeJob.class.getSimpleName(), GROUP).build();
            job.getJobDataMap().put(SendEmailWithVerificationCodeJob.RECIPIENT_KEY, email);
            job.getJobDataMap().put(SendEmailWithVerificationCodeJob.CODE_KEY, emailCode);

            Trigger trigger = getTrigger(SendEmailWithVerificationCodeJob.class.getSimpleName(), GROUP);

            scheduler.scheduleJob(job, trigger);

            if (!scheduler.isStarted()) {
                scheduler.start();
            }

        }catch (SchedulerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Envio do email avisando sobre a desistencia da candidatura.
     * @param jobRequestId
     */
    public void sendEmailToConfirmationStatus(Long jobRequestId) {
                try {
                    JobDetail job = JobBuilder.newJob(SendEmailWithVerificationStatus.class)
                    .withIdentity(SendEmailWithVerificationStatus.class.getSimpleName(), GROUP).build();
            job.getJobDataMap().put(String.valueOf(SendEmailWithVerificationStatus.JOB_REQUEST_ID), jobRequestId);

            Trigger trigger = getTrigger(SendEmailWithVerificationStatus.class.getSimpleName(), GROUP);

            scheduler.scheduleJob(job, trigger);

            if (!scheduler.isStarted()) {
                scheduler.start();
            }

        }catch (SchedulerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Envio do email com o código e link para autenticação.
     * @param email
     * @param emailCode
     * @param link
     */
    public void sendEmailWithAuthenticatationCode(String email, String emailCode, String link, String user) {
        try {
            JobDetail job = JobBuilder.newJob(SendEmailToAuthenticateJob.class)
                    .withIdentity(SendEmailToAuthenticateJob.class.getSimpleName(), GROUP).build();
            job.getJobDataMap().put(SendEmailToAuthenticateJob.RECIPIENT_KEY, email);
            job.getJobDataMap().put(SendEmailToAuthenticateJob.CODE_KEY, emailCode);
            job.getJobDataMap().put(SendEmailToAuthenticateJob.LINK_KEY, link);
            job.getJobDataMap().put(SendEmailToAuthenticateJob.USER_KEY, user);

            Trigger trigger = getTrigger(SendEmailToAuthenticateJob.class.getSimpleName(), GROUP);

            scheduler.scheduleJob(job, trigger);

            if (!scheduler.isStarted()) {
                scheduler.start();
            }

        }catch (SchedulerException e){
            System.out.println(e.getMessage());
        }
    }

    public void verifyExpiredTokenEmailJob() {
        try{
            JobDetail job = JobBuilder.newJob(VerifyExpiredTokenEmailJob.class)
                    .withIdentity(VerifyExpiredTokenEmailJob.class.getSimpleName(), GROUP).build();

            Trigger trigger = getTriggerOnFriday(VerifyExpiredTokenEmailJob.class.getSimpleName(), GROUP);
            scheduler.scheduleJob(job, trigger);

        }catch (SchedulerException e){
            System.out.println(e.getMessage());
        }
    }

    private Trigger getTrigger(String name, String group) {
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                // FIXME startnow ativado somente para teste
               .startNow()
                //.withSchedule(CronScheduleBuilder.cronSchedule(cron))
                //.withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever())
                //.withSchedule(simpleSchedule().withIntervalInHours(24 * 3).repeatForever())
                .build();
        return trigger;
    }

    private Trigger getTriggerOnFriday(String name, String group) {
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                .startNow()
                //.withSchedule(CronScheduleBuilder.cronSchedule(cron))
                //.withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever())
                //Executa toda Sexta as 10:15am
                .withSchedule(CronScheduleBuilder.cronSchedule("0 15 10 ? * 6"))
                .build();
        return trigger;
    }

    public void updateJobRequestStatusWhenIsHiredDateExpired() {
        try{
            JobDetail job = JobBuilder.newJob(ChangeJobRequestStatusWhenIsHiredDateExpiredJob.class)
                    .withIdentity(ChangeJobRequestStatusWhenIsHiredDateExpiredJob.class.getSimpleName(), GROUP).build();

            Trigger trigger = getTriggerEveryDay(ChangeJobRequestStatusWhenIsHiredDateExpiredJob.class.getSimpleName(), GROUP);
            scheduler.scheduleJob(job, trigger);

            if (!scheduler.isStarted()) {
                scheduler.start();
            }

        }catch (SchedulerException e){
            System.out.println(e.getMessage());
        }
    }

    public void closeJobsRequestPass30DaysFromHired() {
        try{
            JobDetail job = JobBuilder.newJob(CloseJobsRequestPass30DaysFromHiredJob.class)
                    .withIdentity(CloseJobsRequestPass30DaysFromHiredJob.class.getSimpleName(), GROUP).build();

            Trigger trigger = getTriggerEveryDay(CloseJobsRequestPass30DaysFromHiredJob.class.getSimpleName(), GROUP);
            scheduler.scheduleJob(job, trigger);

            if (!scheduler.isStarted()) {
                scheduler.start();
            }

        }catch (SchedulerException e){
            System.out.println(e.getMessage());
        }
    }

    private Trigger getTriggerEveryDay(String name, String group) {
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                .startNow()
                //.withSchedule(CronScheduleBuilder.cronSchedule(cron))
                //.withSchedule(simpleSchedule().withIntervalInSeconds(1).repeatForever())
                //Executa toda dia ao 12:00am
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?"))
                .build();
        return trigger;
    }

    /**
     * Escalona o Job para remover da tabela temporária os jobs que um profissional marcou que não quer mais ver.
     * Os ids dos jobs que não podem ser mostrados para o profissional ficam numa tabela e estes ids precisam ser
     * removidos ao passar de x dias.
     */
    public void deleteJobsAvailableToHide() {
        try{
            JobDetail job = JobBuilder.newJob(DeleteJobAvailableToHideJob.class)
                    .withIdentity(DeleteJobAvailableToHideJob.class.getSimpleName(), GROUP).build();

            Trigger trigger = getTriggerEveryDay(DeleteJobAvailableToHideJob.class.getSimpleName(), GROUP);
            scheduler.scheduleJob(job, trigger);

            if (!scheduler.isStarted()) {
                scheduler.start();
            }

        }catch (SchedulerException e){
            System.out.println(e.getMessage());
        }
    }
}
