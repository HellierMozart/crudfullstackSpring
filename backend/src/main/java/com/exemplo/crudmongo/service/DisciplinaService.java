package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.PathVariable;

import com.exemplo.crudmongo.repository.DisciplinaRepository;
import com.exemplo.crudmongo.model.Disciplina;

@Service
public class DisciplinaService 
{
    private final DisciplinaRepository repository;

    public DisciplinaService(DisciplinaRepository repository) 
    {
        this.repository = repository;
    }

    public List<Disciplina> listAll()
    {
       return repository.findAll();
    }

    public Disciplina create(Disciplina disciplina)
    {
        return repository.save(disciplina);
    }

    public Disciplina update(@PathVariable Long id, Disciplina newDisciplina)
    {
        return repository.findById(id).map(d -> {
            d.setNome(newDisciplina.getNome());
            d.setCargaHoraria(newDisciplina.getCargaHoraria());
            d.setAtivo(newDisciplina.getAtivo());
            return repository.save(d);
        }).orElseThrow(() -> new RuntimeException("Disciplina não identificada"));
    }

    public void delete(Long id)
    {
        repository.deleteById(id);
    }
}