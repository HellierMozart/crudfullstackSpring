# Microserviços com Spring Boot ? Guia Didático

> Branch: `feature/microservicos`  
> Entidade de exemplo: **Matrícula**  
> Porta: **8081**

---

## O que é um Microserviço?

No modelo **monolítico**, toda a aplicação roda em um único projeto: Pessoa, Curso, Disciplina, Matrícula, Professor ? tudo junto, um banco, uma porta.

No modelo de **microserviços**, cada responsabilidade vira um projeto independente:

```
Monolito                          Microserviços
?????????????????????????         ?????????????????????????????????????
???????????????????????           ????????????????  ????????????????
?   crudmongo (8080)  ?           ? pessoa-svc   ?  ?  curso-svc   ?
?  ??? Pessoa         ?   ???     ?   (8082)     ?  ?   (8083)     ?
?  ??? Curso          ?           ????????????????  ????????????????
?  ??? Disciplina     ?           ????????????????  ????????????????
?  ??? Professor      ?           ?disciplina-svc?  ?professor-svc ?
?  ??? Matricula      ?           ?   (8084)     ?  ?   (8085)     ?
?  Banco: crudmongo   ?           ????????????????  ????????????????
???????????????????????           ????????????????
                                  ?matricula-svc ? exemplo desta aula
                                  ?   (8081)     ?
                                  ????????????????
```

---

## Comparativo: Monolito vs Microserviço

| Aspecto              | Monolito (`crudmongo`)          | Microserviço (`matricula-service`) |
|----------------------|---------------------------------|------------------------------------|
| Porta                | 8080                            | 8081                               |
| Banco de dados       | `crudmongo` (compartilhado)     | `matriculadb` (exclusivo)          |
| Deploy               | Um único JAR                    | JAR independente                   |
| Relacionamentos JPA  | `@ManyToOne` entre entidades    | Apenas IDs (`pessoaId`, `cursoId`) |
| Comunicação          | Chamada direta de método Java   | HTTP REST entre serviços           |
| Escala               | Escala tudo junto               | Escala só o que precisa            |

---

## Estrutura de arquivos criada

```
microservicos/
??? matricula-service/
    ??? pom.xml
    ??? src/main/
        ??? java/com/exemplo/matriculaservice/
        ?   ??? MatriculaServiceApplication.java
        ?   ??? config/
        ?   ?   ??? MatriculaDataLoader.java
        ?   ??? controller/
        ?   ?   ??? MatriculaController.java
        ?   ??? model/
        ?   ?   ??? Matricula.java
        ?   ??? repository/
        ?   ?   ??? MatriculaRepository.java
        ?   ??? service/
        ?       ??? MatriculaService.java
        ??? resources/
            ??? application.properties
```

---

## Explicação de cada arquivo

### `pom.xml` ? Dependências do projeto

O microserviço usa apenas o que precisa. Não tem Spring Security, não tem JWT ? só o essencial:

```xml
spring-boot-starter-web      <!-- API REST -->
spring-boot-starter-data-jpa <!-- persistência JPA -->
h2                           <!-- banco em memória próprio -->
```

> Cada microserviço tem seu próprio `pom.xml`, totalmente independente.

---

### `application.properties` ? Configuração isolada

```properties
server.port=8081                        # porta exclusiva
spring.application.name=matricula-service
spring.datasource.url=jdbc:h2:mem:matriculadb  # banco exclusivo
```

**Regra:** nenhum microserviço compartilha banco com outro. Isso é chamado de **Database per Service**.

---

### `model/Matricula.java` ? Entidade sem @ManyToOne

```java
// ? NÃO faça isso em microserviços:
@ManyToOne
private Pessoa pessoa;

// ? Faça isso ? guarda apenas o ID:
private Long pessoaId;
private Long cursoId;
```

**Por quê?** A entidade `Pessoa` pertence a outro serviço. Se usarmos `@ManyToOne`, criamos um acoplamento de banco ? o que quebra a independência dos microserviços.

---

### `repository/MatriculaRepository.java` ? Queries customizadas

Além dos métodos herdados do `JpaRepository`, foram adicionadas queries por convenção de nome:

```java
List<Matricula> findByPessoaId(Long pessoaId);  // SELECT * WHERE pessoa_id = ?
List<Matricula> findByCursoId(Long cursoId);    // SELECT * WHERE curso_id = ?
List<Matricula> findByAtivo(boolean ativo);     // SELECT * WHERE ativo = ?
```

O Spring Data gera o SQL automaticamente a partir do nome do método.

---

### `service/MatriculaService.java` ? Lógica de negócio

Toda regra de negócio fica no Service, nunca no Controller. Destaques:

```java
// Soft delete ? desativa sem apagar do banco
public void desativar(Long id) {
    Matricula m = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Matrícula não encontrada: " + id));
    m.setAtivo(false);
    repository.save(m);
}
```

**Soft delete** é a prática de marcar um registro como inativo em vez de removê-lo fisicamente, preservando o histórico.

---

### `controller/MatriculaController.java` ? Endpoints REST

| Método   | URL                              | Ação                        |
|----------|----------------------------------|-----------------------------|
| `GET`    | `/api/matriculas`                | Lista todas                 |
| `GET`    | `/api/matriculas/{id}`           | Busca por ID                |
| `GET`    | `/api/matriculas/pessoa/{id}`    | Lista por pessoa            |
| `GET`    | `/api/matriculas/curso/{id}`     | Lista por curso             |
| `POST`   | `/api/matriculas`                | Cria nova (retorna 201)     |
| `PUT`    | `/api/matriculas/{id}`           | Atualiza completo           |
| `PATCH`  | `/api/matriculas/{id}/desativar` | Desativa (soft delete)      |
| `DELETE` | `/api/matriculas/{id}`           | Remove permanentemente      |

---

### `config/MatriculaDataLoader.java` ? Dados iniciais

Popula o banco H2 automaticamente ao subir o serviço, facilitando testes:

```java
repository.save(new Matricula(1L, 1L, "2024-01-10", true));
```

---

## Como executar

```bash
# Na pasta do microserviço:
cd microservicos/matricula-service
mvn spring-boot:run
```

Acesse:
- **API:** `http://localhost:8081/api/matriculas`
- **H2 Console:** `http://localhost:8081/h2-console`
  - JDBC URL: `jdbc:h2:mem:matriculadb`
  - User: `sa` / Senha: `sa`

---

## Como testar com exemplos HTTP

### Listar todas as matrículas
```http
GET http://localhost:8081/api/matriculas
```

### Buscar matrículas de uma pessoa
```http
GET http://localhost:8081/api/matriculas/pessoa/1
```

### Criar nova matrícula
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

### Desativar uma matrícula (soft delete)
```http
PATCH http://localhost:8081/api/matriculas/1/desativar
```

---

## Como criar um novo microserviço (guia para os alunos)

Replique o mesmo padrão para outra entidade seguindo estes passos:

1. **Criar a pasta** em `microservicos/<entidade>-service/`
2. **Copiar o `pom.xml`** e alterar o `artifactId` e `name`
3. **Criar o `application.properties`** com uma porta nova (8082, 8083...) e um nome de banco exclusivo
4. **Criar as 5 classes** seguindo o mesmo padrão:
   - `model/` ? entidade com `@Entity`, usando apenas IDs para referências externas
   - `repository/` ? estende `JpaRepository`, adicionar queries customizadas
   - `service/` ? lógica de negócio, injeção via construtor
   - `controller/` ? endpoints REST com `ResponseEntity`
   - `config/` ? `DataLoader` com dados iniciais
5. **Criar a classe `Application`** com `@SpringBootApplication`

> **Regra de ouro:** se a entidade precisa de dados de outro serviço, faça uma chamada HTTP REST ? nunca importe a classe Java do outro serviço.
