package br.edu.utfpr.servicebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Value("${support.mail}")
    private String supportMail;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envia um email com texto simples.
     * @param to
     * @param subject
     * @param text
     */
    public void sendTextEmail(String to, String subject, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(text);

        mailSender.send(mail);
    }

    /**
     * Envia um email com HTML
     * @param to
     * @param subject
     * @param html
     * @throws MessagingException
     */
    public void sendHTMLEmail(String to, String subject, String html) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setContent(html, "text/html; charset=utf-8");

        mailSender.send(message);
    }

}
