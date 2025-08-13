# Basket API

API RESTful para gerenciamento de ligas de basquete, desenvolvida com Java e Spring Boot. A API permite o gerenciamento completo de ligas, times, jogadores, partidas e estatísticas.

## Visão Geral

Esta API foi projetada para ser uma solução completa para organizar e gerenciar campeonatos de basquete amador ou profissional. Ela oferece endpoints para registrar usuários, criar e gerenciar ligas, times e jogadores, agendar jogos, registrar estatísticas detalhadas por partida e jogador, e gerar rankings de classificação e de melhores jogadores.

## Funcionalidades Principais

-   **Autenticação e Autorização**: Sistema de autenticação baseado em JWT (JSON Web Token) para proteger os endpoints.
-   **Gerenciamento de Usuários**: Cadastro de usuários que podem gerenciar ligas.
-   **Gerenciamento de Ligas**:
    -   Criação, listagem e busca de ligas.
    -   Associação de times a uma liga.
-   **Gerenciamento de Times**:
    -   Criação e busca de times com informações detalhadas.
    -   Associação de jogadores a times em categorias específicas.
-   **Gerenciamento de Jogadores**: Cadastro de jogadores com dados pessoais e estatísticas.
-   **Gerenciamento de Categorias**: Criação de categorias para os times (ex: Sub-18 Masculino, Adulto Feminino).
-   **Agendamento de Jogos**:
    -   Criação de partidas entre times de uma liga.
    -   Atualização de status dos jogos (Agendado, Em Andamento, Finalizado, Cancelado).
-   **Estatísticas Detalhadas**:
    -   Registro de estatísticas por jogador ao final de cada partida (pontos, faltas, assistências, rebotes).
    -   Cálculo automático do placar final do jogo com base nas estatísticas dos jogadores.
-   **Rankings e Classificação**:
    -   Tabela de classificação dos times na liga (vitórias, derrotas, pontos, saldo).
    -   Ranking de cestinhas (maiores pontuadores) da liga.
    -   Ranking de jogadores com mais cestas de 3 pontos (paginado).

## Estrutura do Projeto

O projeto é organizado em módulos, seguindo as melhores práticas de arquitetura de software para garantir a separação de responsabilidades e a manutenibilidade.

com.basket.api
├── modules
│   ├── user
│   ├── category
│   ├── team
│   ├── player
│   ├── league
│   ├── leagueTeam
│   ├── teamCategory
│   ├── teamPlayer
│   ├── game
│   ├── gameEvent
│   └── stats
├── security
├── providers
├── exception
└── config

-   **`modules`**: Contém a lógica de negócio principal, separada por funcionalidades (módulos). Cada módulo possui suas próprias `entities`, `repositories`, `useCases` (serviços) e `controllers`.
-   **`security`**: Classes relacionadas à configuração de segurança e filtros de autenticação.
-   **`providers`**: Fornecedores de serviços, como o `JWTProvider` para manipulação de tokens.
-   **`exception`**: Classes de exceções customizadas e um `GlobalExceptionHandler` para tratamento de erros.
-   **`config`**: Configurações gerais da aplicação, como a configuração do OpenAPI (Swagger).

## Tecnologias Utilizadas

-   **Backend**:
    -   Java 21
    -   Spring Boot 3.3.2
    -   Spring Web
    -   Spring Data JPA
    -   Spring Security
    -   Hibernate
-   **Banco de Dados**:
    -   PostgreSQL
-   **Autenticação**:
    -   JSON Web Token (JWT)
-   **Documentação da API**:
    -   SpringDoc OpenAPI (Swagger UI)
-   **Build e Gerenciamento de Dependências**:
    -   Maven
-   **Utilitários**:
    -   Lombok

## Pré-requisitos

-   JDK 21 ou superior
-   Maven 3.x
-   Docker e Docker Compose (para o banco de dados)

## Como Executar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone <URL_DO_REPOSITORIO>
    cd java-api-basket
    ```

2.  **Inicie o banco de dados PostgreSQL com Docker:**
    Na raiz do projeto, execute o comando abaixo para iniciar um container Docker com o PostgreSQL.
    ```bash
    docker-compose up -d
    ```
    Isso irá criar um banco de dados chamado `basket_manager_db` na porta `5432`, com o usuário e senha `admin`.

3.  **Configure a aplicação:**
    O arquivo `src/main/resources/application.properties` já está pré-configurado para se conectar ao banco de dados Docker.
    ```properties
    spring.application.name=basket-api
    spring.datasource.url=jdbc:postgresql://localhost:5432/basket_manager_db
    spring.datasource.username=admin
    spring.datasource.password=admin
    spring.jpa.hibernate.ddl-auto=update

    security.token.secret="thiago"
    ```
    **Nota:** O `security.token.secret` é usado para assinar os tokens JWT. É altamente recomendável alterar este valor em um ambiente de produção e gerenciá-lo de forma segura (ex: variáveis de ambiente).

4.  **Execute a aplicação com Maven:**
    ```bash
    mvn spring-boot:run
    ```
    A API estará disponível em `http://localhost:8080`.

## Documentação da API (Swagger)

Com a aplicação em execução, você pode acessar a documentação interativa da API (Swagger UI) no seguinte endereço:

**http://localhost:8080/swagger-ui.html**

Lá você encontrará todos os endpoints disponíveis, seus parâmetros, schemas de resposta e poderá testá-los diretamente.

## Endpoints da API

Abaixo está uma lista dos principais endpoints agrupados por controlador.

<details>
<summary><strong>Autenticação e Usuários</strong></summary>

-   `POST /auth/sign-in`: Autentica um usuário e retorna um token JWT.
-   `POST /users`: Cria um novo usuário.

</details>

<details>
<summary><strong>Ligas e Times</strong></summary>

-   `POST /leagues`: Cria uma nova liga.
-   `GET /leagues`: Lista todas as ligas.
-   `GET /leagues/{id}`: Busca uma liga pelo seu ID.
-   `POST /leagues/{leagueId}/teams/{teamId}`: Adiciona um time a uma liga.
-   `GET /leagues/{leagueId}/teams`: Lista todos os times de uma liga.
-   `POST /teams`: Cria um novo time.
-   `GET /teams/{id}`: Busca um time pelo seu ID.

</details>

<details>
<summary><strong>Jogadores e Categorias</strong></summary>

-   `POST /players`: Cria um novo jogador.
-   `POST /categories`: Cria uma nova categoria.
-   `POST /teams/{teamId}/categories/add/{categoryId}`: Associa uma categoria a um time.
-   `GET /teams/{teamId}/categories`: Lista as categorias de um time.
-   `POST /teams/{teamId}/player/{playerId}/category/{categoryId}`: Adiciona um jogador a um time em uma categoria específica.
-   `GET /teams/{teamId}/players`: Lista todos os jogadores de um time.
-   `GET /teams/{teamId}/category/{categoryId}/players`: Lista os jogadores de um time em uma categoria específica.

</details>

<details>
<summary><strong>Jogos e Estatísticas</strong></summary>

-   `POST /games`: Agenda um novo jogo.
-   `GET /games/league/{leagueId}`: Lista todos os jogos de uma liga.
-   `POST /games/{gameId}/stats`: Registra as estatísticas de todos os jogadores de uma partida.
-   `GET /games/{gameId}/stats`: Busca as estatísticas completas de uma partida.
-   `GET /games/{gameId}/players/{playerId}/stats`: Busca as estatísticas de um jogador em uma partida.
-   `GET /leagues/{leagueId}/standings`: Retorna a tabela de classificação de uma liga.
-   `GET /leagues/{leagueId}/player-stats/top-scorers`: Retorna o ranking de cestinhas da liga.
-   `GET /leagues/{leagueId}/player-stats/three-point-leaders`: Retorna o ranking de cestinhas de 3 pontos.

</details>
