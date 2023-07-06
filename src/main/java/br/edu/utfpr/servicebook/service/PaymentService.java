package br.edu.utfpr.servicebook.service;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.utfpr.servicebook.model.entity.Payment;
import br.edu.utfpr.servicebook.model.repository.PaymentRepository;


@Service
public class PaymentService {
    
    private final Environment environment;

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    public PaymentService(Environment environment){
        this.environment = environment;
    }

    public ResponseEntity<?> pay(Map<String, Object> paymentData){
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.mercadopago.com/v1/payments";

        try {
            String accessToken = environment.getProperty("MP_ACCESS_TOKEN");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);   
            headers.setBearerAuth(accessToken);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(paymentData, headers);

            ResponseEntity<?> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Object.class);
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Algo deu errado! Tente novamente.");
        }
            
    }

    public Payment save(Payment entity){ return paymentRepository.save(entity); }
}
