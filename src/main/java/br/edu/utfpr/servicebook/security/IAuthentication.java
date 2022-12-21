package br.edu.utfpr.servicebook.security;

import org.springframework.security.core.Authentication;

public interface IAuthentication {
    Authentication getAuthentication();
    String getEmail();
}
