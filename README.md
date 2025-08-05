
# 🚗 LocadoraSpring - API REST para Locadora de Veículos

![Java](https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-6DB33F?style=for-the-badge&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Secure-000000?style=for-the-badge&logo=JSON%20web%20tokens)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven&logoColor=white)
![In Progress](https://img.shields.io/badge/Status-Em%20desenvolvimento-yellow?style=for-the-badge)

---

## 📖 Descrição

**LocadoraSpring** é uma API REST desenvolvida em Java com Spring Boot que simula o funcionamento de uma locadora de veículos.  
Ela possui três entidades principais — **Cliente**, **Veículo** e **Aluguel** — e oferece funcionalidades completas de CRUD, regras de negócio personalizadas, autenticação e autorização com **JWT**.

É um projeto voltado para **aprendizado prático** e **portfólio**, com foco em boas práticas, arquitetura em camadas e uso eficiente do ecossistema Spring.

---

## ✅ Funcionalidades

- 🔐 Autenticação e autorização com JWT (stateless)
- 🧑‍💼 Cadastro e gerenciamento de clientes
- 🚗 Cadastro e gerenciamento de veículos
- 📄 Controle completo de aluguéis:
  - Registrar aluguel e devolução
  - Buscar aluguel por placa
  - Buscar veículos por tipo e quantidade
  - Calcular faturamento total por tipo de veículo
  - Calcular quantidade total de diárias
  - Reajustar valor da diária
  - Depreciação de veículos
- 📜 Validação de dados com Bean Validation
- 🗂 Arquitetura em camadas: Controller, Service, Repository
- 🔧 Injeção de dependência com Spring
- 🔐 Perfis de acesso: `ADMIN` e `USER`
- 📂 Migrations de banco de dados com Flyway
- 📦 Organização limpa e modular do projeto

---

## 🛠 Tecnologias e Ferramentas

- Java 17
- Spring Boot 3.5.3
  - Spring Web
  - Spring Data JPA
  - Spring Security
- MySQL
- JWT
- Maven
- Flyway
- Lombok
- Postman (para testes manuais)

---

## 🚀 Como rodar o projeto localmente

### Pré-requisitos

- Java 17 instalado
- MySQL rodando localmente
- Maven instalado (ou usar `./mvnw`)
- IDE (IntelliJ, VSCode, Eclipse...)

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/VitorFichel/LocadoraSpring.git

# 2. Acesse o diretório
cd LocadoraSpring
```

### Banco de dados

Crie o banco no MySQL:

```sql
CREATE DATABASE locadoraSpring;
```

Edite o arquivo `application.properties` se necessário:

```properties
spring.application.name=locadoraString
spring.datasource.url=jdbc:mysql://localhost:3306/locadoraSpring?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.flyway.baseline-on-migrate=true
spring.flyway.baseline-version=1
logging.level.org.springframework.security=DEBUG
api.security.token.secret=${JWT_SECRET:my-secret-key}
```

### Executando o projeto

```bash
# Execute pela linha de comando
./mvnw spring-boot:run
```

Ou use a IDE para rodar a classe principal `LocadoraSpringApplication`.

---

## 🔐 Autenticação e Segurança

A autenticação é baseada em **JWT (JSON Web Token)** e o sistema possui perfis de acesso (`ADMIN`, `USER`).

### Fluxo

1. **Cadastrar usuário**
   Endpoint: `POST /auth/register`

2. **Login**
   Endpoint: `POST /auth/login`

3. **Receba um token JWT válido**
   O token será usado nas próximas requisições.

4. **Autentique as requisições**
   Adicione o header:

```
Authorization: Bearer <token>
```

> **Exemplo de login:**

```json
{
  "login": "admin",
  "senha": "123456"
}
```

---

## 📂 Estrutura de Pastas

```bash
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── locadora/
│   │           ├── controller/
│   │           ├── dto/
│   │           ├── model/
│   │           ├── repository/
│   │           ├── security/
│   │           └── service/
│   └── resources/
│       ├── application.properties
│       └── db/
│           └── migration/   # Migrations com Flyway
```

---

## 📈 Planejamento Futuro

* [ ] Adicionar Swagger para documentação da API
* [ ] Criar testes unitários com JUnit
* [ ] Implementar testes de integração
* [ ] Realizar deploy na nuvem (Render, Railway, etc)
* [ ] Adicionar paginação e ordenação nas buscas
* [ ] Criar front-end para visualização dos dados

---

## 🧪 Testes

> 🔧 Ainda não implementados.
> Em breve o projeto contará com testes utilizando JUnit e Mockito.

---

## 📌 Status do Projeto

🚧 Em desenvolvimento
✔️ Funcional, com várias features completas
🔜 Em expansão, com melhorias previstas

---

## 👨‍💻 Autor

Feito com 💻 e ☕ por [Vitor Fichel](https://github.com/VitorFichel)

* GitHub: [@VitorFichel](https://github.com/VitorFichel)
* LinkedIn: [https://linkedin.com/in/vitorfichel](https://linkedin.com/in/vitorfichel)
* Email: [vitorfichel@email.com](vitorfichel@email.com)

---

## 📄 Licença

Este projeto está licenciado sob a **MIT License** – sinta-se livre para estudar, modificar e contribuir.
