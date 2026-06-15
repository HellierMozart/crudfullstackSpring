package com.exemplo.crudmongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.exemplo.crudmongo"})
public class CrudMongoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrudMongoApplication.class, args);
    }
}