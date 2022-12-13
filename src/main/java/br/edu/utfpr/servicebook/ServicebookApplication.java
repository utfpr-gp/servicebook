package br.edu.utfpr.servicebook;

import br.edu.utfpr.servicebook.controller.IndexController;
import br.edu.utfpr.servicebook.filter.JobRequestFilter;
import br.edu.utfpr.servicebook.service.IndexService;
import br.edu.utfpr.servicebook.service.QuartzService;
import br.edu.utfpr.servicebook.service.UserCodeService;
import br.edu.utfpr.servicebook.util.quartz.AutoWiringSpringBeanJobFactory;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@SpringBootApplication
@ServletComponentScan
public class ServicebookApplication {

    @Autowired
    IndexService indexService;

    @Autowired
    QuartzService quartzService;

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

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name","dgueb0wir",
                "api_key", "546318655587864",
                "api_secret", "UPEpuVA_PWlah9B5BrkZMx7E5VE"
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

    @Bean
    public FilterRegistrationBean<JobRequestFilter> jobRequestFilter(){
        FilterRegistrationBean<JobRequestFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JobRequestFilter());
        registrationBean.addUrlPatterns("/minha-conta/cliente");
        registrationBean.setOrder(1);

        return registrationBean;
    }
    
    @EventListener(ApplicationStartedEvent.class)
    public void initApplication() {
        System.out.println("Cleaning old email codes");
        quartzService.verifyExpiredTokenEmailJob();
        System.out.println("updating jobs request with expired hired date");
        quartzService.updateJobRequestStatusWhenIsHiredDateExpired();
        System.out.println("close jobs that pass 30 days from hired");
        quartzService.closeJobsRequestPass30DaysFromHired();
    }

    @EventListener(ApplicationStartedEvent.class)
    public void updateJobRequestStatusWhenIsHiredDateIsExpired() {
        System.out.println("updating jobs request with expired hired date");
        quartzService.updateJobRequestStatusWhenIsHiredDateExpired();
    }
}
