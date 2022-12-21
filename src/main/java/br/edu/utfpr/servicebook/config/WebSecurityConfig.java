package br.edu.utfpr.servicebook.config;

import br.edu.utfpr.servicebook.security.RoleType;
import br.edu.utfpr.servicebook.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    private static final String[] PERMIT_LIST = {
            "/",
            "/assets/**",
            "/login"
    };

    /**
     * As rotas mais específicas vem antes.
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
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
                .and().logout()
                    .logoutSuccessUrl("/").permitAll()
                .and()
                    .csrf().disable()//habilitar em produção
                    .headers().frameOptions().disable();//para funcionar o H2
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebInvocationPrivilegeEvaluator webInvocationPrivilegeEvaluator(FilterSecurityInterceptor filterSecurityInterceptor) {
//        return new DefaultWebInvocationPrivilegeEvaluator(filterSecurityInterceptor);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());

//        auth.inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder().encode("qwerty")).roles(RoleType.USER)
//                .and()
//                .withUser("admin").password(passwordEncoder().encode("qwerty")).roles(RoleType.USER, RoleType.ADMIN);

    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

}
