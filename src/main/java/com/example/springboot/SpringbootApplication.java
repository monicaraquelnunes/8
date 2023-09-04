package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Annotation para mostra que a classe SpringbootApplication é a classe principal da aplicação
public class SpringbootApplication { // Iniciando a classe principal

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
