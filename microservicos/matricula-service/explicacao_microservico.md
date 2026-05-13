# ð§© MicroserviÃ§os com Spring Boot â Do Monolito ao Universo DistribuÃ­do

> ð¿ Branch: `feature/microservicos`
> ð¯ Entidade de exemplo: **MatrÃ­cula**
> ð Porta: **8081**

---

## ð  Era uma vez... um Monolito

Imagine uma casa onde **todos moram no mesmo cÃ´modo**: cozinha, quarto, escritÃ³rio e garagem, tudo junto. Funciona, mas quando a garagem pega fogo, a cozinha tambÃ©m sofre as consequÃªncias.

Ã assim que funciona uma aplicaÃ§Ã£o **monolÃ­tica**: um Ãºnico projeto, um Ãºnico banco, uma Ãºnica porta. Se uma parte trava, tudo pode travar junto.

```
[ crudmongo - porta 8080 ]
âââââââââââââââââââââââââââ
â  ð¤ Pessoa              â
â  ð Curso               â  â Tudo numa casa sÃ³
â  ð Disciplina          â
â  ð¨âð« Professor           â
â  ð MatrÃ­cula           â
â  ðï¸ Banco: crudmongo    â
âââââââââââââââââââââââââââ
```

---

## ðï¸ A soluÃ§Ã£o: um bairro de microserviÃ§os!

No modelo de **microserviÃ§os**, cada entidade vira uma casinha independente. Se uma pega fogo, as outras continuam de pÃ©.

```
ââââââââââââââââ   ââââââââââââââââ   ââââââââââââââââ
â  ð¤ pessoa   â   â  ð curso    â   â  ð disciplinaâ
â   svc:8082   â   â   svc:8083   â   â    svc:8084   â
â  ðï¸ pessoadb â   â  ðï¸ cursodb  â   â  ðï¸ discipldb â
ââââââââââââââââ   ââââââââââââââââ   ââââââââââââââââ

ââââââââââââââââ   ââââââââââââââââ
â  ð¨âð« professorâ   â  ð matricula â  â este Ã© o nosso exemplo!
â   svc:8085   â   â   svc:8081   â
â  ðï¸ profdb   â   â  ðï¸ matriculadbâ
ââââââââââââââââ   ââââââââââââââââ
```

Cada casinha tem: **sua prÃ³pria porta**, **seu prÃ³prio banco** e **suas prÃ³prias responsabilidades**. Elas conversam entre si via **chamadas HTTP REST** â como vizinhos que trocam mensagens pelo interfone.

---

## âï¸ Monolito vs MicroserviÃ§o â A comparaÃ§Ã£o honesta

| O que muda?          | Monolito (`crudmongo`)          | MicroserviÃ§o (`matricula-service`) |
|----------------------|---------------------------------|------------------------------------|
| ð Porta             | 8080                            | 8081                               |
| ðï¸ Banco             | `crudmongo` (compartilhado)     | `matriculadb` (exclusivo)          |
| ð¦ Deploy            | Um Ãºnico JAR gordo              | JARs independentes e leves         |
| ð Relacionamentos   | `@ManyToOne` entre entidades    | Apenas IDs (`pessoaId`, `cursoId`) |
| ð¬ ComunicaÃ§Ã£o       | Chamada direta de mÃ©todo Java   | HTTP REST entre serviÃ§os           |
| ð Escala            | Escala tudo ou nada             | Escala sÃ³ o que estÃ¡ sofrendo      |

---

## ï¿½ Antes e Depois â O que mudou de verdade no cÃ³digo

Chega de teoria! Veja exatamente o que existia no monolito (`crudmongo`) e como ficou no microserviÃ§o (`matricula-service`).

---

### ð Estrutura do projeto

**Antes â tudo no mesmo projeto:**
```
backend/src/main/java/com/exemplo/crudmongo/
âââ controller/
â   âââ MatriculaController.java   â junto com Pessoa, Curso, Professor...
â   âââ PessoaController.java
â   âââ CursoController.java
âââ Model/
â   âââ Matricula.java
â   âââ Pessoa.java                â MatrÃ­cula conhecia Pessoa diretamente
â   âââ Curso.java
âââ repository/
    âââ MatriculaRepository.java
```

**Depois â projeto isolado, responsabilidade Ãºnica:**
```
microservicos/matricula-service/src/main/java/com/exemplo/matriculaservice/
âââ controller/
â   âââ MatriculaController.java   â sÃ³ matrÃ­cula aqui
âââ model/
â   âââ Matricula.java             â nÃ£o conhece Pessoa nem Curso
âââ repository/
    âââ MatriculaRepository.java
```

---

### ð Relacionamento entre entidades

**Antes â acoplamento direto com `@ManyToOne`:**
```java
// Matricula.java no monolito
@ManyToOne
@JoinColumn(name = "pessoa_id")
private Pessoa pessoa;       // â importava a classe Pessoa do mesmo projeto

@ManyToOne
@JoinColumn(name = "curso_id")
private Curso curso;         // â importava a classe Curso do mesmo projeto
```

**Depois â apenas IDs, sem acoplamento:**
```java
// Matricula.java no microserviÃ§o
private Long pessoaId;       // â sÃ³ guarda o ID; Pessoa mora em outro serviÃ§o
private Long cursoId;        // â sÃ³ guarda o ID; Curso mora em outro serviÃ§o
```

> ð§  **Por que isso importa?** No monolito, se a classe `Pessoa` mudasse, o cÃ³digo de `Matricula` poderia quebrar. No microserviÃ§o, `Matricula` nÃ£o sabe nem que `Pessoa` existe â ela sÃ³ anota o nÃºmero.

---

### âï¸ ConfiguraÃ§Ã£o do banco

**Antes â banco compartilhado entre todas as entidades:**
```properties
# application.properties do crudmongo (backend)
spring.data.mongodb.uri=mongodb://localhost:27017/crudmongo
# Pessoa, Curso, Matricula, Professor... todos no mesmo banco
```

**Depois â banco exclusivo, isolado:**
```properties
# application.properties do matricula-service
server.port=8081
spring.datasource.url=jdbc:h2:mem:matriculadb   # banco sÃ³ de matrÃ­cula
spring.application.name=matricula-service
```

---

### ð SeguranÃ§a (Spring Security)

**Antes â toda requisiÃ§Ã£o passava pelo filtro JWT:**
```java
// SecurityConfig.java no monolito
// Cada endpoint exigia token e validaÃ§Ã£o de role
.requestMatchers("/api/matriculas/**").hasAnyRole("PROFESSOR", "ALUNO")
```

**Depois â serviÃ§o aberto para facilitar o aprendizado:**
```java
// No microserviÃ§o didÃ¡tico, a seguranÃ§a foi removida intencionalmente
// para que os alunos foquem na arquitetura, nÃ£o na autenticaÃ§Ã£o.
// Em produÃ§Ã£o real, cada microserviÃ§o teria seu prÃ³prio mecanismo de auth
// ou usaria um API Gateway central.
```

---

### ð¡ Como acessar a entidade MatrÃ­cula

**Antes â uma URL, tudo em um servidor:**
```
GET http://localhost:8080/api/matriculas   â monolito na porta 8080
```

**Depois â serviÃ§o dedicado com porta prÃ³pria:**
```
GET http://localhost:8081/api/matriculas   â microserviÃ§o na porta 8081
```

---

### ð Resumo visual do antes e depois

```
ANTES (Monolito)
âââââââââââââââââââââââââââââââââââââââââââââââââââââ
  [ browser ]
       â
       â¼
  [ crudmongo :8080 ]
  ââââââââââââââââââââââââââââââââââââââââââââââ
  â  /api/pessoas   /api/cursos  /api/matriculasâ
  â  /api/turmas    /api/professores  ...       â
  â                                            â
  â  ðï¸ Um Ãºnico banco para tudo               â
  ââââââââââââââââââââââââââââââââââââââââââââââ


DEPOIS (MicroserviÃ§os)
âââââââââââââââââââââââââââââââââââââââââââââââââââââ
  [ browser ]
       â
  ââââââ´âââââ¬âââââââââââââââ¬âââââââââââââââ
  â¼         â¼              â¼              â¼
[:8082]   [:8083]        [:8084]        [:8081]
pessoa    curso          disciplina     matricula
ðï¸ propdb ðï¸ cursodb    ðï¸ discipldb   ðï¸ matriculadb
```

---

## ï¿½ðï¸ Estrutura de arquivos â O mapa da casinha

```
microservicos/
âââ matricula-service/
    âââ pom.xml                          â lista de ingredientes
    âââ src/main/
        âââ java/com/exemplo/matriculaservice/
        â   âââ MatriculaServiceApplication.java  â a porta de entrada
        â   âââ config/
        â   â   âââ MatriculaDataLoader.java      â dados de teste iniciais
        â   âââ controller/
        â   â   âââ MatriculaController.java      â quem atende as requisiÃ§Ãµes
        â   âââ model/
        â   â   âââ Matricula.java                â o molde do objeto
        â   âââ repository/
        â   â   âââ MatriculaRepository.java      â quem fala com o banco
        â   âââ service/
        â       âââ MatriculaService.java         â as regras do negÃ³cio
        âââ resources/
            âââ application.properties           â as configuraÃ§Ãµes
```

---

## ð Destrinchando cada arquivo

---

### ð¦ `pom.xml` â A lista de ingredientes

Diferente do monolito que tem dezenas de dependÃªncias, o microserviÃ§o usa **apenas o necessÃ¡rio**. Sem Spring Security, sem JWT â sÃ³ o bÃ¡sico para gerenciar matrÃ­culas:

```xml
spring-boot-starter-web       <!-- para criar a API REST -->
spring-boot-starter-data-jpa  <!-- para salvar no banco    -->
h2                            <!-- o banco em memÃ³ria      -->
```

> ð¡ **Dica:** pense no `pom.xml` como a lista de compras do supermercado. VocÃª sÃ³ compra o que vai usar â nÃ£o precisa de farinha se vai fazer uma salada.

---

### âï¸ `application.properties` â As configuraÃ§Ãµes da casinha

```properties
server.port=8081                              # nossa porta exclusiva
spring.application.name=matricula-service    # nosso nome no bairro
spring.datasource.url=jdbc:h2:mem:matriculadb # nosso banco exclusivo
```

> ð  **Regra de ouro do bairro:** **nenhuma casinha divide o banco com outra**. Isso se chama **Database per Service** â cada serviÃ§o Ã© dono dos seus prÃ³prios dados.

---

### ð§± `model/Matricula.java` â O molde do objeto

Aqui mora a diferenÃ§a mais importante dos microserviÃ§os. Preste atenÃ§Ã£o:

```java
// â NO MONOLITO funcionava assim:
@ManyToOne
private Pessoa pessoa;   // importava a classe Pessoa direto!

// â NO MICROSERVIÃO fazemos assim:
private Long pessoaId;   // guardamos apenas o ID
private Long cursoId;    // o serviÃ§o de Pessoa fica na casinha dele
```

**Por que nÃ£o usar `@ManyToOne`?**
Porque `Pessoa` pertence ao serviÃ§o de pessoas. Se importarmos a classe `Pessoa` aqui, as duas casinhas ficam **grudadas** â e aÃ­ voltamos ao problema do monolito. Queremos vizinhos independentes, nÃ£o siameses!

---

### ð `repository/MatriculaRepository.java` â O bibliotecÃ¡rio do banco

AlÃ©m dos mÃ©todos automÃ¡ticos do `JpaRepository`, ensinamos o Spring a buscar dados por campos especÃ­ficos â apenas escrevendo o nome certo do mÃ©todo:

```java
findByPessoaId(Long pessoaId)  // â SELECT * FROM matricula WHERE pessoa_id = ?
findByCursoId(Long cursoId)    // â SELECT * FROM matricula WHERE curso_id = ?
findByAtivo(boolean ativo)     // â SELECT * FROM matricula WHERE ativo = ?
```

> ðª **MÃ¡gica do Spring Data:** o framework lÃª o nome do mÃ©todo e **gera o SQL sozinho**. Sem escrever uma linha de SQL!

---

### ð§  `service/MatriculaService.java` â O cÃ©rebro da operaÃ§Ã£o

Toda a inteligÃªncia do serviÃ§o fica aqui. O Controller sÃ³ recebe pedidos, o Service decide o que fazer com eles.

Destaque especial para o **soft delete** â uma tÃ©cnica onde nÃ£o apagamos o registro, apenas o "desligamos":

```java
// Em vez de deletar do banco, apenas marcamos como inativo
public void desativar(Long id) {
    Matricula m = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Matricula nao encontrada: " + id));
    m.setAtivo(false);   // o registro continua no banco!
    repository.save(m);
}
```

> ðï¸ **Por que soft delete?** Imagine cancelar uma matrÃ­cula e perder todo o histÃ³rico do aluno. Com soft delete, o registro fica guardado para fins de auditoria â apenas invisÃ­vel nas listagens normais.

---

### ðª `controller/MatriculaController.java` â A recepÃ§Ã£o do serviÃ§o

O Controller Ã© a porta de entrada da casinha. Ele recebe as requisiÃ§Ãµes HTTP e passa para o Service resolver:

| MÃ©todo   | URL                              | O que faz                           |
|----------|----------------------------------|-------------------------------------|
| `GET`    | `/api/matriculas`                | ð Lista todas as matrÃ­culas        |
| `GET`    | `/api/matriculas/{id}`           | ð Busca uma pelo ID                |
| `GET`    | `/api/matriculas/pessoa/{id}`    | ð¤ Todas as matrÃ­culas de uma pessoa|
| `GET`    | `/api/matriculas/curso/{id}`     | ð Todas as matrÃ­culas de um curso  |
| `POST`   | `/api/matriculas`                | â Cria uma nova (retorna 201)      |
| `PUT`    | `/api/matriculas/{id}`           | âï¸ Atualiza todos os dados          |
| `PATCH`  | `/api/matriculas/{id}/desativar` | ð´ Desativa (soft delete)           |
| `DELETE` | `/api/matriculas/{id}`           | ðï¸ Remove permanentemente           |

---

### ð± `config/MatriculaDataLoader.java` â O jardineiro dos dados

Toda vez que o serviÃ§o sobe, este arquivo planta 4 matrÃ­culas de exemplo no banco H2. Assim vocÃª jÃ¡ tem dados para testar sem precisar cadastrar nada manualmente:

```java
repository.save(new Matricula(1L, 1L, "2024-01-10", true));
repository.save(new Matricula(1L, 2L, "2024-01-15", true));
// ... e mais 2
```

---

## ð Subindo o serviÃ§o

```bash
# Entre na pasta do microserviÃ§o
cd microservicos/matricula-service

# Suba o servidor
mvn spring-boot:run
```

Quando aparecer `Started MatriculaServiceApplication in X.XX seconds`, o serviÃ§o estÃ¡ pronto! Acesse:

- ð **API:** `http://localhost:8081/api/matriculas`
- ðï¸ **H2 Console:** `http://localhost:8081/h2-console`
  - JDBC URL: `jdbc:h2:mem:matriculadb`
  - UsuÃ¡rio: `sa` | Senha: `sa`

---

## ð§ª Testando na prÃ¡tica

### Ver todas as matrÃ­culas
```http
GET http://localhost:8081/api/matriculas
```

### Ver matrÃ­culas de uma pessoa
```http
GET http://localhost:8081/api/matriculas/pessoa/1
```

### Criar uma nova matrÃ­cula
```http
POST http://localhost:8081/api/matriculas
Content-Type: application/json

{
  "pessoaId": 1,
  "cursoId": 2,
  "dataMatricula": "2024-05-11",
  "ativo": true
}
```

### Cancelar uma matrÃ­cula (sem apagar do banco)
```http
PATCH http://localhost:8081/api/matriculas/1/desativar
```

---

## ð Atividade (PrÃ³ximas duas aulas) Como criar o seu prÃ³prio microserviÃ§o

Agora Ã© com vocÃª! Escolha uma entidade, faÃ§a a implementaÃ§Ã£o seguindo o roteiro:

### Passo 1 â Crie a estrutura
Crie a pasta `microservicos/<suaentidade>-service/` com a mesma estrutura de pastas do exemplo.

### Passo 2 â Configure o `pom.xml`
Copie o `pom.xml` do `matricula-service` e troque:
- `<artifactId>matricula-service</artifactId>` â `<artifactId>suaentidade-service</artifactId>`
- `<name>` e `<description>` para o seu serviÃ§o

### Passo 3 â Configure o `application.properties`
```properties
server.port=8082                           # porta nova (8082, 8083, ...)
spring.application.name=suaentidade-service
spring.datasource.url=jdbc:h2:mem:suaentidadedb
```

### Passo 4 â Crie as 5 classes
Seguindo sempre este padrÃ£o:

| Classe        | Responsabilidade                                  | Lembrete especial                   |
|---------------|---------------------------------------------------|-------------------------------------|
| `model/`      | Define os campos da entidade com `@Entity`        | Use IDs para referÃªncias externas   |
| `repository/` | Estende `JpaRepository`, queries por nome         | Sem SQL manual!                     |
| `service/`    | LÃ³gica de negÃ³cio, injeÃ§Ã£o via construtor         | Nunca coloque lÃ³gica no Controller  |
| `controller/` | Endpoints REST com `@RestController`              | Delegue tudo ao Service             |
| `config/`     | `DataLoader` com dados iniciais para testes       | Implemente `CommandLineRunner`      |

### Passo 5 â Crie a classe principal
```java
@SpringBootApplication
public class SuaEntidadeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SuaEntidadeServiceApplication.class, args);
    }
}
```

---

> â ï¸ **Regra de ouro:** se o seu serviÃ§o precisa de informaÃ§Ãµes de outro serviÃ§o (ex: buscar o nome da Pessoa pelo ID), faÃ§a uma **chamada HTTP REST** para o serviÃ§o de Pessoa. **Nunca importe a classe Java de outro microserviÃ§o** â isso recria o acoplamento que estamos tentando evitar.

---

## ð Desafios de EvoluÃ§Ã£o do Projeto

Abaixo estÃ£o os desafios para vocÃª continuar evoluindo o projeto, do mais simples ao mais avanÃ§ado.

---

### ð¢ NÃ­vel 1 â Replicar o padrÃ£o (obrigatÃ³rio)

> **Objetivo:** consolidar o que foi aprendido criando novos microserviÃ§os seguindo exatamente o mesmo padrÃ£o do `matricula-service`.

Crie um microserviÃ§o para **cada entidade abaixo**, seguindo os passos do guia anterior:

| Entidade      | SugestÃ£o de porta | Nome do banco     | Branch sugerida              |
|---------------|-------------------|-------------------|------------------------------|
| ð¤ Pessoa     | 8082              | `pessoadb`        | `feature/pessoa-service`     |
| ð Curso      | 8083              | `cursodb`         | `feature/curso-service`      |
| ð Disciplina | 8084              | `disciplinadb`    | `feature/disciplina-service` |
| ð¨âð« Professor  | 8085              | `professordb`     | `feature/professor-service`  |
| ð« Turma      | 8086              | `turmadb`         | `feature/turma-service`      |

**EntregÃ¡vel:** cada serviÃ§o deve subir com `mvn spring-boot:run` e responder em sua respectiva porta.

---

### ð¡ NÃ­vel 2 â ComunicaÃ§Ã£o entre serviÃ§os

> **Objetivo:** fazer dois microserviÃ§os conversarem via HTTP, sem compartilhar cÃ³digo Java.

**Desafio:** ao buscar uma matrÃ­cula pelo ID, retorne tambÃ©m o **nome da pessoa** e o **nome do curso** â buscando essas informaÃ§Ãµes nos respectivos microserviÃ§os via `RestTemplate` ou `WebClient`.

Exemplo do retorno esperado:
```json
{
  "id": 1,
  "pessoaId": 1,
  "nomePessoa": "Ana Silva",       â buscado do pessoa-service (8082)
  "cursoId": 2,
  "nomeCurso": "Engenharia",       â buscado do curso-service (8083)
  "dataMatricula": "2024-01-10",
  "ativo": true
}
```

**Dica:** crie uma classe `MatriculaDetalhadaDTO` para montar essa resposta no `MatriculaService`, sem alterar a entidade `Matricula`.

---

### ð  NÃ­vel 3 â Tratamento de erros e resiliÃªncia

> **Objetivo:** deixar o sistema robusto quando um serviÃ§o vizinho estiver fora do ar.

**Desafios:**

- **3.1 â Handler global de erros:** crie uma classe `@ControllerAdvice` que capture `RuntimeException` e devolva respostas JSON padronizadas com `status`, `mensagem` e `timestamp`, em vez do Whitelabel Error Page.

- **3.2 â Fallback:** na comunicaÃ§Ã£o entre serviÃ§os (NÃ­vel 2), se o `pessoa-service` estiver fora, o `matricula-service` **nÃ£o deve cair junto**. Retorne `"nomePessoa": "indisponÃ­vel"` como fallback em vez de propagar o erro.

---

### ð´ NÃ­vel 4 â Dockerizando o bairro

> **Objetivo:** empacotar cada microserviÃ§o em um container Docker e orquestrÃ¡-los juntos.

**Desafio:** crie um `Dockerfile` para o `matricula-service` e adicione-o ao `docker-compose.yml` da raiz do projeto, de forma que todos os serviÃ§os subam com um Ãºnico comando:

```bash
docker-compose up --build
```

**ReferÃªncia:** veja o `Dockerfile` e o `docker-compose.yml` jÃ¡ existentes na raiz do projeto como modelo.

---

### â« NÃ­vel 5 â API Gateway 

> **Objetivo:** criar um ponto de entrada Ãºnico para todos os microserviÃ§os, como uma portaria do condomÃ­nio.

**OpÃ§Ã£o A â Spring Cloud Gateway** *(recomendada)*

Cria um novo projeto `gateway-service` com Spring Cloud Gateway e configura as rotas no `application.properties`:

```properties
server.port=8080
spring.cloud.gateway.routes[0].id=matricula
spring.cloud.gateway.routes[0].uri=http://localhost:8081
spring.cloud.gateway.routes[0].predicates[0]=Path=/matriculas/**

spring.cloud.gateway.routes[1].id=pessoa
spring.cloud.gateway.routes[1].uri=http://localhost:8082
spring.cloud.gateway.routes[1].predicates[0]=Path=/pessoas/**
```

---

**OpÃ§Ã£o B â Gateway manual com Spring Boot** *(sem dependÃªncias novas â funciona offline)*

Crie um projeto `gateway-service` usando apenas `spring-boot-starter-web` (jÃ¡ presente em todos os microserviÃ§os) e implemente o roteamento manualmente com `RestTemplate`:

```java
@RestController
@RequestMapping("/gateway")
public class GatewayController {

    private final RestTemplate restTemplate = new RestTemplate();

    // Redireciona /gateway/matriculas/** â http://localhost:8081/api/matriculas
    @GetMapping("/matriculas")
    public ResponseEntity<String> matriculas() {
        return restTemplate.getForEntity(
            "http://localhost:8081/api/matriculas", String.class);
    }

    // Redireciona /gateway/pessoas/** â http://localhost:8082/api/pessoas
    @GetMapping("/pessoas")
    public ResponseEntity<String> pessoas() {
        return restTemplate.getForEntity(
            "http://localhost:8082/api/pessoas", String.class);
    }
}
```

**Como fica a arquitetura:**
```
[ cliente ]
    â
    â¼
[ gateway-service :8080 ]  â portaria Ãºnica (OpÃ§Ã£o B)
    â              â
    â¼              â¼
 :8081          :8082      ...
matricula       pessoa
```

> ð¡ A OpÃ§Ã£o B nÃ£o Ã© um gateway de produÃ§Ã£o, mas demonstra o **conceito** de ponto de entrada Ãºnico â que Ã© o objetivo do exercÃ­cio. A OpÃ§Ã£o A (Spring Cloud) seria o caminho em um projeto real com internet disponÃ­vel.

---

> ð¬ **Uma Ãºltima palavra:** microserviÃ§os nÃ£o sÃ£o a soluÃ§Ã£o para tudo. Eles trazem complexidade operacional â mais processos, mais deploys, mais pontos de falha. O monolito do `crudmongo` Ã© perfeitamente vÃ¡lido para projetos pequenos. O objetivo aqui foi entender os **conceitos e o padrÃ£o** â para que, quando o projeto crescer de verdade, vocÃª saiba como e quando separar as responsabilidades. ðï¸
