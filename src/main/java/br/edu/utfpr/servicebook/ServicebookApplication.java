package br.edu.utfpr.servicebook;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class ServicebookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicebookApplication.class, args);
    }

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
