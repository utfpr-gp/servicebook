package br.edu.utfpr.servicebook.util;

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

public class NumberValidator {

    private final String ACCOUNT_SID = "AC1bb8ca34ffa4fa29e5de7bd1bac2872b";

    private final String AUTH_TOKEN = "79293c43ccaf34927d5e3f5250f0acf6";

    private final String VERIFY_SID = "VA5cb256872925ecc492c51af1dfb92f4a";

    public final String APPROVED = "approved";

    private final String number;

    private String status;

    public NumberValidator(String number) {
        this.number = "+55" + number;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendVerifySms() {
        Verification verification = Verification.creator(VERIFY_SID, this.number, "sms").create();
    }

    public void sendVerifyCode(String code) throws Exception {
        VerificationCheck verificationCheck = VerificationCheck.creator(VERIFY_SID, code).setTo(this.number).create();
        this.status = verificationCheck.getStatus();
    }

    public boolean isVerified() {
        return this.status.equals(APPROVED);
    }
}
