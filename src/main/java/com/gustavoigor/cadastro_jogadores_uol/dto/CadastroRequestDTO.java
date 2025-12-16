package com.gustavoigor.cadastro_jogadores_uol.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CadastroRequestDTO {
    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    private String telefone;
    private String listaReferencia;
}
