package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exemplo.crudmongo.Model.Disciplina;
import com.exemplo.crudmongo.repository.DisciplinaRepository;

@Service
public class DisciplinaService {
    private final DisciplinaRepository repository; // Repositório para acesso ao banco de dados
    
    /**
     * Injeta o repositório PessoaRepository via construtor.
     */
    public DisciplinaService(DisciplinaRepository repository) {
        this.repository = repository;
    }


    /**
     * Retorna todas as pessoas cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Disciplina> listarTodas() {
        return repository.findAll();
    }

    /**
     * Salva uma nova pessoa no banco de dados.
     * @param disciplina Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public Disciplina salvar(Disciplina disciplina) {
        return repository.save(disciplina);
    }

    /**
    
    /**
     * Exclui uma pessoa pelo ID.
     * @param id Identificador da pessoa a ser excluída
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}