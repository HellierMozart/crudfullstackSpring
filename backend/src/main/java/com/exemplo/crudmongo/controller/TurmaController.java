package com.exemplo.crudmongo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.crudmongo.model.Turma;
import com.exemplo.crudmongo.service.TurmaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(value = {"/api/turma", "/api/turmas"})
public class TurmaController {
    private final TurmaService service;

    public TurmaController( TurmaService service )
    {
        this.service = service;
    }

    @GetMapping
    public List<Turma> listAll()
    {
        return service.listAll();
    }

    @PostMapping
    public Turma create(@RequestBody Turma turma)
    {
        return service.create(turma);
    }

    @PutMapping("/{id}")
    public Turma update(@PathVariable Long id, @RequestBody Turma newTurma)
    {
        return service.update(id, newTurma);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id)
    {
        service.delete(id);
    }
}