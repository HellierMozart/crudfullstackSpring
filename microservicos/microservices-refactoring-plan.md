# Microservices Refactoring Plan

## Objetivo
Criar microserviços equivalentes ao exemplo `microservicos/matricula-service` para as demais classes de domínio do repositório e garantir comunicação e portas distintas entre serviços.

## Escopo
- `pessoa-service` (porta 8082)
- `curso-service` (porta 8083)
- `disciplina-service` (porta 8084)
- `professor-service` (porta 8085)
- `turma-service` (porta 8086)

> Basear cada novo serviço no padrão do `microservicos/matricula-service`.

## Passos de implementação

1. **Criar a estrutura do módulo para cada serviço**
   - Copiar a pasta `microservicos/matricula-service/` para `microservicos/pessoa-service/`, `microservicos/curso-service/`, `microservicos/disciplina-service/`, `microservicos/professor-service/` e `microservicos/turma-service/`.
   - Remover arquivos de `target/` das cópias se estiverem presentes.

2. **Ajustar o `pom.xml` de cada serviço**
   - Atualizar `<artifactId>` para o nome do serviço correspondente.
   - Atualizar `<name>` e `<description>`.
   - Manter dependências Spring Boot, JPA, H2 e Spring Boot Maven Plugin.

3. **Configurar `application.properties` de cada serviço**
   - Definir porta exclusiva:
     - `pessoa-service` → `server.port=8082`
     - `curso-service` → `server.port=8083`
     - `disciplina-service` → `server.port=8084`
     - `professor-service` → `server.port=8085`
     - `turma-service` → `server.port=8086`
   - Definir `spring.application.name=<nome>-service`.
   - Definir banco em memória H2 com nome próprio:
     - `spring.datasource.url=jdbc:h2:mem:pessoadb`
     - `spring.datasource.url=jdbc:h2:mem:cursodb`
     - `spring.datasource.url=jdbc:h2:mem:disciplinadb`
     - `spring.datasource.url=jdbc:h2:mem:professordb`
     - `spring.datasource.url=jdbc:h2:mem:turmadb`

4. **Criar o código de cada serviço**
   - Copiar a arquitetura de pastas do `matricula-service`:
     - `src/main/java/.../config/`
     - `src/main/java/.../controller/`
     - `src/main/java/.../Model/`
     - `src/main/java/.../repository/`
     - `src/main/java/.../service/`
   - Implementar os mesmos componentes:
     - `Entity` no pacote `Model`
     - `Repository extends JpaRepository<Entidade, Long>`
     - `Service` com lógica de CRUD e injeção via construtor
     - `Controller` com `@RestController`, `@RequestMapping("/api/<plural>")` e endpoints REST
     - `DataLoader` em `config/` para inicialização de dados de teste
     - Classe principal `@SpringBootApplication`

5. **Mapear entidades do monolito para microserviços**
   - Usar as entidades e regras do backend monolítico como fonte de verdade.
   - Cada microserviço deve expor seu conjunto de rotas REST independentes.

6. **Garantir comunicação entre microserviços**
   - Se um serviço precisar de dados de outro serviço, usar chamada HTTP REST externa.
   - Não compartilhar código Java entre microserviços.
   - Exemplo de configuração de URL externa no `application.properties` (para chamadas HTTP):
     - `pessoa.service.url=http://localhost:8082`
     - `curso.service.url=http://localhost:8083`
   - Em um ambiente Docker Compose, use o nome do serviço como host:
     - `http://pessoa-service:8082`
     - `http://curso-service:8083`

7. **Atualizar `docker-compose.yml` da raiz**
   - Adicionar os novos serviços como containers separados:
     - `pessoa-service`, `curso-service`, `disciplina-service`, `professor-service`, `turma-service`
   - Mapear portas externas distintas para cada serviço.
   - Usar uma rede comum para permitir resolução por nome de serviço.

8. **Validação local**
   - Buildar cada serviço:
     - `mvn -f microservicos/pessoa-service/pom.xml clean package`
   - Rodar cada serviço e verificar endpoints principais.
   - Testar chamadas REST entre serviços usando URLs de configuração.

## Dependências de porta e comunicação

- `8081` — `matricula-service`
- `8082` — `pessoa-service`
- `8083` — `curso-service`
- `8084` — `disciplina-service`
- `8085` — `professor-service`
- `8086` — `turma-service`

### Configuração recomendada para comunicação inter-serviços

No `application.properties` de serviços que dependem de outro serviço:
```properties
pessoa.service.url=http://localhost:8082
curso.service.url=http://localhost:8083
professor.service.url=http://localhost:8085
```

No Docker Compose, usar o host de serviço:
```properties
pessoa.service.url=http://pessoa-service:8082
curso.service.url=http://curso-service:8083
```

## Commit messages sugeridos

1. `feat(microservices): add pessoa-service module based on matricula-service example`
2. `feat(microservices): add curso-service module with independent H2 database`
3. `feat(microservices): add disciplina-service module and CRUD endpoints`
4. `feat(microservices): add professor-service module and data loader`
5. `feat(microservices): add turma-service module with own API port`
6. `chore(microservices): set distinct ports for new microservices`
7. `refactor(microservices): centralize inter-service REST URL configuration`
8. `chore(docker): add new microservices to root docker-compose.yml`

## Branch strategy

- `feature/pessoa-service`
- `feature/curso-service`
- `feature/disciplina-service`
- `feature/professor-service`
- `feature/turma-service`

> Cada branch deve conter a implementação completa do serviço e a configuração de porta/URL correspondente.

## Resultado esperado

Ao final do trabalho, o projeto deve ter:
- Cinco novos microserviços no diretório `microservicos/`
- Cada serviço com sua própria `server.port` e banco H2 em memória
- Endpoints REST independentes para cada entidade
- Comunicação entre serviços feita por HTTP, sem dependências Java diretas
- Docker Compose atualizado para orquestrar todos os serviços
