package com.gustavoigor.cadastro_jogadores_uol.model;
/* Controla quais codi-nomes
estão disponiveis ou já foram usados */

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Codinome {

    @Id
    private String codinome;
}
