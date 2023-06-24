package br.edu.utfpr.servicebook.util;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

public class PhoneNumberVerificationService {
    private final String twilioVerifyServiceSid;

    public final String APPROVED = "approved";

    private final String number;

    private String status;

    public PhoneNumberVerificationService(String twilioAccountSid, String twilioAuthToken, String twilioVerifyServiceSid, String number) {
        this.number = "+55" + number;
        this.twilioVerifyServiceSid = twilioVerifyServiceSid;
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    public void sendSMSToVerification() {
        Verification.creator(twilioVerifyServiceSid, this.number, "sms").create();
    }

    public void verify(String code) throws Exception {
        this.status = VerificationCheck.creator(twilioVerifyServiceSid).setTo(this.number).create().getStatus();
    }

    public boolean isVerified() {
        return this.status.equals(APPROVED);
    }
}
