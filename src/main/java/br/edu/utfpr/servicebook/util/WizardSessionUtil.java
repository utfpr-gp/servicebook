package br.edu.utfpr.servicebook.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class WizardSessionUtil<T> {

    private final String KEY_WIZARD = "wizard";

    public T getWizardState(HttpSession httpSession, Class<T> clazz) {
        T wizardDTO = (T) httpSession.getAttribute(KEY_WIZARD);
        if(wizardDTO == null){
            try {
                wizardDTO = (T) clazz.newInstance();
                httpSession.setAttribute(KEY_WIZARD, wizardDTO);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return wizardDTO;
    }
}