package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.repository.UsuarioRepository;
import com.exemplo.crudmongo.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioDataLoader {

    @Bean
    CommandLineRunner seedUsuarios(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        return args -> {
            if (usuarioRepository.findByUsername("professor").isEmpty()) {
                usuarioService.salvarUsuario("professor", "prof123", "PROFESSOR");
                System.out.println("? Usu�rio 'professor' criado (role: PROFESSOR)");
            }
            if (usuarioRepository.findByUsername("aluno").isEmpty()) {
                usuarioService.salvarUsuario("aluno", "passwordEncoder.encode(\"123\")", "ALUNO");
                System.out.println("? Usu�rio 'aluno' criado (role: ALUNO)");
            }
        };
    }
}
