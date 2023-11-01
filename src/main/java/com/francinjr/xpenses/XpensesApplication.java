package com.francinjr.xpenses;

import org.springframework.boot.SpringApplication;	
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				contact = @Contact(
						name = "francinjr",
						email = "francinaldomanoel135@gmail.com",
						url = "https://github.com/francinjr"
					),

				description = "Gerenciador de ganhos e despesas",
				title = "Xpenses API - Gerenciador de ganhos e despesas",
				version = "1.0",
				license = @License(
						name = "licenseTest",
						url = "https://some.com"
				),
				termsOfService = "Terms"
		),
		servers = {
				@Server(
						description = "Local ENV",
						url = "http://localhost:8080"
						)
		}
)
public class XpensesApplication {

	public static void main(String[] args) {
		SpringApplication.run(XpensesApplication.class, args);
	}

}
