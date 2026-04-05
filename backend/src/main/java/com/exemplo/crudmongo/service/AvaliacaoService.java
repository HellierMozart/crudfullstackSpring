package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.exemplo.crudmongo.model.Avaliacao;
import com.exemplo.crudmongo.repository.AvaliacaoRepository;

@Service
public class AvaliacaoService {
    private final AvaliacaoRepository repository;

    public AvaliacaoService( AvaliacaoRepository repository )
    {
        this.repository = repository;
    }

    public List<Avaliacao> listAll()
    {
        return repository.findAll();
    }

    public Avaliacao create(Avaliacao avaliacao)
    {
        return repository.save(avaliacao);
    }

    public Avaliacao update(@PathVariable Long id, Avaliacao newAvaliacao)
    {
        return repository.findById(id).map(a -> {
            a.setPessoa(newAvaliacao.getPessoa());
            a.setDisciplina(newAvaliacao.getDisciplina());
            a.setNota(newAvaliacao.getNota());
            a.setData(newAvaliacao.getData());
            a.setAtivo(newAvaliacao.getAtivo());
            return repository.save(a);
        }).orElseThrow(() -> new RuntimeException("Avaliacao nao identificada."));
    }

    public void delete(Long id)
    {
        repository.deleteById(id);
    }
}
