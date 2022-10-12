package br.edu.utfpr.servicebook.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    /**
     *
     * Gera um hash utilizando BCrypt.
     * @param pwd
     * @return
     */
    public static String generateBCrypt(String pwd) {
        if(pwd == null) {
            return pwd;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pwd);
    }

    /**
     *
     * Verifica se a senha é válida.
     *
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    public static boolean verify(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encodedPassword);
    }

}
