package com.gustavoigor.cadastro_jogadores_uol.service;

import com.gustavoigor.cadastro_jogadores_uol.dto.CadastroRequestDTO;
import com.gustavoigor.cadastro_jogadores_uol.model.Jogador;
import com.gustavoigor.cadastro_jogadores_uol.repository.JogadorRepository;
import com.gustavoigor.cadastro_jogadores_uol.service.contentExternal.LigaDaJusticaService;
import com.gustavoigor.cadastro_jogadores_uol.service.contentExternal.VingadoresService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class JogadorService {

    private final VingadoresService vingadoresService;
    private final LigaDaJusticaService ligaDaJusticaService;
    private final JogadorRepository jogadorRepository;

    public JogadorService(VingadoresService vingadoresService,
                          LigaDaJusticaService ligaDaJusticaService,
                          JogadorRepository jogadorRepository) {

        this.vingadoresService = vingadoresService;
        this.ligaDaJusticaService = ligaDaJusticaService;
        this.jogadorRepository = jogadorRepository;
    }

    public Jogador cadastrarJogador(CadastroRequestDTO dto) {

        Objects.requireNonNull(dto, "CadastroRequestDTO não pode ser nulo");

        if (dto.getListaReferencia() == null || dto.getListaReferencia().isBlank()) {
            throw new IllegalArgumentException("É necessário informar a lista (ex: 'vingadores' ou 'liga').");
        }

        String listaEscolhida = dto.getListaReferencia().trim().toLowerCase();

        // 1 - Obter codinomes externos
        List<String> codinomesExterno;
        if (listaEscolhida.contains("vingador")) {
            codinomesExterno = vingadoresService.buscarCodinomesDisponiveis();
        } else if (listaEscolhida.contains("liga")) {
            codinomesExterno = ligaDaJusticaService.buscaCodinomesDisponiveis();
        } else {
            throw new IllegalArgumentException("Lista inválida. Use 'vingadores' ou 'liga'.");
        }

        if (codinomesExterno == null || codinomesExterno.isEmpty()) {
            throw new IllegalArgumentException("Não foi possível obter codinomes da lista selecionada.");
        }

        // 2 - Codinomes usados no banco
        List<String> codinomesUsados = jogadorRepository.findAll()
                .stream()
                .map(Jogador::getCodinome)
                .filter(Objects::nonNull)
                .toList();

        // 3 - Pegar primeiro codinome disponível
        String codinomeLivre = codinomesExterno.stream()
                .filter(c -> !codinomesUsados.contains(c))
                .findFirst()
                .orElse(null);

        if (codinomeLivre == null) {
            throw new IllegalArgumentException("A lista escolhida não possui mais codinomes disponíveis.");
        }

        // 4 - Criar jogador
        Jogador jogador = new Jogador();
        jogador.setNome(dto.getNome());
        jogador.setEmail(dto.getEmail());
        jogador.setTelefone(dto.getTelefone());
        jogador.setCodinome(codinomeLivre);

        String referencia = listaEscolhida.contains("vingador")
                ? "vingadores.json"
                : "liga_da_justica.xml";

        // CORREÇÃO AQUI — salvar como STRING normal!
        jogador.setListaReferencia(referencia);

        // 5 - Persistir
        return jogadorRepository.save(jogador);
    }

    public List<Jogador> listarJogadores() {
        return jogadorRepository.findAll();
    }
}
