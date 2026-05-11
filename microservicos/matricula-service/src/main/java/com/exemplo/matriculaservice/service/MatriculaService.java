package com.exemplo.matriculaservice.service;

import com.exemplo.matriculaservice.model.Matricula;
import com.exemplo.matriculaservice.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Camada de negócio do microserviço de Matrículas.
 * Toda a lógica de negócio fica aqui ? o controller só delega.
 */
@Service
public class MatriculaService {

    private final MatriculaRepository repository;

    public MatriculaService(MatriculaRepository repository) {
        this.repository = repository;
    }

    /** Lista todas as matrículas */
    public List<Matricula> listarTodas() {
        return repository.findAll();
    }

    /** Busca uma matrícula pelo ID */
    public Optional<Matricula> buscarPorId(Long id) {
        return repository.findById(id);
    }

    /** Lista matrículas de uma pessoa específica */
    public List<Matricula> listarPorPessoa(Long pessoaId) {
        return repository.findByPessoaId(pessoaId);
    }

    /** Lista matrículas de um curso específico */
    public List<Matricula> listarPorCurso(Long cursoId) {
        return repository.findByCursoId(cursoId);
    }

    /** Cria uma nova matrícula */
    public Matricula salvar(Matricula matricula) {
        return repository.save(matricula);
    }

    /** Atualiza uma matrícula existente */
    public Matricula atualizar(Long id, Matricula dados) {
        Matricula existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada: " + id));
        existente.setPessoaId(dados.getPessoaId());
        existente.setCursoId(dados.getCursoId());
        existente.setDataMatricula(dados.getDataMatricula());
        existente.setAtivo(dados.isAtivo());
        return repository.save(existente);
    }

    /** Desativa uma matrícula (soft delete) */
    public void desativar(Long id) {
        Matricula existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada: " + id));
        existente.setAtivo(false);
        repository.save(existente);
    }

    /** Remove permanentemente uma matrícula */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
