package br.com.conexasaude.desafio;

import br.com.conexasaude.desafio.model.MedicoModel;
import br.com.conexasaude.desafio.service.MedicoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication()
public class ConexasaudeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConexasaudeApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
