package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Matricula;
import com.exemplo.crudmongo.repository.MatriculaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;


import java.util.Locale;
import java.util.concurrent.TimeUnit;
@Configuration
public class MatriculaDataLoader {

    @Bean
    CommandLineRunner loadMatriculaDatabase(MatriculaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 200; i++) {
                    Matricula matricula = new Matricula();
                    matricula.setPessoaId(faker.number().numberBetween(1, 100));
                    matricula.setCursoId(faker.number().numberBetween(1, 200));
                    matricula.setDataMatricula(faker.date().past(365, TimeUnit.DAYS));
                    matricula.setAtivo(faker.bool().bool());
                    repository.save(matricula);
                }

                System.out.println("✅ Banco de matrículas populado com 200 registros!");
            } else {
                System.out.println("ℹ️ Banco de matrículas já contém dados, não foi necessário repopular.");
            }
        };
    }



    
}
