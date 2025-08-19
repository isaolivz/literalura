# Literalura

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.3-green?logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-42.2.27-blue?logo=postgresql)

## 📖 Descrição do Projeto

Literalura é uma aplicação de linha de comando desenvolvida em Java com Spring Boot, projetada para catalogar livros e autores de forma eficiente. O programa consome dados da API **Gutendex**, permitindo que o usuário busque e registre livros em um banco de dados local.

## ✨ Funcionalidades

O projeto oferece um menu interativo com as seguintes opções:

* **Buscar livro por título**: Realiza uma busca na API Gutendex e salva o livro no banco de dados. Se o livro já existir, ele é exibido sem ser duplicado.
* **Listar livros registrados**: Exibe todos os livros que foram salvos no banco de dados.
* **Listar autores vivos em um determinado ano**: Filtra e exibe autores que estavam vivos no ano especificado.
* **Listar livros por idioma**: Filtra e exibe os livros com base no idioma selecionado.
* **Listar autores registrados**: Mostra a lista de todos os autores salvos no banco de dados.

## 💻 Tecnologias

* **Java 17**: Linguagem de programação.
* **Spring Boot**: Framework para simplificar o desenvolvimento.
* **Spring Data JPA**: Para persistência e gerenciamento do banco de dados.
* **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.
* **Maven**: Gerenciador de dependências.
* **Jackson**: Biblioteca para processamento de JSON.

## ⚙️ Como Executar

### Pré-requisitos

* JDK 17 ou superior
* Maven
* PostgreSQL instalado e rodando

### Configuração

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/literalura.git](https://github.com/seu-usuario/literalura.git)
    cd literalura
    ```
2.  **Crie o banco de dados:**
    No seu PostgreSQL, crie um banco de dados com o nome `literalura`.
    ```sql
    CREATE DATABASE literalura;
    ```
3.  **Configure as credenciais:**
    Abra o arquivo `src/main/resources/application.properties` e atualize as informações de usuário e senha do seu banco de dados.

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

### Execução

No terminal, na raiz do projeto, execute o seguinte comando:

```bash
mvn spring-boot:run
