package br.edu.utfpr.servicebook.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PerspectiveAPIService {
    //chave da api deve ser inserida em .env
    @Value("${perspective.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public PerspectiveAPIService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public String analyzeComment(String comment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construa o corpo da solicitação conforme a documentação da API do Perspective
        // Aqui está um exemplo simples para análise de toxicidade
        String requestBody = "{\"comment\":{\"text\":\"" + comment + "\"},\"languages\":[\"en\"], \"requestedAttributes\":{\"TOXICITY\":{}, \"UNSUBSTANTIAL\":{}}}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Faça a solicitação POST para a API do Perspective
        String apiUrl = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=" + apiKey;

        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

        String responseBody = responseEntity.getBody();
        boolean isOffensive = isCommentOffensive(responseBody, 0.5); // Ajuste o limiar conforme necessário

        return isOffensive ? "Comentário Ofensivo" : "Comentário Não Ofensivo";
    }

    // valida retornando true ou false
    private boolean isCommentOffensive(String responseJson, double toxicityThreshold) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseJson);

            if (rootNode.has("attributeScores")) {
                JsonNode attributeScoresNode = rootNode.get("attributeScores");

                if (attributeScoresNode.has("TOXICITY")) {
                    JsonNode toxicityNode = attributeScoresNode.get("TOXICITY");
                    double toxicityScore = toxicityNode.get("summaryScore").get("value").asDouble();

                    // Compara a pontuação de toxicidade com o limite definido
                    return toxicityScore > toxicityThreshold;
                }
            }

            // Se o nó ou atributo não estiver presente, considera como não ofensivo
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            // Em caso de erro, considere como não ofensivo
            return false;
        }
    }
}
