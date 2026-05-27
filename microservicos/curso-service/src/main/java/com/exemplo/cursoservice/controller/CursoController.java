package com.exemplo.cursoservice.controller;

import com.exemplo.cursoservice.model.Curso;
import com.exemplo.cursoservice.service.CursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST do microserviço de Cursos.
 *
 * Base URL: http://localhost:8083/api/cursos
 *
 * Endpoints disponíveis:
 *   GET    /api/cursos              – lista todos
 *   GET    /api/cursos/{id}         – busca por ID
 *   GET    /api/cursos/nome/{nome}  – busca por nome
 *   POST   /api/cursos              – cria novo
 *   PUT    /api/cursos/{id}         – atualiza
 *   PATCH  /api/cursos/{id}/desativar – desativa (soft delete)
 *   DELETE /api/cursos/{id}         – remove permanentemente
 */
@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Curso> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public List<Curso> buscarPorNome(@PathVariable String nome) {
        return service.buscarPorNome(nome);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Curso criar(@RequestBody Curso curso) {
        return service.salvar(curso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizar(@PathVariable Long id,
                                           @RequestBody Curso curso) {
        try {
            return ResponseEntity.ok(service.atualizar(id, curso));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        try {
            service.desativar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
