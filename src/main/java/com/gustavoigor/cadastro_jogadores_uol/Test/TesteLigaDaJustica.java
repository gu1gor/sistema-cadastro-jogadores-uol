package com.gustavoigor.cadastro_jogadores_uol.Test;

import com.gustavoigor.cadastro_jogadores_uol.service.contentExternal.LigaDaJusticaService;
import org.springframework.web.client.RestTemplate;

//Lista os codinomes do link .xml (liga da justiça)

public class TesteLigaDaJustica {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        LigaDaJusticaService liga = new LigaDaJusticaService(restTemplate);

        liga.buscaCodinomesDisponiveis().forEach(System.out::println);
    }
}
