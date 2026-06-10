# Como testar os microserviços — guia prático

Este documento foi reestruturado para ser direto, prático e fácil de seguir. Contém:
- Quickstart (subir o serviço)
- Lista de serviços e portas
- Passo a passo CRUD simples com exemplos (curl + PowerShell)
- Troubleshooting rápido
- Comandos Docker para build/run

Objetivo: em 1 minuto você consegue criar, listar, atualizar e excluir um registro de exemplo.

---

## Quickstart rápido

1. Abra o projeto no IDE ou terminal.
2. Rode o microserviço que deseja testar (ex.: `curso-service`):

```bash
cd microservicos/curso-service
mvn spring-boot:run
```

3. Verifique que o serviço está no ar acessando no navegador `http://localhost:8083/api/cursos`.

---

## Serviços e portas (padrão do repositório)

- `pessoa-service`  → http://localhost:8082/api/pessoas
- `matricula-service` → http://localhost:8081/api/matriculas
- `curso-service` → http://localhost:8083/api/cursos
- `disciplina-service` → http://localhost:8084/api/disciplinas
- `professor-service` → http://localhost:8085/api/professores
- `turma-service` → http://localhost:8086/api/turmas

Cada serviço também disponibiliza H2 Console (quando configurado): `http://localhost:{porta}/h2-console`.

---
 # Como testar os microserviços — guia prático e enxuto

Este documento mostra rapidamente como testar os microserviços localmente: comandos mínimos, exemplos práticos (curl + PowerShell) e dicas de resolução de problemas.

## Serviços e portas (rápido)

- `pessoa-service`  → http://localhost:8082/api/pessoas
- `matricula-service` → http://localhost:8081/api/matriculas
- `curso-service` → http://localhost:8083/api/cursos
- `disciplina-service` → http://localhost:8084/api/disciplinas
- `professor-service` → http://localhost:8085/api/professores
- `turma-service` → http://localhost:8086/api/turmas

Cada serviço também expõe o H2 Console (quando em memória): `http://localhost:{porta}/h2-console`.

## Quick-start (em 1 minuto)

1. Inicie o serviço desejado (IDE ou `mvn spring-boot:run`).
2. Teste um GET no navegador para confirmar que responde (ex.: `http://localhost:8083/api/cursos`).
3. Execute os comandos de exemplo abaixo para criar, listar, atualizar e excluir.

## Exemplos práticos (comandos mínimos)

Observação: os exemplos usam `curso-service` (porta 8083). Troque porta e rota para testar outro serviço.

Criar (POST)

Linux / macOS / WSL / Git Bash:

```bash
curl -s -i -X POST -H "Content-Type: application/json" \
  -d '{"nome":"Engenharia de Software","ativo":true}' \
  http://localhost:8083/api/cursos
```

PowerShell (Windows):

```powershell
Invoke-RestMethod -Method Post -Uri 'http://localhost:8083/api/cursos' \
  -ContentType 'application/json' -Body '{"nome":"Engenharia de Software","ativo":true}'
```

Listar todos (GET):

```bash
curl -s http://localhost:8083/api/cursos
```

Buscar por id (GET):

```bash
curl -s http://localhost:8083/api/cursos/{id}
```

Atualizar (PUT):

```bash
curl -s -X PUT -H "Content-Type: application/json" \
  -d '{"nome":"Engenharia - Atualizado","ativo":true}' \
  http://localhost:8083/api/cursos/{id}
```

Desativar (PATCH):

```bash
curl -s -X PATCH http://localhost:8083/api/cursos/{id}/desativar
```

Excluir (DELETE):

```bash
curl -s -X DELETE http://localhost:8083/api/cursos/{id}
```

Exemplo rápido para `pessoa-service` (porta 8082) — criar:

```bash
curl -s -i -X POST -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","idade":25,"email":"joao@email.com","ativo":true}' \
  http://localhost:8082/api/pessoas
```

## O que verificar após cada operação

- POST: status `201`/`200` e corpo com `id` → guarde o `id` para próximas operações.
- GET: lista ou objeto retornado; `404` significa `id` não encontrado.
- PUT/PATCH/DELETE: status `200`/`204` quando bem sucedido.

## Erros comuns e solução rápida

- 400 / 415: payload inválido ou `Content-Type` ausente — valide JSON.
- 404: URL/porta/rota incorreta ou serviço não está rodando.
- Sem resposta: verifique logs da aplicação (IDE) ou `docker logs` se estiver em container.

## Dicas para Postman

- Crie uma coleção com os endpoints usados no demo (GET, POST, PUT, PATCH, DELETE).
- Defina `Content-Type: application/json` no header e use o body raw → JSON.
- Salve o `id` retornado do POST em uma variável da coleção para reaplicar nas próximas requisições.

## Executando com Docker (rápido)

Build de um serviço (ex.: `curso-service`):

```bash
cd microservicos/curso-service
docker build -t curso-service .
docker run -d --name curso-service -p 8083:8083 curso-service
```

Ver logs:

```bash
docker logs -f curso-service
```

## Perguntas frequentes rápidas

- Como sei o formato do JSON? — Veja o modelo (`model`) do serviço: por exemplo `Pessoa` tem `nome`, `idade`, `email`, `ativo`.
- IDs são gerados automaticamente? — Sim, JPA gera `id` ao salvar (ver `@GeneratedValue`).

---

Se quiser, eu deixo este documento com blocos prontos para copiar/colar em Postman (variáveis) ou replico o mesmo conjunto de exemplos curtos para os outros serviços. Qual prefere? 
- replico os comandos práticos (curl + PowerShell) para `pessoa-service` e `matricula-service`;
