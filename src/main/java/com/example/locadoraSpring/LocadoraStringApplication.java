package com.example.locadoraSpring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Locadora",
        version = "1.0",
        description = "API para gerenciamento de locadora de veiculos")
    )

public class LocadoraStringApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocadoraStringApplication.class, args);
    }

}
