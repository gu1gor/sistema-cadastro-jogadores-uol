package com.gustavoigor.cadastro_jogadores_uol.service.contentExternal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavoigor.cadastro_jogadores_uol.dto.VingadorDTO;
import com.gustavoigor.cadastro_jogadores_uol.dto.VingadoresResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class VingadoresService {

    private final RestTemplate restTemplate;
    private static final String URL = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json";

    public VingadoresService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> buscarCodinomes() {
        try {
            String json = restTemplate.getForObject(URL, String.class);

            ObjectMapper mapper = new ObjectMapper();

            // Converte JSON diretamente para DTO
            VingadoresResponseDTO dto = mapper.readValue(json, VingadoresResponseDTO.class);

            return dto.getVingadores()
                    .stream()
                    .map(VingadorDTO::getCodinome)
                    .toList();

        } catch (Exception e) {
            System.out.println("Erro ao converter JSON: " + e.getMessage());
            return List.of();
        }
    }
}