package br.edu.utfpr.servicebook.jobs;

import br.edu.utfpr.servicebook.model.entity.JobAvailableToHide;
import br.edu.utfpr.servicebook.model.entity.JobRequestUserPK;
import br.edu.utfpr.servicebook.service.JobAvailableToHideService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DeleteJobAvailableToHideJob implements Job {

    @Autowired
    private JobAvailableToHideService jobAvailableToHideService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            Date now = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.DATE, 3);
            Date currentDatePlusThree = c.getTime();

            List<JobAvailableToHide> jobAvailableToHide = jobAvailableToHideService.findAllByDateLessThan(currentDatePlusThree);
            for (JobAvailableToHide s : jobAvailableToHide) {
                JobRequestUserPK id = s.getId();
                jobAvailableToHideService.deleteByJobAvailableId(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}