package com.exemplo.crudmongo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.crudmongo.model.Matricula;
import com.exemplo.crudmongo.service.MatriculaService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(value = {"/api/matricula", "/api/matriculas"})
public class MatriculaController {
    private final MatriculaService service;

    public MatriculaController ( MatriculaService service )
    {
        this.service = service;
    }

    @GetMapping
    public List<Matricula> listAll()
    {
        return service.listAll();
    }   

    @PostMapping
    public Matricula create(@RequestBody Matricula matricula)
    {
        return service.create(matricula);
    }

    @PutMapping("/{id}")
    public Matricula update(@PathVariable Long id, @RequestBody Matricula newMatricula)
    {
        return service.update(id, newMatricula);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id)
    {
        service.delete(id);
    }
}
