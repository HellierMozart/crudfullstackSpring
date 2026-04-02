package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Disciplina;
import com.exemplo.crudmongo.repository.DisciplinaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;

import java.util.Locale;

@Configuration
public class DisciplinaDataLoader {

    @Bean
    CommandLineRunner loadDisciplinaDatabase(DisciplinaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 200; i++) {
                    Disciplina disciplina = new Disciplina();
                    disciplina.setNome(faker.name().fullName());
                    disciplina.setIdade(faker.number().numberBetween(18, 80));
                    disciplina.setEmail(faker.internet().emailAddress());
                    disciplina.setAtivo(faker.bool().bool());
                    repository.save(disciplina);
                }

                System.out.println("✅ Banco de disciplinas populado com 200 registros!");
            } else {
                System.out.println("ℹ️ Banco de disciplinas já contém dados, não foi necessário repopular.");
            }
        };
    }
}
