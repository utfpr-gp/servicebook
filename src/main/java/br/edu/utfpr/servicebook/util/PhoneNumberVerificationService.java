package br.edu.utfpr.servicebook.util;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Envia SMS para o número de telefone informado e verifica se o código informado é válido.
 */
@Service
public class PhoneNumberVerificationService {

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.verify.service.sid}")
    private String twilioVerifyServiceSid;

    public final String APPROVED = "approved";

    private String status;

    @PostConstruct
    public void init() {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    public void sendSMSToVerification(String phoneNumber) {
        Verification.creator(twilioVerifyServiceSid, "+55" + phoneNumber, "sms").create();
    }

    public boolean verify(String code, String phoneNumber) throws Exception {
        this.status = VerificationCheck.creator(twilioVerifyServiceSid,code).setTo("+55" + phoneNumber).create().getStatus();
        return this.status.equals(APPROVED);
    }
}
