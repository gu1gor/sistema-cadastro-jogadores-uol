package com.gustavoigor.cadastro_jogadores_uol.dto;

import lombok.Data;

@Data
public class CadastroRequestDTO {
    private String nome;
    private String email;
    private String telefone;
    private String listaReferencia;
}
