package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.exemplo.crudmongo.Model.Professor;
import com.exemplo.crudmongo.repository.ProfessorRepository;

@Service
public class ProfessorService {
    private final ProfessorRepository repository; // Repositório para acesso ao banco de dados
    
    /**
     * Injeta o repositório PessoaRepository via construtor.
     */
    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }


    /**
     * Retorna todas as pessoas cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Professor> listarTodas() {
        return repository.findAll();
    }

    /**
     * Salva uma nova pessoa no banco de dados.
     * @param professor Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public Professor salvar(Professor professor) {
        return repository.save(professor);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     * @param id Identificador da pessoa a ser atualizada
     * @param novoProfessor Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    public Professor atualizar(@PathVariable Long id,  Professor novoProfessor) {
        return repository.findById(id).map(p -> {
            p.setNome(novoProfessor.getNome());
            p.setArea(novoProfessor.getArea());
            p.setAtivo(novoProfessor.isAtivo());
            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    /**
     * Exclui uma pessoa pelo ID.
     * @param id Identificador da pessoa a ser excluída
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}