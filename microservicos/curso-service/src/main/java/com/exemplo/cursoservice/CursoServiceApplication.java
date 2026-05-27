package com.exemplo.cursoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * =====================================================
 * MICROSERVIÇO: curso-service
 * =====================================================
 *
 * Este é um serviço independente responsável APENAS
 * pelo gerenciamento de cursos.
 *
 * Para executar:
 *   mvn spring-boot:run
 *
 * Acesso:
 *   API:        http://localhost:8083/api/cursos
 *   H2 Console: http://localhost:8083/h2-console
 *
 * =====================================================
 */
@SpringBootApplication
public class CursoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CursoServiceApplication.class, args);
    }
}
