package br.edu.utfpr.servicebook.jobs;

import br.edu.utfpr.servicebook.model.dto.UserCodeDTO;
import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.model.entity.UserCode;
import br.edu.utfpr.servicebook.service.EmailSenderService;
import br.edu.utfpr.servicebook.service.UserCodeService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class VerifyExpiredTokenEmailJob implements Job {
    @Autowired
    private UserCodeService userCodeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            //deletar codigo expirado
            List<UserCode> userCode = userCodeService.findAll();
            Date now = new Date(new Date().getTime());
            for (UserCode s : userCode) {
                Date expiredDate = new Date(s.getExpiredDate().getTime());
                if (now.compareTo(expiredDate) > 0)
                    userCodeService.deleteById(s.getId());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}