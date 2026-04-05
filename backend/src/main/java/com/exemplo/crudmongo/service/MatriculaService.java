package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.exemplo.crudmongo.model.Matricula;
import com.exemplo.crudmongo.repository.MatriculaRepository;

@Service
public class MatriculaService {
    private final MatriculaRepository repository;
    
    public MatriculaService ( MatriculaRepository repository )
    {
        this.repository = repository;
    }

    public List<Matricula> listAll()
    {
        return repository.findAll();
    }

    public Matricula create(Matricula matricula)
    {
        return repository.save(matricula);
    }

    public Matricula update(@PathVariable Long id, Matricula newMatricula)
    {
        return repository.findById(id).map(m -> {
            m.setPessoa(newMatricula.getPessoa());
            m.setCurso(newMatricula.getCurso());
            m.setDataMatricula(newMatricula.getDataMatricula());
            m.setAtivo(newMatricula.getAtivo());
            return repository.save(m);
        }).orElseThrow(() -> new RuntimeException("Matricula não encontrada"));
    }

    public void delete(Long id)
    {
        repository.deleteById(id);
    }
}
