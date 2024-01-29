package com.iche.sco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoApplication.class, args);
    }

}
