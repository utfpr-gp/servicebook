package br.edu.utfpr.servicebook.config;

import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

//    @Autowired
//    UserDetailServiceImpl userDetailService;

    private static final String[] PERMIT_LIST = {
            "/",
            "/**",
            "/assets/**",
            "/login"
    };

    /**
     * As rotas mais específicas vem antes.
     * @param http
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/profissionais/busca").permitAll()
                .antMatchers(PERMIT_LIST).permitAll()
                .antMatchers("/u/**").hasAnyRole(RoleType.COMPANY, RoleType.USER)
                .antMatchers("/a/**", "/u/**").hasRole(RoleType.ADMIN)
                .anyRequest()//qualquer url
                .authenticated()
                //.and().httpBasic() //janela flutuante
                .and().formLogin()//tela de login padrão
                .loginPage("/login").permitAll()
                .and()
                .logout(logout -> logout.deleteCookies("JSESSIONID").logoutSuccessUrl("/").permitAll())
                .sessionManagement(session -> session.invalidSessionUrl("/login"))//Se a sessão estiver expirada ou o usuário removeu o cookie JSESSIONID, então encaminha para a tela de login.
                //.anonymous().disable()//desabilita o usuário anônimo criado por default
                .csrf().disable()//habilitar em produção
                .headers().frameOptions().disable();//para funcionar o H2
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}
