package com.gustavoigor.cadastro_jogadores_uol.service.contentExternal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class VingadoresService {

    private final RestTemplate restTemplate;
    private final String URL = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json";

    public VingadoresService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String gerarCodinome() {
        List<String> codinomes = buscarCodinomes();

        if (codinomes.isEmpty()) {
            throw new RuntimeException("Nenhum codinome disponível no momento.");
        }

        return escolherAleatorio(codinomes);
    }

    public List<String> buscarCodinomes() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return List.of();
            }

            return converterJson(response.getBody());

        } catch (Exception e) {
            System.out.println("Erro ao chamar API: " + e.getMessage());
            return List.of();
        }
    }

    // Converte o JSON no formato:
    // { "vingadores": [ { "codinome": "Hulk" }, ... ] }
    private List<String> converterJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);

            JsonNode array = root.get("vingadores");

            List<String> lista = new ArrayList<>();

            if (array != null && array.isArray()) {
                for (JsonNode item : array) {
                    JsonNode codinomeNode = item.get("codinome");
                    if (codinomeNode != null) {
                        lista.add(codinomeNode.asText());
                    }
                }
            }

            return lista;

        } catch (Exception e) {
            System.out.println("Erro ao converter JSON: " + e.getMessage());
            return List.of();
        }
    }

    private String escolherAleatorio(List<String> lista) {
        Random random = new Random();
        return lista.get(random.nextInt(lista.size()));
    }
}