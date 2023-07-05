package br.edu.utfpr.servicebook.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import org.springframework.web.client.RestTemplate;

@Service
public class ModerateService {
    private final String rapidapiKey;
    private final String rapidapiHost;
    private final String API_KEY;

    public ModerateService(
                             @Value("${X_RAPIDAPI_KEY}") String rapidapiKey,
                             @Value("${X_RAPIDAPI_HOST}") String rapidapiHost,
                             @Value("${api.key}") String API_KEY) {
        this.rapidapiKey = rapidapiKey;
        this.rapidapiHost = rapidapiHost;
        this.API_KEY = API_KEY;
    }

    /**
     * Verifica se a imagem que acessível via URL apresenta conteúdo NSFW (Not Safe For Work).
     * @param imageUrl
     * @return
     */
    public boolean isNsfwImage(String imageUrl) {
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

    /**
     * Verifica se o texto é tóxico.
     * @param text
     * @return
     */
    public boolean isToxic(String text) {
        String API_ENDPOINT = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze";

        try {
            String requestBody = "{\"comment\": {\"text\": \"" + text + "\"}, \"languages\": [\"en\"], \"requestedAttributes\": {\"TOXICITY\": {}}}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    API_ENDPOINT + "?key=" + API_KEY,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            String response = responseEntity.getBody();

            // Usar Jackson para desserializar o JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            JsonNode attributeScores = jsonNode.path("attributeScores");
            if (attributeScores.has("TOXICITY")) {
                JsonNode toxicity = attributeScores.path("TOXICITY");
                JsonNode summaryScore = toxicity.path("summaryScore");
                float score = summaryScore.path("value").floatValue();
                if(score >= 0.8){
                    return true;
                }
                else{
                    return false;
                }
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}