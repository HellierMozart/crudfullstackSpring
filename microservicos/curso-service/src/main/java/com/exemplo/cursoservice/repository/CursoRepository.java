package com.exemplo.cursoservice.repository;

import com.exemplo.cursoservice.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório JPA do microserviço de Cursos.
 * Herda todos os métodos CRUD de JpaRepository.
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Consulta customizada: buscar cursos por nome
    List<Curso> findByNomeContainingIgnoreCase(String nome);

    // Consulta customizada: buscar cursos ativos
    List<Curso> findByAtivo(boolean ativo);
}
