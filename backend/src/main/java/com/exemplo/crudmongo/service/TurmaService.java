package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.exemplo.crudmongo.Model.Turma;
import com.exemplo.crudmongo.repository.TurmaRepository;

@Service
public class TurmaService {
    private final TurmaRepository repository; // Repositório para acesso ao banco de dados
    
    /**
     * Injeta o repositório PessoaRepository via construtor.
     */
    public TurmaService(TurmaRepository repository) {
        this.repository = repository;
    }


    /**
     * Retorna todas as pessoas cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Turma> listarTodas() {
        return repository.findAll();
    }

    /**
     * Salva uma nova pessoa no banco de dados.
     * @param turma Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public Turma salvar(Turma turma) {
        return repository.save(turma);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     * @param id Identificador da pessoa a ser atualizada
     * @param novaTurma Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    public Turma atualizar(@PathVariable Long id,  Turma novaTurma) {
        return repository.findById(id).map(t -> {
            t.setNome(novaTurma.getNome());
            t.setAno(novaTurma.getAno());
            t.setAtivo(novaTurma.isAtivo());
            return repository.save(t);
        }).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    }

    /**
     * Exclui uma pessoa pelo ID.
     * @param id Identificador da pessoa a ser excluída
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}