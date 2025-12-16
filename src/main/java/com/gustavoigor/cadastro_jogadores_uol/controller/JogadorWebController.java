package com.gustavoigor.cadastro_jogadores_uol.controller;

import com.gustavoigor.cadastro_jogadores_uol.dto.CadastroRequestDTO;
import com.gustavoigor.cadastro_jogadores_uol.model.Jogador;
import com.gustavoigor.cadastro_jogadores_uol.service.JogadorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// Controller principal do projeto
// Retorna HTML, renderiza paginas

@Controller
public class JogadorWebController {

    private final JogadorService jogadorService;

    public JogadorWebController(JogadorService jogadorService) {
        this.jogadorService = jogadorService;
    }

    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("cadastro", new CadastroRequestDTO());
        return "cadastro";
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid @ModelAttribute CadastroRequestDTO dto) {
        jogadorService.cadastrarJogador(dto);
        return "redirect:/lista";
    }

    @GetMapping("/lista")
    public String listar(Model model) {
        List<Jogador> jogadores = jogadorService.listarJogadores();
        model.addAttribute("jogadores", jogadores);
        return "lista";
    }
}
