package com.gustavoigor.cadastro_jogadores_uol.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class Jogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String nome;
    @NonNull
    private String email;
    private String telefone;

    /* Atributos controlados pelo sistema
    preenchidos quando o cadastro for feito */
    private String codinome;

    private String listaReferencia;    //vingadores.json" ou "liga_da_justica.xml
}
