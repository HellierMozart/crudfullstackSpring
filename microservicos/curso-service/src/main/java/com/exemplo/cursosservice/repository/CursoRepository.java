package com.exemplo.cursosservice.repository;

import com.exemplo.cursosservice.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByAtivo(boolean ativo);
}
