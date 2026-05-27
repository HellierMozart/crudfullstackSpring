package com.exemplo.cursoservice.service;

import com.exemplo.cursoservice.model.Curso;
import com.exemplo.cursoservice.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Camada de negócio do microserviço de Cursos.
 * Toda a lógica de negócio fica aqui - o controller só delega.
 */
@Service
public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    /** Lista todos os cursos */
    public List<Curso> listarTodos() {
        return repository.findAll();
    }

    /** Busca um curso pelo ID */
    public Optional<Curso> buscarPorId(Long id) {
        return repository.findById(id);
    }

    /** Busca cursos por nome */
    public List<Curso> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    /** Cria um novo curso */
    public Curso salvar(Curso curso) {
        return repository.save(curso);
    }

    /** Atualiza um curso existente */
    public Curso atualizar(Long id, Curso dados) {
        Curso existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com ID: " + id));
        
        existente.setNome(dados.getNome());
        existente.setCargaHoraria(dados.getCargaHoraria());
        existente.setAtivo(dados.isAtivo());
        
        return repository.save(existente);
    }

    /** Remove um curso (soft delete) */
    public void desativar(Long id) {
        Curso curso = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com ID: " + id));
        curso.setAtivo(false);
        repository.save(curso);
    }

    /** Deleta um curso permanentemente */
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Curso não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }
}
