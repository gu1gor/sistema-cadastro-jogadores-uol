package com.gustavoigor.cadastro_jogadores_uol.Test;

import com.gustavoigor.cadastro_jogadores_uol.service.contentExternal.LigaDaJusticaService;

//Lista os codinomes do link .xml (liga da justiça)

public class TesteLigaDaJustica {
    public static void main(String[] args) {
        LigaDaJusticaService service = new LigaDaJusticaService();
        var codinomes = service.buscaCodinomesDisponiveis();

        codinomes.forEach(System.out::println);
    }
}
