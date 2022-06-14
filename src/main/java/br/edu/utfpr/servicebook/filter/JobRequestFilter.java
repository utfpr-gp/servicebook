package br.edu.utfpr.servicebook.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.edu.utfpr.servicebook.util.WizardSessionUtil;
import br.edu.utfpr.servicebook.model.dto.JobRequestDTO;

@Component
@Order(1)
public class JobRequestFilter implements Filter {

    @Autowired
    private WizardSessionUtil<JobRequestDTO> wizardSessionUtil;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

//        HttpSession httpSession = req.getSession();
//        JobRequestDTO sessionDTO = wizardSessionUtil.getWizardState(httpSession, JobRequestDTO.class, WizardSessionUtil.KEY_WIZARD_JOB_REQUEST);

        chain.doFilter(request, response);
    }

    @Bean
    public FilterRegistrationBean<JobRequestFilter> loggingFilter(){
        FilterRegistrationBean<JobRequestFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JobRequestFilter());
        registrationBean.addUrlPatterns("/minha-conta/cliente/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}