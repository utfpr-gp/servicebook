package br.edu.utfpr.servicebook.config;

import br.edu.utfpr.servicebook.security.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

//    @Autowired
//    UserDetailServiceImpl userDetailService;

    private static final String[] PUBLIC_URLS = {
            "/",
            "/assets/**",
            "/images/**",
            "/login"
    };

    /**
     * A ordem de definição das rotas é importante nos antMatchers.
     * As rotas mais específicas devem vir antes das mais genéricas. A primeira rota que der match será utilizada.
     * Cuidar para que as rotas do PUBLIC_URLS fiquem por último.
     * @param http
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/profissionais/busca").permitAll()
                .antMatchers("/u/**").hasAnyRole(RoleType.COMPANY, RoleType.USER)
                .antMatchers("/a/**", "/u/**").hasRole(RoleType.ADMIN)
                .antMatchers(PUBLIC_URLS).permitAll()
            .anyRequest()//qualquer outra url
                .authenticated() //precisa de autenticação
            .and()
                .exceptionHandling().accessDeniedPage("/nao-autorizado") // Redireciona para "/nao-autorizado" quando o acesso é negado
                //.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
            //.and().httpBasic() //janela flutuante
            .and()
                .formLogin()//tela de login padrão
                .loginPage("/login").permitAll()
                .failureUrl("/login?error=true")
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
