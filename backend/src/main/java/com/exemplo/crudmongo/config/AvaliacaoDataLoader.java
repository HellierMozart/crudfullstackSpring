package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Avaliacao;
import com.exemplo.crudmongo.repository.AvaliacaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.javafaker.Faker;


import java.util.Locale;
@Configuration
public class AvaliacaoDataLoader {

    @Bean
    CommandLineRunner loadAvaliacaoDatabase(AvaliacaoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i < 200; i++) {
                    Avaliacao avaliacao = new Avaliacao();
                    avaliacao.setNome(faker.educator().course());
                    avaliacao.setCargaHoraria(faker.number().numberBetween(20, 200));
                    repository.save(avaliacao);
                }

                System.out.println("✅ Banco de avaliações populado com 200 registros!");
            } else {
                System.out.println("ℹ️ Banco de avaliações já contém dados, não foi necessário repopular.");
            }
        };
    }



    
}
