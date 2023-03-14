package br.edu.utfpr.servicebook.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class WizardSessionUtil<T> {

    public static final String KEY_WIZARD_JOB_REQUEST = "wizardJobRequest";
    public static final String KEY_WIZARD_USER = "wizardUser";


    public static final String KEY_ERROR_JOB_REQUEST = "KEY_ERROR_JOB_REQUEST";

    public static final String KEY_LOGIN = "loginUser";
    public static final String KEY_EXERPERTISES = "wizardExerpertises";
    public static final String KEY_WIZARD_COMPANY = "wizardCompany";

    /**
     * Retorna um objeto DTO para armazenar os dados do Wizard na sessão.
     * Caso não exista este DTO, ele é criado e retornado.
     * @param httpSession
     * @param clazz nome da classe do DTO
     * @param wizard nome da chave do atributo na sessão
     * @return
     */
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

    /**
     * Retorna um objeto DTO para armazenar os dados do Wizard na sessão.
     * Caso não exista este DTO, retorna nulo.
     * @param httpSession
     * @param clazz nome da classe do DTO
     * @param wizard nome da chave do atributo na sessão
     * @return
     */
    public T getWizardStateOrNull(HttpSession httpSession, Class<T> clazz, String wizard) {

        T wizardDTO = (T) httpSession.getAttribute(wizard);

        return wizardDTO;
    }

    /**
     * Remove o atributo da sessão
     * @param httpSession
     * @param wizard nome da chave do atributo na sessão
     */
    public void removeWizardState(HttpSession httpSession, String wizard) {
        httpSession.removeAttribute(wizard);
    }
}