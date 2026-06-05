package com.portfolio.bleustudio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class BleuStudioApplication {

    public static void main(String[] args) {
        SpringApplication.run(BleuStudioApplication.class, args);
    }

}
