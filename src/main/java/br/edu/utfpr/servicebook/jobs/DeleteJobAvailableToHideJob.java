package br.edu.utfpr.servicebook.jobs;

import br.edu.utfpr.servicebook.model.entity.JobAvailableToHide;
import br.edu.utfpr.servicebook.model.entity.JobRequestUserPK;
import br.edu.utfpr.servicebook.service.JobAvailableToHideService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@Component
public class DeleteJobAvailableToHideJob implements Job {

    @Autowired
    private JobAvailableToHideService jobAvailableToHideService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            LocalDate now = LocalDate.now();
            LocalDate currentDatePlusThree = now.plusDays(3);

            List<JobAvailableToHide> jobAvailableToHideList = jobAvailableToHideService.findAllByDateLessThan(currentDatePlusThree);
            for (JobAvailableToHide s : jobAvailableToHideList) {
                JobRequestUserPK id = s.getId();
                jobAvailableToHideService.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}