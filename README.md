# Sistema de Cadastro de Jogadores – Java Spring Boot

Este projeto consiste no desenvolvimento de uma aplicação Java utilizando **Spring Boot**, capaz de consumir informações de arquivos externos nos formatos **JSON** e **XML**, persistir dados em um banco de dados em memória (**HSQLDB**) e disponibilizar uma interface simples para cadastro e listagem de jogadores.

Os jogadores recebem automaticamente codinomes pertencentes a duas listas distintas:

- **Os Vingadores**
- **A Liga da Justiça**

---

## 📑 Sumário

- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Funcionalidades](#️-funcionalidades)
- [Pré-requisitos](#-pré-requisitos)
- [Como Rodar o Projeto](#️-como-rodar-o-projeto)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Formato dos Arquivos de Importação](#-formato-dos-arquivos-de-importação)
- [Endpoints da API](#-endpoints-da-api)
- [Licença](#-licença)
- [Autor](#-autor)

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5
- Spring Data JPA
- Spring Web
- Spring Validation
- Thymeleaf
- Maven
- HSQLDB (banco de dados em memória)
- Jackson (Dataformat XML)
- Gson
- Lombok

---

## ⚙️ Funcionalidades

- Cadastro de jogadores
- Escolha automática de codinomes (Vingadores / Liga da Justiça)
- Consumo de dados externos (JSON e XML)
- Persistência em banco de dados em memória
- Listagem de jogadores cadastrados
- Tratamento global de exceções com `@ControllerAdvice`

---

## ✅ Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- [JDK 17](https://adoptium.net/) ou superior
- Maven 3.8+ (opcional, o projeto já inclui o wrapper `mvnw`)
- Uma IDE de sua preferência (IntelliJ, Eclipse, VS Code, etc.)

---

## ▶️ Como Rodar o Projeto

1. Clone o repositório:
```bash
   git clone https://github.com/gu1gor/sistema-cadastro-jogadores-uol.git
   cd sistema-cadastro-jogadores-uol
```

2. Rode o projeto usando o Maven Wrapper:
```bash
   # Linux/Mac
   ./mvnw spring-boot:run

   # Windows
   mvnw.cmd spring-boot:run
```

   Ou execute diretamente a classe `CadastroJogadoresUolApplication` pela sua IDE.

3. Acesse a aplicação em:
http://localhost:8081

---

## 📁 Estrutura do Projeto
```
sistema-cadastro-jogadores-uol/
├── src/
│   ├── main/
│   │   ├── java/com/gustavoigor/cadastrojogadoresuol/
│   │   │   ├── controller/     # Controllers REST e MVC
│   │   │   ├── model/          # Entidades JPA
│   │   │   ├── repository/     # Interfaces de acesso a dados
│   │   │   ├── service/        # Regras de negócio
│   │   │   └── exception/      # Tratamento global de exceções
│   │   └── resources/
│   │       ├── templates/      # Páginas HTML (Thymeleaf)
│   │       └── application.properties
│   └── test/                   # Testes automatizados
├── pom.xml
└── README.md
```

## 📄 Formato dos Arquivos de Importação

O sistema aceita a importação de jogadores nos formatos **JSON** e **XML**. Exemplos de estrutura esperada:

**JSON**
```json
{
  "nome": "João da Silva",
  "posicao": "Atacante",
  "idade": 24
}
```

**XML**
```xml
<jogador>
  <nome>João da Silva</nome>
  <posicao>Atacante</posicao>
  <idade>24</idade>
</jogador>
```

> Atualize os campos acima para refletir exatamente o modelo (`model`) utilizado no projeto.

---

## 🔌 Endpoints da API

| Método | Endpoint          | Descrição                          |
|--------|-------------------|-------------------------------------|
| GET    | `/jogadores`       | Lista todos os jogadores cadastrados |
| POST   | `/jogadores`       | Cadastra um novo jogador             |

Exemplo de requisição com `curl`:
```bash
curl -X POST http://localhost:8081/jogadores \
  -H "Content-Type: application/json" \
  -d '{"nome": "João da Silva", "posicao": "Atacante", "idade": 24}'
```

> Revise essa tabela conforme os endpoints reais implementados nos seus Controllers.

---

## 📜 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👤 Autor

Desenvolvido por **Gustavo Igor**

[![GitHub](https://img.shields.io/badge/GitHub-gu1gor-181717?style=flat&logo=github)](https://github.com/gu1gor)
