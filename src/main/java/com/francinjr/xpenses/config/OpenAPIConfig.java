package com.francinjr.xpenses.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {
	
	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Gerenciador de Finanças")
				.version("v1")
				.description("API para gerenciamento de ganhos e despesas")
				.contact(
						new Contact()
						.name("Francinaldo Manoel")
						.email("francinaldomanoel135@gmail.com")
						.url("https://github.com/francinjr")
						)
				.termsOfService("Termos")
				.license(
					new License()
						.name("Apache 2.0")
						.url("URL")
					)
				);
	}

}
