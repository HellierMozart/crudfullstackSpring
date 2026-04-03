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
     * Injeta o repositório TurmaRepository via construtor.
     */
    public TurmaService(TurmaRepository repository) {
        this.repository = repository;
    }


    /**
     * Retorna todas as turmas cadastradas no banco de dados.
     * @return Lista de turmas
     */
    public List<Turma> listarTodas() {
        return repository.findAll();
    }

    /**
     * Salva uma nova turma no banco de dados.
     * @param turma Objeto Turma a ser salvo
     * @return Turma salva
     */
    public Turma salvar(Turma turma) {
        return repository.save(turma);
    }

    /**
     * Atualiza uma turma existente pelo ID.
     * @param id Identificador da turma a ser atualizada
     * @param novaTurma Dados atualizados da turma
     * @return Turma atualizada
     */
    public Turma atualizar(@PathVariable Long id,  Turma novaTurma) {
        return repository.findById(id).map(c -> {
            c.setNome(novaTurma.getNome());
            c.setSemestre(novaTurma.getSemestre());
            c.setAtivo(novaTurma.isAtivo());
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    }

    /**
     * Exclui uma turma pelo ID.
     * @param id Identificador da turma a ser excluída
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}