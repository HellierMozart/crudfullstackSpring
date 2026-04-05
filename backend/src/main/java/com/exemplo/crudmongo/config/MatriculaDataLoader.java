package com.exemplo.crudmongo.config;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exemplo.crudmongo.model.Matricula;
import com.exemplo.crudmongo.repository.MatriculaRepository;
import com.github.javafaker.Faker;

@Configuration
public class MatriculaDataLoader 
{
    @Bean
    CommandLineRunner loadMatriculaDatabase(MatriculaRepository repository)
    {
        return args -> {
            if (repository.count() == 0)
            {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i <= 200; i++)
                {
                    Matricula matricula = new Matricula();
                    matricula.setPessoa(faker.number().numberBetween(1, 200));
                    matricula.setCurso(faker.number().numberBetween(1, 200));
                    matricula.setDataMatricula(faker.date().past(30, null));
                    matricula.setAtivo(faker.bool().bool());
                }

               System.out.println("✅ Banco de matricula populado com 200 registros!");
            } else {
                System.out.println("ℹ️ Banco de matricula já contém dados, não foi necessário repopular.");
            }
        }
    }
}
