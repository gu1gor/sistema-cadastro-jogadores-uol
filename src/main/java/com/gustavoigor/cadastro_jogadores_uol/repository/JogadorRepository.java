package com.gustavoigor.cadastro_jogadores_uol.repository;

import com.gustavoigor.cadastro_jogadores_uol.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long> {
}
