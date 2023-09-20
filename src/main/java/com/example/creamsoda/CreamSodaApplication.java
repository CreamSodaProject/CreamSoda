package com.example.creamsoda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CreamSodaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreamSodaApplication.class, args);
    }

}
