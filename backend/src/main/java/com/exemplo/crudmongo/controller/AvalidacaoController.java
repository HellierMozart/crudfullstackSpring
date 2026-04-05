package com.exemplo.crudmongo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.crudmongo.model.Avaliacao;
import com.exemplo.crudmongo.service.AvaliacaoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping(value = {"/api/avalicao", "/api/avaliacoes"})
public class AvalidacaoController 
{
    private final AvaliacaoService service;

    public AvalidacaoController ( AvaliacaoService service )
    {
        this.service = service;
    }

    @GetMapping
    public List<Avaliacao> listAll()
    {
        return service.listAll();
    }

    @PostMapping
    public Avaliacao create(@RequestBody Avaliacao avaliacao)
    {
        return service.create(avaliacao);
    }

    @PutMapping("/{id}")
    public Avaliacao update(@PathVariable Long id, @RequestBody Avaliacao newAvaliacao)
    {
        return service.update(id, newAvaliacao);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id)
    {
        service.delete(id);
    }
}
