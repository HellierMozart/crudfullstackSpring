## Como usar cada branch

Veja abaixo instruções básicas para rodar e testar cada etapa do projeto:

### 1. main (CRUD com MongoDB Atlas)
```bash
git checkout main
cd backend
# Crie o arquivo .env com a URI do MongoDB
# Exemplo:
echo SPRING_DATA_MONGODB_URI=... > .env
mvn spring-boot:run
```

### 2. alteracaoBancoDados (Banco relacional)
```bash
git checkout alteracaoBancoDados
cd backend
# Configure o .env com a URL do banco relacional
mvn spring-boot:run
```

### 3. ModoConsultaPesquisa (Consultas avançadas)
```bash
git checkout ModoConsultaPesquisa
cd backend
mvn spring-boot:run
# Teste os endpoints de pesquisa conforme README da branch
```

### 4. feature/auth-jwt (JWT e Roles)
```bash
git checkout feature/auth-jwt
cd backend
mvn spring-boot:run
# Veja no README desta branch como obter e usar o token JWT
```

### 5. feature/entidades-todas-novas (Entidades avançadas)
```bash
git checkout feature/entidades-todas-novas
cd backend
mvn spring-boot:run
```

### 6. feature/pessoa-entity-curso-entity (Pessoa e Curso separados)
```bash
git checkout feature/pessoa-entity-curso-entity
cd backend
mvn spring-boot:run
```

### 7. feature/roles-professor-aluno (Perfis Professor e Aluno)
```bash
git checkout feature/roles-professor-aluno
cd backend
mvn spring-boot:run
```

### 8. feature/microservicos (Microserviços)
```bash
git checkout feature/microservicos
# Veja o README desta branch para instruções de build, dockerização e orquestração
```
# Backend Spring Boot

## Roadmap de Implementação (Branches Reais)

O projeto evolui em etapas, cada uma em sua branch dedicada. Siga a ordem lógica para aprendizado:

### 1. CRUD com MongoDB Atlas (**main**)
- Projeto inicial com CRUD completo usando Spring Boot e MongoDB Atlas.
- Arquivo `.env` para variáveis sensíveis (URI do banco).

### 2. Troca de Banco de Dados (**alteracaoBancoDados**)
- Alteração do backend para usar banco relacional (ex: PostgreSQL ou MySQL) ao invés do MongoDB.
- Migração de entidades e repositórios.

### 3. Pesquisas e Consultas Avançadas (**ModoConsultaPesquisa**)
- Endpoints para buscas filtradas, paginação, ordenação e consultas customizadas.
- Exemplos: busca por nome, faixa de datas, etc.

### 4. Autenticação JWT e Controle de Acesso (**feature/auth-jwt**)
- Autenticação baseada em JWT (JSON Web Token).
- Controle de acesso por roles (perfis de usuário) usando anotações como `@PreAuthorize`.

### 5. Entidades Avançadas (**feature/entidades-todas-novas**)
- Criação e ajustes de todas as entidades do domínio.

### 6. Pessoa e Curso Separados (**feature/pessoa-entity-curso-entity**)
- Separação das entidades Pessoa e Curso para melhor organização.

### 7. Perfis de Usuário: Professor e Aluno (**feature/roles-professor-aluno**)
- Implementação de roles específicas para Professor e Aluno.

### 8. Microserviços (**feature/microservicos**)
- Refatoração do sistema para arquitetura de microserviços.
- Cada domínio (pessoa, curso, matrícula, etc.) em um serviço independente.
- Comunicação HTTP entre serviços, fallback, gateway manual, dockerização e orquestração.

---

> Para cada etapa, troque para a branch correspondente com `git checkout nome-da-branch`.
> Sempre consulte o README de cada branch para instruções específicas.