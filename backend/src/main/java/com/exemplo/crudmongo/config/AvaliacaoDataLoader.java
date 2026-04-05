package com.exemplo.crudmongo.config;

import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exemplo.crudmongo.model.Avaliacao;
import com.exemplo.crudmongo.repository.AvaliacaoRepository;
import com.github.javafaker.Faker;

@Configuration
public class AvaliacaoDataLoader {
    @Bean
    CommandLineRunner loadAvaliacaoDatabase(AvaliacaoRepository repository)
    {
        return args -> {
            if (repository.count() == 0)
            {
                Faker faker = new Faker(new Locale("pt-BR"));

                for (int i = 0; i <= 200; i++)
                {
                    Avaliacao avaliacao = new Avaliacao();
                    avaliacao.setPessoa(null);
                    avaliacao.setDisciplina(null);
                    avaliacao.setNota(faker.number().numberBetween(1, 10));
                    avaliacao.setData(null);
                    avaliacao.setAtivo(faker.bool().bool());
                }
                
                System.out.println("✅ Banco de avaliacao populado com 200 registros!");
            }
            else
            {
                System.out.println("ℹ️ Banco de cursos já contém dados, não foi necessário repopular.");
            }
        };
    }
}
