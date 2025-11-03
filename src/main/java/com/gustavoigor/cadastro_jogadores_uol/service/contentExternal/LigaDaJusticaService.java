package com.gustavoigor.cadastro_jogadores_uol.service.contentExternal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//busca codinome disponivel

@Service
public class LigaDaJusticaService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String LIGA_URL = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml";

    public List<String> buscaCodinomesDisponiveis() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(LIGA_URL, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseBody = responseEntity.getBody();

            List<String> codinomes = new ArrayList<>();

            String tagInicio = "<codinome>";
            String tagFim = "</codinome>";

            int inicio = 0;

            while (true) {
                int posInicio = responseBody.indexOf(tagInicio, inicio);
                int posFim = responseBody.indexOf(tagFim, inicio);

                if (posInicio == -1 || posFim == -1) {
                    break; // não há mais tags
                }

                // Extrai o texto entre as tags
                String codinome = responseBody.substring(
                        posInicio + tagInicio.length(),
                        posFim
                ).trim();

                codinomes.add(codinome);

                // Atualiza o ponto de partida da próxima busca
                inicio = posFim + tagFim.length();
            }

            return codinomes;
        }

        return List.of();

    }


}

