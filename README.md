# Impacta

**Impacta** é uma plataforma que conecta ONGs e voluntários, facilitando o engajamento social, o gerenciamento de eventos e a colaboração entre pessoas e organizações que querem transformar o mundo.

---

## 🚀 Visão Geral

- **ONGs** podem cadastrar eventos, buscar voluntários e gerenciar suas ações sociais.
- **Voluntários** podem encontrar ONGs, se inscrever em eventos e acompanhar seu impacto.
- Plataforma segura, com autenticação JWT e perfis separados para cada tipo de usuário.

---

## 🛠️ Tecnologias

- **Backend:** Java 17+, Spring Boot, Spring Security, JPA/Hibernate, Lombok, PostgreSQL, Flyway
- **Frontend:** Angular 16+, Angular Material, TypeScript, RxJS, ngx-toastr

---

## 📦 Estrutura do Projeto

```
impacta/         # Backend (Java Spring Boot)
impacta-front/   # Frontend (Angular)
```

---

## 🔗 Endpoints da API

### Autenticação
- `POST /auth/register` — Cadastro de ONG ou voluntário
- `POST /auth/login` — Login e geração de token JWT

### Usuários
- `GET /users` — Lista todos os usuários
- `GET /users/{id}` — Busca usuário por ID
- `PUT /users/{id}` — Atualiza dados do usuário
- `DELETE /users/{id}` — Remove usuário

### ONGs
- `GET /ongs` — Lista todas as ONGs
- `GET /ongs/{id}` — Detalhes de uma ONG
- `POST /ongs` — Cria uma nova ONG
- `PUT /ongs/{id}` — Atualiza dados da ONG
- `DELETE /ongs/{id}` — Remove ONG

### Voluntários
- `GET /volunteers` — Lista todos os voluntários
- `GET /volunteers/{id}` — Detalhes de um voluntário
- `POST /volunteers` — Cria um novo voluntário
- `PUT /volunteers/{id}` — Atualiza dados do voluntário
- `DELETE /volunteers/{id}` — Remove voluntário

### Eventos
- `GET /events` — Lista todos os eventos
- `GET /events/{id}` — Detalhes de um evento
- `POST /events` — Cria um novo evento (ONG)
- `PUT /events/{id}` — Atualiza evento
- `DELETE /events/{id}` — Remove evento

### Habilidades
- `GET /skills` — Lista habilidades disponíveis
- `POST /skills` — Cria nova habilidade

---

## 💻 Frontend

- **Login e cadastro** para ONGs e voluntários
- **Dashboard de ONG:** Visualiza voluntários cadastrados, gerencia eventos, busca voluntários por habilidade
- **Dashboard de Voluntário:** Visualiza ONGs, se inscreve em eventos, acompanha histórico de participação
- **Feedback visual** com Angular Material e notificações

---

## ⚡ Como rodar

### Backend

1. Configure o banco em `impacta/src/main/resources/application.properties`
2. Rode:
   ```sh
   cd impacta
   ./mvnw spring-boot:run
   ```
3. API disponível em `http://localhost:9090/`

### Frontend

1. Instale dependências:
   ```sh
   cd impacta-front
   npm install
   ```
2. Rode:
   ```sh
   ng serve
   ```
3. Acesse `http://localhost:4200/`

---
