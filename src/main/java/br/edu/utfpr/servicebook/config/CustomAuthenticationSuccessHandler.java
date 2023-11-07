package br.edu.utfpr.servicebook.config;

import br.edu.utfpr.servicebook.security.RoleType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // retorna ROLE_ADMIN ou ROLE_USER ao invés de ADMIN ou USER
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + RoleType.ADMIN));

        if (isAdmin) {
            response.sendRedirect("a");
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }
}
