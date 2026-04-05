package com.exemplo.crudmongo.config;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exemplo.crudmongo.model.Turma;
import com.exemplo.crudmongo.repository.TurmaRepository;
import com.github.javafaker.Faker;

@Configuration
public class TurmaDataLoader {
    @Bean
    CommandLineRunner loadTurmaDatabase(TurmaRepository repository)
    {
        return args -> {
            if (repository.count() == 0)
            {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i <= 200; i++)
                {
                    Turma turma = new Turma();
                    turma.setNome(faker.educator().course() + " - " + faker.options().option("M", "V", "N"));
                    turma.setAno(String.format("%d.%d", faker.number().numberBetween(2024, 2026), faker.number().numberBetween(1, 3)));
                    turma.setAtivo(faker.bool().bool());
                }

               System.out.println("✅ Banco de turma populado com 200 registros!");
            } else {
                System.out.println("ℹ️ Banco de turma já contém dados, não foi necessário repopular.");
            }
        };
    }
}
