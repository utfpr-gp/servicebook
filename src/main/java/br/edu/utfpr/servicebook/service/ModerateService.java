package br.edu.utfpr.servicebook.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.client.RestTemplate;

@Service
public class ModerateService {
    private final String rapidapiKey;
    private final String rapidapiHost;


    public ModerateService(
                             @Value("${X_RAPIDAPI_KEY}") String rapidapiKey,
                             @Value("${X_RAPIDAPI_HOST}") String rapidapiHost) {
        this.rapidapiKey = rapidapiKey;
        this.rapidapiHost = rapidapiHost;


    }


    /**
     * Verifica se a imagem que acessível via URL apresenta conteúdo NSFW (Not Safe For Work).
     * @param imageUrl
     * @return
     */
    public boolean nsfwFilter(String imageUrl) {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "https://nsfw-image-classification1.p.rapidapi.com/img/nsfw";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Rapidapi-Key", rapidapiKey);
        headers.set("X-Rapidapi-Host", rapidapiHost);

        String requestBody = "{\"url\": \"" + imageUrl + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                String subtexto = responseBody.substring(14, 18);
                float numeroFloat = Float.parseFloat(subtexto);

                System.out.println("Resposta da API: " + subtexto);
                if (numeroFloat > 0.5) {
                    return true;
                } else {
                    return false;
                }
            } else {
                System.out.println("A requisição falhou com o código de status: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            // Captura uma exceção caso ocorra um erro 400 na api
            System.out.println("Erro de cliente: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            // Captura uma exceção caso ocorra um erro 500 na api
            System.out.println("Erro de servidor: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.out.println("A requisição falhou: " + e.getMessage());
        }

        // Retorna false caso ocorra algum erro na requisição
        return false;
    }
}