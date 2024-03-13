package com.example.be8arm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.be8arm.global.config.security.CorsConfig;

@EnableJpaAuditing
@SpringBootApplication
@Import(CorsConfig.class)
public class Be8armApplication {

	public static void main(String[] args) {
		SpringApplication.run(Be8armApplication.class, args);
	}

}
