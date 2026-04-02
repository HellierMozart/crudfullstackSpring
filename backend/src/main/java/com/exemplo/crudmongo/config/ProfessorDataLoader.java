package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Professor;
import com.exemplo.crudmongo.repository.ProfessorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;

import java.util.Locale;

@Configuration
public class ProfessorDataLoader {

    @Bean
    CommandLineRunner loadProfessorDatabase(ProfessorRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 200; i++) {
                    Professor professor = new Professor();
                    professor.setNome(faker.name().fullName());
                    professor.setEspecialidade(faker.number().numberBetween(18, 80));
                    professor.setEmail(faker.internet().emailAddress());
                    professor.setAtivo(faker.bool().bool());
                    repository.save(professor);
                }

                System.out.println("✅ Banco de professores populado com 200 registros!");
            } else {
                System.out.println("ℹ️ Banco de professores já contém dados, não foi necessário repopular.");
            }
        };
    }
}

