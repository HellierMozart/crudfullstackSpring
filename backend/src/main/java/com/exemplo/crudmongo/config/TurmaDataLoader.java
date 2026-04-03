package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Turma;
import com.exemplo.crudmongo.repository.TurmaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;


import java.util.Locale;
@Configuration
public class TurmaDataLoader {

    @Bean
    CommandLineRunner loadTurmaDatabase(TurmaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 200; i++) {
                    Turma turma = new Turma();
                    turma.setNome(faker.educator().course());
                    turma.setSemestre(faker.number().numberBetween(1, 10));
                    turma.setAtivo(faker.bool().bool());
                    repository.save(turma);
                }

                System.out.println("✅ Banco de turma populado com 200 registros!");
            } else {
                System.out.println("ℹ️ Banco de turma já contém dados, não foi necessário repopular.");
            }
        };
    }



    
}