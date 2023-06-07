package br.edu.utfpr.servicebook.model.dto;

import br.edu.utfpr.servicebook.model.entity.User;
import br.edu.utfpr.servicebook.util.IWizardDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTokenDTO implements IWizardDTO, Serializable {
    private String token;
    private String email;
    private User user;

}
