package com.gustavoigor.cadastro_jogadores_uol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CadastroJogadoresUolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroJogadoresUolApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
