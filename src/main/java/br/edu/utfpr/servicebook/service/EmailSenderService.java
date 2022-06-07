package br.edu.utfpr.servicebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Value("${support.mail}")
    private String supportMail;

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(text);

        mailSender.send(mail);
    }
    @Async
    public void sendEmailToServer(String to, String subject, String text) throws MessagingException {
        MimeMessage mail = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mail);

        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(mail);
    }

}
