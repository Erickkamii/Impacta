# Impacta

Impacta é uma plataforma para conectar ONGs e voluntários, facilitando o cadastro, autenticação, gerenciamento de eventos, habilidades e doações. O sistema oferece dashboards distintos para ONGs e voluntários, promovendo o engajamento social de forma simples e eficiente.

---

## Visão Geral

- **ONGs**: cadastram eventos, visualizam voluntários e suas habilidades, recebem doações.
- **Voluntários**: encontram ONGs, se inscrevem em eventos, cadastram habilidades e acompanham suas participações.
- **Segurança**: autenticação JWT, controle de acesso por perfil e dados sensíveis protegidos.

---

## Tecnologias

- **Backend:** Java 17+, Spring Boot, Spring Security, JPA/Hibernate, Lombok, PostgreSQL, Flyway
- **Frontend:** Angular 16+, Angular Material, TypeScript, RxJS, ngx-toastr

---

## Endpoints Principais

### Autenticação
- `POST /auth/register` — Cadastro de ONG ou voluntário
- `POST /auth/login` — Login e geração de token JWT

### Usuário
- `GET /user` — Endpoint de teste/autorização

### ONGs
- `GET /ong` — Lista todas as ONGs (nome e id)

### Voluntários
- `GET /volunteers` — Lista todos os voluntários
- `GET /volunteers/{id}` — Detalhes de um voluntário

### Eventos
- `POST /events` — Criação de evento (ONG)
- `GET /events` — Lista todos os eventos
- `POST /events/{eventId}/register` — Inscrição de voluntário em evento

### Doações
- `POST /donations` — Realiza uma doação
- `GET /donations` — Lista todas as doações
- `GET /donations/by-volunteer?volunteerName=...` — Lista doações por voluntário

### Habilidades
- `POST /skills` — Cria nova habilidade
- `GET /skills` — Lista habilidades
- `GET /skills/{id}` — Detalha habilidade
- `PUT /skills/{id}` — Atualiza habilidade
- `DELETE /skills/{id}` — Remove habilidade
- `POST /skills/assign` — Associa habilidade a voluntário

### Habilidades do Voluntário
- `POST /volunteer-skill` — Cria relação voluntário-habilidade
- `GET /volunteer-skill` — Lista relações voluntário-habilidade

---

## Funcionalidades do Frontend

- **Login e cadastro** para ONGs e voluntários, com validação e feedback visual.
- **Dashboard da ONG:** visualização de voluntários, busca por habilidades, gerenciamento de eventos e doações.
- **Dashboard do Voluntário:** visualização de ONGs, inscrição em eventos, cadastro de habilidades e acompanhamento de histórico.
- **Proteção de rotas** e navegação baseada no perfil do usuário.
- **Notificações** e feedback visual com Angular Material e Toastr.

---

## Como Executar

### Backend

1. Configure o banco em `impacta/src/main/resources/application.properties`.
2. Execute:
   ```sh
   cd impacta
   ./mvnw spring-boot:run
   ```
3. API disponível em `http://localhost:8080/`.

### Frontend

1. Instale as dependências:
   ```sh
   cd impacta-front
   npm install
   ```
2. Execute:
   ```sh
   ng serve
   ```
3. Acesse `http://localhost:4200/`.

---

