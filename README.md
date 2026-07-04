# Sistema de Cadastro de Jogadores – Java Spring Boot

Este projeto consiste no desenvolvimento de uma aplicação Java utilizando **Spring Boot**, capaz de consumir informações de arquivos externos nos formatos **JSON** e **XML**, persistir dados em um banco de dados em memória (**HSQLDB**) e disponibilizar uma interface simples para cadastro e listagem de jogadores.

No momento do cadastro, o usuário escolhe uma lista de referência, e o sistema consulta em tempo real um arquivo externo (hospedado no GitHub) para atribuir automaticamente o **primeiro codinome disponível** (ainda não utilizado por outro jogador):

- **Vingadores** — consumido via JSON
- **Liga da Justiça** — consumido via XML

---

## 📑 Sumário

- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Funcionalidades](#️-funcionalidades)
- [Pré-requisitos](#-pré-requisitos)
- [Como Rodar o Projeto](#️-como-rodar-o-projeto)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Modelo de Dados](#-modelo-de-dados)
- [Fontes Externas de Codinomes](#-fontes-externas-de-codinomes)
- [Regra de Atribuição de Codinome](#-regra-de-atribuição-de-codinome)
- [Endpoints da API](#-endpoints-da-api)
- [Interface Web](#-interface-web)
- [Autor](#-autor)

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5
- Spring Data JPA
- Spring Web (RestTemplate)
- Spring Validation
- Thymeleaf
- Maven
- HSQLDB (banco de dados em memória)
- Jackson (Dataformat XML) + Jackson Databind (JSON)
- Gson
- Lombok

---

## ⚙️ Funcionalidades

- Cadastro de jogadores via API REST ou via formulário web
- Escolha de lista de referência (Vingadores ou Liga da Justiça) no momento do cadastro
- Consumo em tempo real de arquivos externos (JSON e XML) hospedados no GitHub
- Atribuição automática do primeiro codinome disponível na lista escolhida
- Persistência em banco de dados em memória (HSQLDB)
- Listagem de jogadores cadastrados (via API e via página web)
- Validação de dados de entrada (nome obrigatório, e-mail obrigatório e válido)
- Tratamento de erros de negócio (lista inválida, codinomes esgotados, falha ao consumir fonte externa, etc.)

---

## ✅ Pré-requisitos

Antes de rodar o projeto, você precisa ter instalado:

- [JDK 17](https://adoptium.net/) ou superior
- Maven 3.8+ (opcional, o projeto já inclui o wrapper `mvnw`)
- Conexão com a internet (necessária para consumir as listas de codinomes externas)
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

   Ou execute diretamente a classe principal da aplicação pela sua IDE.

3. Acesse a aplicação em:
http://localhost:8081

---

## 📁 Estrutura do Projeto

```
sistema-cadastro-jogadores-uol/
├── src/
│   ├── main/
│   │   ├── java/com/gustavoigor/cadastro_jogadores_uol/
│   │   │   ├── controller/                # JogadorController (REST) e JogadorWebController (páginas HTML)
│   │   │   ├── dto/                       # CadastroRequestDTO, VingadorDTO, VingadoresResponseDTO, LigaDaJusticaResponseDTO
│   │   │   ├── model/                     # Entidade Jogador (JPA)
│   │   │   ├── repository/                # JogadorRepository
│   │   │   ├── service/                   # JogadorService (regras de negócio)
│   │   │   │   └── contentExternal/       # VingadoresService e LigaDaJusticaService (consomem as fontes externas)
│   │   │   └── exception/                 # Tratamento global de exceções
│   │   └── resources/
│   │       ├── templates/                 # Páginas HTML (Thymeleaf): cadastro.html, lista.html
│   │       └── application.properties
│   └── test/                              # Testes automatizados
├── pom.xml
└── README.md
```

---

## 🗂️ Modelo de Dados

A entidade `Jogador` possui os seguintes campos:


```

| Campo             | Tipo   | Preenchido por            | Descrição                                          |
|--------------------|--------|-----------------------------|------------------------------------------------------|
| `id`               | Long   | Sistema (auto-incremento)  | Identificador único do jogador                       |
| `nome`             | String | Usuário                    | Nome do jogador                                      |
| `email`            | String | Usuário                    | E-mail do jogador                                    |
| `telefone`         | String | Usuário (opcional)         | Telefone do jogador                                  |
| `codinome`         | String | Sistema (automático)       | Codinome atribuído no momento do cadastro            |
| `listaReferencia`  | String | Sistema (automático)       | Lista de origem do codinome (`vingadores` ou `liga_da_justica`) |

**Corpo da requisição de cadastro (`CadastroRequestDTO`):**

| Campo             | Tipo   | Obrigatório | Descrição                                             |
|--------------------|--------|--------------|----------------------------------------------------------|
| `nome`             | String | Sim          | Nome do jogador                                          |
| `email`            | String | Sim          | E-mail válido do jogador                                 |
| `telefone`         | String | Não          | Telefone do jogador                                      |
| `listaReferencia`  | String | Sim          | Lista escolhida: deve conter `"vingador"` ou `"liga"`    |

```

---

## 🌐 Fontes Externas de Codinomes

As listas de codinomes não ficam armazenadas no projeto — elas são consultadas em tempo real, a cada cadastro, diretamente do repositório oficial de referência da UOL no GitHub:

| Lista            | Formato | Origem                                                                                                     |
|-------------------|---------|--------------------------------------------------------------------------------------------------------------|
| Vingadores        | JSON    | `raw.githubusercontent.com/uolhost/test-backEnd-Java/.../vingadores.json`     |
| Liga da Justiça   | XML     | `raw.githubusercontent.com/uolhost/test-backEnd-Java/.../liga_da_justica.xml` |

- `VingadoresService` faz a requisição via `RestTemplate`, converte o JSON usando `ObjectMapper` (Jackson) e extrai a lista de codinomes.
- `LigaDaJusticaService` faz o mesmo processo, mas convertendo o XML usando `XmlMapper` (Jackson Dataformat XML).

> ⚠️ Como a aplicação depende dessas URLs externas, é necessário ter conexão com a internet para realizar cadastros. Caso a fonte externa esteja indisponível, o cadastro falha com uma exceção (`IllegalStateException`).

---

## 🎯 Regra de Atribuição de Codinome

1. O usuário informa no cadastro qual lista deseja usar, através do campo `listaReferencia` (ex: `"vingadores"` ou `"liga"`).
2. O sistema busca a lista de codinomes correspondente na fonte externa:
   - Contém `"vingador"` → busca em `VingadoresService`
   - Contém `"liga"` → busca em `LigaDaJusticaService`
3. O sistema compara essa lista com os codinomes já usados no banco de dados.
4. É atribuído ao jogador o **primeiro codinome ainda disponível** na lista escolhida.
5. Caso a lista informada seja inválida, os codinomes da lista já estejam todos em uso, ou a fonte externa esteja indisponível, o cadastro é rejeitado com uma mensagem de erro.

---

## 🔌 Endpoints da API

| Método | Endpoint     | Descrição                          |
|--------|--------------|-------------------------------------|
| GET    | `/jogadores` | Lista todos os jogadores cadastrados (JSON) |
| POST   | `/jogadores` | Cadastra um novo jogador (JSON)     |

**Corpo da requisição:**
```json
{
  "nome": "João da Silva",
  "email": "joao@email.com",
  "telefone": "11999999999",
  "listaReferencia": "vingadores"
}
```

Exemplo de requisição com `curl`:
```bash
curl -X POST http://localhost:8081/jogadores \
  -H "Content-Type: application/json" \
  -d '{"nome": "João da Silva", "email": "joao@email.com", "telefone": "11999999999", "listaReferencia": "vingadores"}'
```

---

## 🖥️ Interface Web

Além da API REST, o projeto disponibiliza páginas HTML (via Thymeleaf) para uso direto no navegador:

```

| Método | Endpoint      | Descrição                                  |
|--------|---------------|----------------------------------------------|
| GET    | `/`           | Exibe o formulário de cadastro de jogador    |
| POST   | `/cadastrar`  | Processa o cadastro enviado pelo formulário e redireciona para `/lista` |
| GET    | `/lista`      | Exibe a lista de jogadores cadastrados       |

```

---

## 👤 Autor

Desenvolvido por **Gustavo Igor**

[![GitHub](https://img.shields.io/badge/GitHub-gu1gor-181717?style=flat&logo=github)](https://github.com/gu1gor)
