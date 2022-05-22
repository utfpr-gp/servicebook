package br.edu.utfpr.servicebook.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.rest.verify.v2.Service;

public class NumberValidator {

    public final String ACCOUNT_SID = "AC1bb8ca34ffa4fa29e5de7bd1bac2872b";

    public final String AUTH_TOKEN = "502bff0663f05c10c081ceb59ac7a6a7";

    public final String SERVICE_SID = "MG070c615ef25157dd05dcb1f41ec56d70";

    public final String VERIFY_SID = "VA5cb256872925ecc492c51af1dfb92f4a";

    private String number;

    public NumberValidator(String number) {
        this.setNumber(number);
    }

    public void setNumber(String number) {
        this.number = "+55" + number;
    }

    public String getNumber() {
        return this.number;
    }

    public String sendSimpleSms(String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(this.number),
                        SERVICE_SID,
                        msg)
                .create();

        return message.getSid();
    }

    public String sendVerifySms() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator(
                        VERIFY_SID,
                        this.number,
                        "sms")
                .create();

        return verification.getStatus();
    }

    public String sendVerifyCode(String codeSms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCheck verificationCheck = VerificationCheck.creator(
                VERIFY_SID, codeSms).setTo(this.number).create();

        return verificationCheck.getStatus();
    }

    public String createServiceVerifyIfNotExists() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Service service = Service.creator("Servicebook").create();

        return service.getSid();
    }
}
