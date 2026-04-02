package com.exemplo.crudmongo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.exemplo.crudmongo.Model.Avaliacao;
import com.exemplo.crudmongo.repository.AvaliacaoRepository;

@Service
public class AvaliacaoService {
    private final AvaliacaoRepository repository; // Repositório para acesso ao banco de dados
    
    /**
     * Injeta o repositório PessoaRepository via construtor.
     */
    public AvaliacaoService(AvaliacaoRepository repository) {
        this.repository = repository;
    }


    /**
     * Retorna todas as pessoas cadastradas no banco de dados.
     * @return Lista de pessoas
     */
    public List<Avaliacao> listarTodas() {
        return repository.findAll();
    }

    /**
     * Salva uma nova pessoa no banco de dados.
     * @param avaliacao Objeto Pessoa a ser salvo
     * @return Pessoa salva
     */
    public Avaliacao salvar(Avaliacao avaliacao) {
        return repository.save(avaliacao);
    }

    /**
     * Atualiza uma pessoa existente pelo ID.
     * @param id Identificador da pessoa a ser atualizada
     * @param novaAvaliacao Dados atualizados da pessoa
     * @return Pessoa atualizada
     */
    public Avaliacao atualizar(@PathVariable Long id,  Avaliacao novaAvaliacao) {
        return repository.findById(id).map(a -> {
            a.setPessoaId(novaAvaliacao.getPessoaId());
            a.setDisciplinaId(novaAvaliacao.getDisciplinaId());
            a.setNota(novaAvaliacao.getNota());
            a.setData(novaAvaliacao.getData());
            a.setAtivo(novaAvaliacao.isAtivo());
            return repository.save(a);
        }).orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }

    /**
     * Exclui uma pessoa pelo ID.
     * @param id Identificador da pessoa a ser excluída
     */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}