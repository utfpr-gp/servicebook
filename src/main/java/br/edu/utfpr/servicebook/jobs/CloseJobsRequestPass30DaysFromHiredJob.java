package br.edu.utfpr.servicebook.jobs;

import br.edu.utfpr.servicebook.model.entity.JobCandidate;
import br.edu.utfpr.servicebook.model.entity.JobContracted;
import br.edu.utfpr.servicebook.model.entity.JobRequest;
import br.edu.utfpr.servicebook.service.JobCandidateService;
import br.edu.utfpr.servicebook.service.JobContractedService;
import br.edu.utfpr.servicebook.service.JobRequestService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Finaliza os JobRequests que o cliente não finalizou manualmente.
 * Então, depois de 30 dias, um JobRequest será finalizado.
 * Mas somente os JobRequests que estão no estado de DOING, ou seja, quando o profissional aceitou fazer, chegou a data
 * para fazer e não cancelou.
 * Os JobRequests em estado de TO_HIRED, serão cancelados, pois o profissional não aceitou.
 */
@Component
public class CloseJobsRequestPass30DaysFromHiredJob implements Job {
    @Autowired
    private JobContractedService jobContractedService;

    @Autowired
    private JobRequestService jobRequestService;

    /**
     * Quantidade de dias para ser considerado que um JobRequest expirou.
     */
    private final int EXPIRED_DAYS = 30;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {

            //busca os jobs expirados para finalizar
            List<JobContracted> contractedJobs = jobContractedService.findAllJobRequestsToClose(EXPIRED_DAYS);
            for (JobContracted jobContracted : contractedJobs) {

                JobRequest jobRequest = jobContracted.getJobRequest();
                jobRequest.setStatus(JobRequest.Status.CLOSED);
                jobRequestService.save(jobRequest);

                jobContracted.setFinishDate(LocalDate.now());
                jobContractedService.save(jobContracted);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {

            //busca os jobs expirados para cancelar
            List<JobContracted> contractedJobs = jobContractedService.findAllJobRequestsToCancel(EXPIRED_DAYS);
            for (JobContracted jobContracted : contractedJobs) {

                JobRequest jobRequest = jobContracted.getJobRequest();
                jobRequest.setStatus(JobRequest.Status.CANCELED);
                jobRequestService.save(jobRequest);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}