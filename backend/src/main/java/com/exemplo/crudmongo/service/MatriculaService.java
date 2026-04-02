package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.exemplo.crudmongo.Model.Matricula;
import com.exemplo.crudmongo.repository.MatriculaRepository;

@Service
public class MatriculaService {
    private final MatriculaRepository repository; // Repositório para acesso ao banco de dados
    
    /**
     * Injeta o repositório PessoaRepository via construtor.
     */
    public MatriculaService(MatriculaRepository repository) {
        this.repository = repository;
    }


    /**
     * Retorna todas as pessoas cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Matricula> listarTodas() {
        return repository.findAll();
    }

    /**
     * Salva uma nova pessoa no banco de dados.
     * @param matricula Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public Matricula salvar(Matricula matricula) {
        return repository.save(matricula);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     * @param id Identificador da pessoa a ser atualizada
     * @param novaMatricula Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    public Matricula atualizar(@PathVariable Long id,  Matricula novaMatricula) {
        return repository.findById(id).map(m -> {
            m.setPessoaId(novaMatricula.getPessoaId());
            m.setCursoId(novaMatricula.getCursoId());
            m.setDataMatricula(novaMatricula.getDataMatricula());
            m.setAtivo(novaMatricula.isAtivo());
            return repository.save(m);
        }).orElseThrow(() -> new RuntimeException("Matrícula não encontrada"));
    }

    /**
     * Exclui uma pessoa pelo ID.
     * @param id Identificador da pessoa a ser excluída
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}