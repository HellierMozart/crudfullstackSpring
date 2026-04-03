package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
     * Retorna todas as Disciplinas cadastradas no banco de dados.
     * @return Lista de disciplinas
     */
    public List<Disciplina> listarTodas() {
        return repository.findAll();
    }

    /**
     * Salva uma nova Disciplina no banco de dados.
     * @param Disciplina Objeto Disciplina a ser salvo
     * @return Disciplina salva
     */
    public Disciplina salvar(Disciplina disciplina) {
        return repository.save(disciplina);
    }

    /**
     * Atualiza uma Disciplina existente pelo ID.
     * @param id Identificador da disciplina a ser atualizada
     * @param novaDisciplina Dados atualizados da disciplina
     * @return Disciplina atualizada
     */
    public Disciplina atualizar(@PathVariable Long id,  Disciplina novaDisciplina) {
        return repository.findById(id).map(c -> {
            c.setNome(novaDisciplina.getNome());
            c.setCargaHoraria(novaDisciplina.getCargaHoraria());
            c.setAtivo(novaDisciplina.isAtivo());
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Disciplina não encontrada"));
    }

    /**
     * Exclui uma disciplina pelo ID.
     * @param id Identificador da disciplina a ser excluída
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}