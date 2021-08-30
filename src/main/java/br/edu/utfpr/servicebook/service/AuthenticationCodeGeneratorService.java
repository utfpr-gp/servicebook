package br.edu.utfpr.servicebook.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AuthenticationCodeGeneratorService {

    public String generateAuthenticationCode() {
        SecureRandom secureRandom = new SecureRandom();
        String code = "";

        for (int i = 0; i < 6; i++) {
            code += secureRandom.nextInt(9);
        }

        return code;
    }

}
