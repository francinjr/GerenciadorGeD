package com.francinjr.xpenses.integrationtests.swagger;

import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.francinjr.xpenses.configs.TestConfigs;
import com.francinjr.xpenses.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTest extends AbstractIntegrationTest {
	
	@Test
	void shouldDisplaySwaggerUIPage() {
		var content = 
		given()
			.basePath("/swagger-ui/index.html")
			.port(TestConfigs.SERVER_PORT)
			.when()
				.get()
			.then()
				.statusCode(200)
			.extract()
				.body()
					.asString();
		
		assertTrue(content.contains("Swagger UI"));
	}
}
