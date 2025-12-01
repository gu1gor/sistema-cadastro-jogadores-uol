package com.gustavoigor.cadastro_jogadores_uol;

import com.gustavoigor.cadastro_jogadores_uol.service.contentExternal.VingadoresService;
import org.springframework.web.client.RestTemplate;

public class TesteVingadores {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        VingadoresService vingadores = new VingadoresService(restTemplate);

        vingadores.buscarCodinomesDisponiveis().forEach(System.out::println);
    }
}

