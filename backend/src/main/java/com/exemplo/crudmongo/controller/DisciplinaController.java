package com.exemplo.crudmongo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import com.exemplo.crudmongo.service.DisciplinaService;
import com.exemplo.crudmongo.model.Disciplina;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/disciplina", "/api/disciplinas"})
public class DisciplinaController 
{
    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService service)
    {
        this.service = service;
    }

    @GetMapping
    public List<Disciplina> listAll()
    {
        return service.listAll();
    }

    @PostMapping
    public Disciplina createDisciplina(@RequestBody Disciplina disciplina)
    {
        return service.create(disciplina);
    }

    @PutMapping("/{id}")
    public Disciplina updateDisciplina(@PathVariable Long id, @RequestBody Disciplina disciplina)
    {
        return service.update(id, disciplina);
    }

    @DeleteMapping("/{id}")
    public void deleteDisciplina(@PathVariable Long id)
    {
        service.delete(id);
    }

}