package br.edu.utfpr.servicebook.util;

import br.edu.utfpr.servicebook.model.dto.CompanyDTO;
import br.edu.utfpr.servicebook.model.dto.IndividualDTO;
import br.edu.utfpr.servicebook.model.dto.UserDTO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class UserWizardUtil {

    public static final String KEY_WIZARD_INDIVIDUAL = "KEY_WIZARD_INDIVIDUAL";

    public static final String KEY_EXPERTISES = "KEY_EXPERTISES";

    public static final String KEY_WIZARD_COMPANY = "KEY_WIZARD_COMPANY";

    /**
     * Chave para o atributo da sessão que guarda se o cadastro é de empresa ou indivíduo
     */
    public static final String KEY_IS_REGISTER_COMPANY = "KEY_IS_REGISTER_COMPANY";


    /**
     * Retorna um objeto DTO para armazenar os dados do Wizard na sessão.
     * Caso não exista este DTO, ele é criado e retornado.
     * @param httpSession
     * @param clazz nome da classe do DTO
     * @param wizardKey nome da chave do atributo na sessão
     * @return
     */
    public IWizardDTO getWizardState(HttpSession httpSession, Class clazz, String wizardKey) {

        IWizardDTO wizardDTO = (IWizardDTO) httpSession.getAttribute(wizardKey);
        if(wizardDTO == null){
            try {
                wizardDTO = (IWizardDTO) clazz.newInstance();
                httpSession.setAttribute(wizardKey, wizardDTO);
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
    public IWizardDTO getWizardStateOrNull(HttpSession httpSession, Class clazz, String wizard) {

        IWizardDTO wizardDTO = (IWizardDTO) httpSession.getAttribute(wizard);

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

    public UserDTO getUserDTO(HttpSession httpSession){
        boolean isCompany = (boolean)httpSession.getAttribute(UserWizardUtil.KEY_IS_REGISTER_COMPANY);

        UserDTO userSessionDTO = null;
        if(!isCompany){
            userSessionDTO = (IndividualDTO) this.getWizardState(httpSession, IndividualDTO.class, UserWizardUtil.KEY_WIZARD_INDIVIDUAL);
        }
        else{
            userSessionDTO = (CompanyDTO) this.getWizardState(httpSession, CompanyDTO.class, UserWizardUtil.KEY_WIZARD_COMPANY);
        }
        return userSessionDTO;
    }
}