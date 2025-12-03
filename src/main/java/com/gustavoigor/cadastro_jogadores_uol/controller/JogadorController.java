package com.gustavoigor.cadastro_jogadores_uol.controller;

import com.gustavoigor.cadastro_jogadores_uol.dto.CadastroRequestDTO;
import com.gustavoigor.cadastro_jogadores_uol.model.Jogador;
import com.gustavoigor.cadastro_jogadores_uol.service.JogadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    private final JogadorService jogadorService;

    public JogadorController(JogadorService timeService) {
        this.jogadorService = timeService;
    }

    @PostMapping
    public ResponseEntity<Jogador> cadastrar(@RequestBody CadastroRequestDTO dto) {
        Jogador jogador = jogadorService.cadastrarJogador(dto);
        return ResponseEntity.ok(jogador);
    }

    @GetMapping
    public ResponseEntity<List<Jogador>> listar() {
        return ResponseEntity.ok(jogadorService.listarJogadores());
    }
}