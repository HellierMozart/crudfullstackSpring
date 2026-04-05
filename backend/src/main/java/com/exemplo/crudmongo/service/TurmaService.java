package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.exemplo.crudmongo.model.Turma;
import com.exemplo.crudmongo.repository.TurmaRepository;

@Service
public class TurmaService {
    private final TurmaRepository repository;

    public TurmaService( TurmaRepository repository )
    {
        this.repository = repository;
    }

    public List<Turma> listAll()
    {
        return repository.findAll();
    }

    public Turma create(Turma turma)
    {
        return repository.save(turma);
    }

    public Turma update(@PathVariable Long id, Turma newTurma)
    {
        return repository.findById(id).map(t -> {
            t.setNome(newTurma.getNome());
            t.setAno(newTurma.getAno());
            t.setAtivo(newTurma.getAtivo());
            return repository.save(t);
        }).orElseThrow(() -> new RuntimeException("Turma nao encontrada"));
    }

    public void delete(Long id)
    {
        repository.deleteById(id);
    }
}

