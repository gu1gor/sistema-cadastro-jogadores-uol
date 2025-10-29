package com.gustavoigor.cadastro_jogadores_uol.repository;

import com.gustavoigor.cadastro_jogadores_uol.model.Codinome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodinomeRepository extends JpaRepository<Codinome, String> {

    //retorna codinome disponivel
    Optional<Codinome> findFirstByDisponivelTrue();
}
