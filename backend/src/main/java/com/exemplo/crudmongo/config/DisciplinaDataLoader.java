package com.exemplo.crudmongo.config;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exemplo.crudmongo.model.Disciplina;
import com.exemplo.crudmongo.repository.DisciplinaRepository;
import com.github.javafaker.Faker;

@Configuration
public class DisciplinaDataLoader
{
    @Bean
    CommandLineRunner loadDisciplinaDatabase(DisciplinaRepository repository)
    {
        return args -> {
            if (repository.count() == 0)
            {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 200; i++)
                {
                    Disciplina disciplina = new Disciplina();
                    disciplina.setNome(faker.educator().university());
                    disciplina.setCargaHoraria(faker.number().numberBetween(15, 80));
                    disciplina.setAtivo(faker.bool().bool());
                    repository.save(disciplina);
                }

                System.out.println("✅ Banco de disciplinas populado com 200 registros!");
            } 
            else 
            {
                System.out.println("ℹ️ Banco de disciplinas já contém dados, não foi necessário repopular.");
            }
        };
    }
}