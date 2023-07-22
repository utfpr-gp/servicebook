package br.edu.utfpr.servicebook;

import br.edu.utfpr.servicebook.filter.TemplateStatisticInfoFilter;
import br.edu.utfpr.servicebook.filter.TemplateUserInfoFilter;
import br.edu.utfpr.servicebook.service.IndexService;
import br.edu.utfpr.servicebook.service.QuartzService;
import br.edu.utfpr.servicebook.util.SessionNames;
import br.edu.utfpr.servicebook.util.quartz.AutoWiringSpringBeanJobFactory;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@SpringBootApplication()
@ServletComponentScan
public class ServicebookApplication {

    @Autowired
    IndexService indexService;

    @Autowired
    QuartzService quartzService;

    @Autowired
    private Environment env;

    @Autowired
    private ServletContext servletContext;

    public static void main(String[] args) {
        SpringApplication.run(ServicebookApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner() {
//        return args -> {
//
//            // inicializa a base de dados
//            indexService.initialize();
//        };
//    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Bean para injetar o Cloudinary em outras classes.
     * @return
     */
    @Bean
    public Cloudinary cloudinary(){
        String cloudName = env.getProperty("CLOUDINARY_CLOUD_NAME");
        String apiKey = env.getProperty("CLOUDINARY_API_KEY");
        String apiSecret = env.getProperty("CLOUDINARY_API_SECRET");

        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean quartzScheduler(ApplicationContext applicationContext) {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        quartzScheduler.setJobFactory(jobFactory);

        return quartzScheduler;
    }

    /**
     * Foi desabilitado para não permitir cadastro de jobs de forma anônima.
     * Será substituído por busca ativa.
     * Filter para verificar se o usuário tem um job request e então cadastrar quando logar.
     * @return
     */
//    @Bean
//    public FilterRegistrationBean<JobRequestFilter> jobRequestFilter(){
//        FilterRegistrationBean<JobRequestFilter> registrationBean
//                = new FilterRegistrationBean<>();
//
//        registrationBean.setFilter(new JobRequestFilter());
//        registrationBean.addUrlPatterns("/minha-conta/cliente");
//        registrationBean.setOrder(1);
//
//        return registrationBean;
//    }

    /**
     * Filtro para enviar os dados para apresentação no template quando usuário está logado.
     * @return
     */
    @Bean
    public FilterRegistrationBean<TemplateUserInfoFilter> templateUserInfoFilterRegistrationBean(){
        FilterRegistrationBean<TemplateUserInfoFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new TemplateUserInfoFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    /**
     * Filtro para enviar os dados para apresentação no template quando usuário está logado.
     * Filtra apenas as páginas do profissional, uma vez apenas nesta página que é mostrado o painel lateral com as estatísticas.
     * @return
     */
    @Bean
    public FilterRegistrationBean<TemplateStatisticInfoFilter> templateStatisticInfoFilterRegistrationBean(){
        FilterRegistrationBean<TemplateStatisticInfoFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new TemplateStatisticInfoFilter());
        registrationBean.addUrlPatterns("/minha-conta/profissional/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
    
    @EventListener(ApplicationStartedEvent.class)
    public void initApplication() {
        System.out.println("Cleaning old email codes");
        quartzService.verifyExpiredTokenEmailJob();
        System.out.println("updating jobs request with expired hired date");
        quartzService.updateJobRequestStatusWhenIsHiredDateExpired();
        System.out.println("removing expired jobs available to hide");
        quartzService.deleteJobsAvailableToHide();
        System.out.println("close jobs that pass 30 days from hired");
        quartzService.closeJobsRequestPass30DaysFromHired();

        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        System.out.println(bc.encode("qwerty123"));

        servletContext.setAttribute(SessionNames.ACCESS_USER_KEY, SessionNames.ACCESS_USER_CLIENT_VALUE);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void updateJobRequestStatusWhenIsHiredDateIsExpired() {
        System.out.println("updating jobs request with expired hired date");
        quartzService.updateJobRequestStatusWhenIsHiredDateExpired();
    }
}
