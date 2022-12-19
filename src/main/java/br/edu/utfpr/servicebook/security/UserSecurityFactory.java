package br.edu.utfpr.servicebook.security;

import br.edu.utfpr.servicebook.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserSecurityFactory {

    /**
     *
     * Converte e gera um UserSecurity com base nos dados de um usuário.
     *
     * @param user
     * @return
     */
    public static UserSecurity create(User user) {
        return new UserSecurity(user.getEmail(), user.getPassword(), mapToGrantedAuthorithies(user.getProfile()));
    }

    /**
     *
     * Converte o perfil do usuário para o formato utilizado pelo Spring Security.
     *
     * @param profileEnum
     * @return
     */
    private static List<GrantedAuthority> mapToGrantedAuthorithies(ProfileEnum profileEnum) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
        return authorities;
    }
}
