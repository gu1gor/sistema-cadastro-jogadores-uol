package com.gustavoigor.cadastro_jogadores_uol.service.contentExternal;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gustavoigor.cadastro_jogadores_uol.dto.LigaDaJusticaResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//busca codinome disponivel

@Service
public class LigaDaJusticaService {
    private final RestTemplate restTemplate;
    private static final String LIGA_URL = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml";

    public LigaDaJusticaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> buscaCodinomesDisponiveis() {
        try {
            String xml = restTemplate.getForObject(LIGA_URL, String.class);

            XmlMapper xmlMapper = new XmlMapper();

            LigaDaJusticaResponseDTO dto = xmlMapper.readValue(xml, LigaDaJusticaResponseDTO.class);

            return dto.getCodinomes();

        } catch (Exception e) {
            System.out.println("Erro ao processar o XML: " + e.getMessage());
            return List.of();
        }
    }
}




