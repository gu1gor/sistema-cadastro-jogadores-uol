package com.gustavoigor.cadastro_jogadores_uol.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    @Column(unique = true)
    private String codinome;

    private String listaReferencia;    //vingadores.json" ou "liga_da_justica.xml
}
