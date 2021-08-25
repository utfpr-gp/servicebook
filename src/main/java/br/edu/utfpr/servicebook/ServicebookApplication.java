package br.edu.utfpr.servicebook;

import br.edu.utfpr.servicebook.controller.IndexController;
import br.edu.utfpr.servicebook.service.IndexService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class ServicebookApplication {

    @Autowired
    IndexService indexService;

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
}
