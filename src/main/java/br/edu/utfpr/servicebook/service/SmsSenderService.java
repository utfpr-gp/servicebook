package br.edu.utfpr.servicebook.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderService {

    @Value("AC1bb8ca34ffa4fa29e5de7bd1bac2872b")
    private String ACCOUNT_SID;

    @Value("34206a3827ed3fecaa48d1482ca7fa8f")
    private String AUTH_TOKEN;

    @Value("MG070c615ef25157dd05dcb1f41ec56d70")
    private String SERVICE_SID;

    public void sendSmsToUser(String number, String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+55" + number),
                        SERVICE_SID,
                        msg)
                .create();

        System.out.println(message.getSid());
    }
}