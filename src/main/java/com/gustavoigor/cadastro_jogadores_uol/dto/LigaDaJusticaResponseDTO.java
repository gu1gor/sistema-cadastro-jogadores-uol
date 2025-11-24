package com.gustavoigor.cadastro_jogadores_uol.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class LigaDaJusticaResponseDTO {
    @JacksonXmlElementWrapper(localName = "codinomes")
    @JacksonXmlProperty(localName = "codinome")
    private List<String> codinomes;
}
