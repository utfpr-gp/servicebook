package br.edu.utfpr.servicebook.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class WizardSessionUtil<T> {

    public static final String KEY_WIZARD_JOB_REQUEST = "wizardJobRequest";
    public static final String KEY_WIZARD_USER = "wizardUser";

    public static final String KEY_LOGIN = "loginUser";
    public T getWizardState(HttpSession httpSession, Class<T> clazz, String wizard) {

        T wizardDTO = (T) httpSession.getAttribute(wizard);
        if(wizardDTO == null){
            try {
                wizardDTO = (T) clazz.newInstance();
                httpSession.setAttribute(wizard, wizardDTO);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return wizardDTO;
    }
}