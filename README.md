# Sistema de Cadastro de Jogadores – Java Spring Boot

Este projeto consiste no desenvolvimento de uma aplicação Java utilizando **Spring Boot**, capaz de consumir informações de arquivos externos nos formatos **JSON** e **XML**, persistir dados em um banco de dados em memória (**HSQLDB**) e disponibilizar uma interface simples para cadastro e listagem de jogadores.

Os jogadores recebem automaticamente codinomes pertencentes a duas listas distintas:
- **Os Vingadores**
- **A Liga da Justiça**

---

## 🚀 Tecnologias Utilizadas

- Java
- Spring Boot
- Maven
- HSQLDB (banco de dados em memória)
- JPA / Hibernate
- HTML
- Lombok

---

## ⚙️ Funcionalidades

- Cadastro de jogadores
- Escolha automática de codinomes
- Consumo de dados externos (JSON e XML)
- Persistência em banco de dados em memória
- Listagem de jogadores cadastrados
- Tratamento global de exceções com `@ControllerAdvice`

---

## ▶️ Como Rodar o Projeto

1. Clone o repositório
    https://github.com/gu1gor/sistema-cadastro-jogadores-uol.git
2. Rode a classe `CadastroJogadoresUolApplication`
3. Acesse:  http://localhost:8081