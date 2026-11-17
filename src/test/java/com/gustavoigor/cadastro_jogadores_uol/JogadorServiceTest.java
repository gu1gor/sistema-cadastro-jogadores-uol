package com.gustavoigor.cadastro_jogadores_uol;

import org.mockito.ArgumentCaptor;
import com.gustavoigor.cadastro_jogadores_uol.dto.CadastroRequestDTO;
import com.gustavoigor.cadastro_jogadores_uol.model.Jogador;
import com.gustavoigor.cadastro_jogadores_uol.service.JogadorService;
import com.gustavoigor.cadastro_jogadores_uol.service.contentExternal.LigaDaJusticaService;
import com.gustavoigor.cadastro_jogadores_uol.service.contentExternal.VingadoresService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.gustavoigor.cadastro_jogadores_uol.repository.JogadorRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JogadorServiceTest {

    // Test 1: Deve Rejeitar lista invalida
    @Test
    void deveRejeitarListaInvalida() {
        JogadorRepository jogadorRepository = Mockito.mock(JogadorRepository.class);
        VingadoresService vingadoresService = Mockito.mock(VingadoresService.class);
        LigaDaJusticaService ligaDaJusticaService = Mockito.mock(LigaDaJusticaService.class);

        JogadorService jogadorService = new JogadorService(
                vingadoresService,
                ligaDaJusticaService,
                jogadorRepository
        );

        CadastroRequestDTO dto = new CadastroRequestDTO();
        dto.setNome("Gustavo");
        dto.setEmail("gustavo@teste.com");
        dto.setListaReferencia("ListaInexistente");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jogadorService.cadastrarJogador(dto)
        );

        assertEquals("Lista inválida. Use 'vingadores' ou 'liga'.", exception.getMessage());
    }

    // Test 2: Deve Rejeitar lista não informada
    @Test
    void deveRejeitarListaNaoInformada() {
        CadastroRequestDTO dto = new CadastroRequestDTO();

        dto.setNome("Gustavo");
        dto.setEmail("gustavo@teste.com");

        JogadorRepository jogadorRepository = Mockito.mock(JogadorRepository.class);
        VingadoresService vingadoresService = Mockito.mock(VingadoresService.class);
        LigaDaJusticaService ligaDaJusticaService = Mockito.mock(LigaDaJusticaService.class);

        JogadorService jogadorService = new JogadorService(
                vingadoresService,
                ligaDaJusticaService,
                jogadorRepository
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> jogadorService.cadastrarJogador(dto)
        );
    }

    // Test 3: Deve Rejeitar lista vazia
    @Test
    void deveRejeitarListaVazia() {
        CadastroRequestDTO dto = new CadastroRequestDTO();

        dto.setNome("Gustavo");
        dto.setEmail("gustavo@teste.com");

        JogadorRepository jogadorRepository = Mockito.mock(JogadorRepository.class);
        VingadoresService vingadoresService = Mockito.mock(VingadoresService.class);
        LigaDaJusticaService ligaDaJusticaService = Mockito.mock(LigaDaJusticaService.class);

        JogadorService jogadorService = new JogadorService(
                vingadoresService,
                ligaDaJusticaService,
                jogadorRepository
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> jogadorService.cadastrarJogador(dto)
        );
    }

    // Test 4: Deve confirmar que consegue cadastrar jogador
    @Test
    void deveCadastrarJogadorComListaValida() {
        CadastroRequestDTO dto = new CadastroRequestDTO();

        dto.setNome("Gustavo");
        dto.setEmail("gustavo@teste.com");
        dto.setListaReferencia("vingadores");

        JogadorRepository jogadorRepository = Mockito.mock(JogadorRepository.class);
        VingadoresService vingadoresService = Mockito.mock(VingadoresService.class);
        LigaDaJusticaService ligaDaJusticaService = Mockito.mock(LigaDaJusticaService.class);

        JogadorService jogadorService = new JogadorService(
                vingadoresService,
                ligaDaJusticaService,
                jogadorRepository
        );

        Mockito.when(vingadoresService.buscarCodinomesDisponiveis())
                .thenReturn(List.of("Thor", "Hulk", "Homem de Ferro"));

        Mockito.when(jogadorRepository.findAll())
                .thenReturn(List.of());

        jogadorService.cadastrarJogador(dto);

        ArgumentCaptor<Jogador> jogadorCaptor =
                ArgumentCaptor.forClass(Jogador.class);

        Mockito.verify(jogadorRepository, Mockito.times(1))
                .save(jogadorCaptor.capture());

        Jogador jogadorSalvo = jogadorCaptor.getValue();

        assertEquals("Gustavo", jogadorSalvo.getNome());
        assertEquals("gustavo@teste.com", jogadorSalvo.getEmail());
        assertTrue(List.of("Thor", "Hulk", "Homem de Ferro")
                .contains(jogadorSalvo.getCodinome()));
    }

    // Teste 5: Deve ignorar codinome já Utilizado
    @Test
    void deveIgnorarCodinomeJaUtilizado() {
        CadastroRequestDTO dto = new CadastroRequestDTO();

        dto.setNome("Carlos");
        dto.setEmail("carlos@teste.com");
        dto.setListaReferencia("vingadores");

        JogadorRepository jogadorRepository = Mockito.mock(JogadorRepository.class);
        VingadoresService vingadoresService = Mockito.mock(VingadoresService.class);
        LigaDaJusticaService ligaDaJusticaService = Mockito.mock(LigaDaJusticaService.class);

        JogadorService jogadorService = new JogadorService(
                vingadoresService,
                ligaDaJusticaService,
                jogadorRepository
        );

        Jogador jogadorExistente = new Jogador();           // Simular que "Thor" já está ocupado

        jogadorExistente.setNome("Pedro");
        jogadorExistente.setEmail("pedro@teste.com");
        jogadorExistente.setCodinome("Thor");

        Mockito.when(jogadorRepository.findAll())
                .thenReturn(List.of(jogadorExistente));

        Mockito.when(vingadoresService.buscarCodinomesDisponiveis())
                .thenReturn(List.of("Thor", "Hulk", "Homem de Ferro"));

        jogadorService.cadastrarJogador(dto);

        ArgumentCaptor<Jogador> jogadorCaptor =
                ArgumentCaptor.forClass(Jogador.class);

        Mockito.verify(jogadorRepository, Mockito.times(1))
                .save(jogadorCaptor.capture());

        Jogador jogadorSalvo = jogadorCaptor.getValue();

        assertEquals("Hulk", jogadorSalvo.getCodinome());
    }

    // Teste 6: Verifica se todos os codinomes foram ocupados
    @Test
    void deveRejeitarCadastroQuandoTodosCodinomesEstiveremOcupados() {
        CadastroRequestDTO dto = new CadastroRequestDTO();

        dto.setNome("Marcos");
        dto.setEmail("marcos@teste.com");
        dto.setListaReferencia("vingadores");

        JogadorRepository jogadorRepository = Mockito.mock(JogadorRepository.class);
        VingadoresService vingadoresService = Mockito.mock(VingadoresService.class);
        LigaDaJusticaService ligaDaJusticaService = Mockito.mock(LigaDaJusticaService.class);

        JogadorService jogadorService = new JogadorService(
                vingadoresService,
                ligaDaJusticaService,
                jogadorRepository
        );

        Mockito.when(vingadoresService.buscarCodinomesDisponiveis())
                .thenReturn(List.of("Thor", "Hulk"));

        Jogador jogadorExistente1 = new Jogador();

        jogadorExistente1.setNome("Pedro");
        jogadorExistente1.setEmail("pedro@teste.com");
        jogadorExistente1.setCodinome("Thor");

        Jogador jogadorExistente2 = new Jogador();

        jogadorExistente2.setNome("João");
        jogadorExistente2.setEmail("joao@teste.com");
        jogadorExistente2.setCodinome("Hulk");

        Mockito.when(jogadorRepository.findAll())
                .thenReturn(List.of(jogadorExistente1, jogadorExistente2));

        assertThrows(IllegalArgumentException.class, () -> {
            jogadorService.cadastrarJogador(dto);

            Mockito.verify(jogadorRepository, Mockito.never())    // Verificar que nenhum jogador foi salvo
                    .save(Mockito.any(Jogador.class));
        });
    }
}
