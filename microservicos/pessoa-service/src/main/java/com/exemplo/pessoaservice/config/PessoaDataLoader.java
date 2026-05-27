package com.exemplo.pessoaservice.config;

import com.exemplo.pessoaservice.model.Pessoa;
import com.exemplo.pessoaservice.repository.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PessoaDataLoader {

    @Bean
    CommandLineRunner carregarDados(PessoaRepository repository) {
        return args -> {
            repository.save(new Pessoa("Ana Silva", 28, "ana.silva@example.com", true));
            repository.save(new Pessoa("Carlos Souza", 33, "carlos.souza@example.com", true));
            repository.save(new Pessoa("Marina Lima", 22, "marina.lima@example.com", false));
            System.out.println("? [pessoa-service] Dados iniciais carregados.");
        };
    }
}
