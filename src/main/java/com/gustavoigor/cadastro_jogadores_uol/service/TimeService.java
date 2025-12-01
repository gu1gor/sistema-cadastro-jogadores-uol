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
public class TimeService {

    private final VingadoresService vingadoresService;
    private final LigaDaJusticaService ligaDaJusticaService;
    private final JogadorRepository jogadorRepository;

    public TimeService(VingadoresService vingadoresService, LigaDaJusticaService ligaDaJusticaService, JogadorRepository jogadorRepository) {
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

        // 1 - obtem codinomes da fonte correta
        List<String> codinomesExterno;
        if (listaEscolhida.contains("vingador")) {
            codinomesExterno = vingadoresService.buscarCodinomes();
        } else if (listaEscolhida.contains("liga")) {
            codinomesExterno = ligaDaJusticaService.buscaCodinomesDisponiveis();
        } else {
            throw new IllegalArgumentException("Lista inválida. Use 'vingadores' ou 'liga'.");
        }
        if (codinomesExterno == null || codinomesExterno.isEmpty()) {
            throw new IllegalArgumentException("Não foi possivel obter codinomes da lista selecionada.");
        }

        // 2 - obter os codinomes já usados no banco
        List<String> codinomesUsados = jogadorRepository.findAll()
                .stream()
                .map(Jogador::getCodinome)
                .filter(Objects::nonNull)
                .toList();

        // 3 - encontrar o primeiro codinome disponível
        String codinomeLivre = codinomesExterno.stream()
                .filter(c -> !codinomesUsados.contains(c))
                .findFirst()
                .orElse(null);

        if (codinomeLivre == null) {
            throw new IllegalArgumentException("A lista escolhida não possui mais codinomes disponiveis.");
        }

        // 4 - criar entidade Jogador
        Jogador jogador = new Jogador();
        jogador.setNome(dto.getNome());
        jogador.setEmail(dto.getEmail());
        jogador.setTelefone(dto.getTelefone());
        jogador.setCodinome(codinomeLivre);

        // guardar a referência da lista. Seu Jogador tem List<String>, então guardamos como lista com um item.
        String referencia = listaEscolhida.contains("vingador") ? "vingadores.json" : "liga_da_justica.xml";
        jogador.setListaReferencia(List.of(referencia).toString());

        // 5 - persistir e retornar
        return jogadorRepository.save(jogador);
    }

    /**
     * Lista todos os jogadores cadastrados.
     */
    public List<Jogador> listarJogadores() {
        return jogadorRepository.findAll();
    }

}
