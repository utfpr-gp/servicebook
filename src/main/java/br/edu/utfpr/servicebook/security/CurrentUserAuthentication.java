package br.edu.utfpr.servicebook.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserAuthentication implements IAuthentication{
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getEmail() {
        Authentication authentication = this.getAuthentication();
        return authentication.getName();
    }
}
