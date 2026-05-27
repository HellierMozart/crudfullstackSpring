package com.exemplo.cursoservice.config;

import com.exemplo.cursoservice.model.Curso;
import com.exemplo.cursoservice.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Carrega dados iniciais no banco H2 ao subir o microserviço.
 * Útil para testes e demonstrações.
 */
@Configuration
public class CursoDataLoader {

    @Bean
    CommandLineRunner carregarDados(CursoRepository repository) {
        return args -> {
            repository.save(new Curso("Java Avançado", 40, true));
            repository.save(new Curso("Spring Boot", 50, true));
            repository.save(new Curso("Microserviços", 60, true));
            repository.save(new Curso("Docker e Kubernetes", 45, false));
            System.out.println("✓ [curso-service] Dados iniciais carregados.");
        };
    }
}
