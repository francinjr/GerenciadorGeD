package com.francinjr.xpenses.integrationtests.controller.cors.withjson;

import static io.restassured.RestAssured.given;	
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.francinjr.xpenses.configs.TestConfigs;
import com.francinjr.xpenses.domain.model.FinanceType;
import com.francinjr.xpenses.integrationtests.dto.AccountCredentialsDTO;
import com.francinjr.xpenses.integrationtests.dto.FinanceDTO;
import com.francinjr.xpenses.integrationtests.dto.TokenDTO;
import com.francinjr.xpenses.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class FinanceControllerCorsJsonTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static FinanceDTO finance;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		finance = new FinanceDTO();
	}
	
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsDTO user = new AccountCredentialsDTO("francin", "admin123");
		
		var accessToken = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
								.as(TokenDTO.class)
							.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/finances/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	
	@Test
	@Order(1)
	void testCreate() throws JsonMappingException, JsonProcessingException {
		mockFinance();
		
		var content = 
		given().spec(specification)
		.contentType(TestConfigs.CONTENT_TYPE_JSON)
		.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_XPENSES)
			.body(finance)
			.when()
			.post()
			.then()
				.statusCode(201)
			.extract()
				.body()
					.asString();
		
		FinanceDTO persistedFinance = objectMapper.readValue(content, FinanceDTO.class);
		finance = persistedFinance;
		
		assertNotNull(persistedFinance);
		assertNotNull(persistedFinance.getName());
		assertNotNull(persistedFinance.getDescription());
		assertNotNull(persistedFinance.getValue());
		assertNotNull(persistedFinance.getType());
		
		assertTrue(persistedFinance.getId() > 0);
		
		assertEquals("Coxinha", persistedFinance.getName());
		assertEquals("Coxinha que comprei no lanche da tarde", persistedFinance.getDescription());
		assertEquals(4.50, persistedFinance.getValue());
		assertEquals(FinanceType.EXPENSE, persistedFinance.getType());
	}


	
	@Test
	@Order(2)
	void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockFinance();
		
		var content = 
		given().spec(specification)
		.contentType(TestConfigs.CONTENT_TYPE_JSON)
		.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_YTEST)
				.body(finance)
				.when()
				.post()
				.then()
				.statusCode(403)
				.extract()
				.body()
				.asString();
		
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	
	@Test
	@Order(3)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		mockFinance();
		
		var content = 
		given().spec(specification)
		.contentType(TestConfigs.CONTENT_TYPE_JSON)
		.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_XPENSES)
			.pathParam("id", finance.getId())
			.when()
			.get("{id}")
			.then()
				.statusCode(200)
			.extract()
				.body()
					.asString();
		
		FinanceDTO persistedFinance = objectMapper.readValue(content, FinanceDTO.class);
		finance = persistedFinance;
		
		assertNotNull(persistedFinance);
		assertNotNull(persistedFinance.getName());
		assertNotNull(persistedFinance.getDescription());
		assertNotNull(persistedFinance.getValue());
		assertNotNull(persistedFinance.getType());
		
		assertTrue(persistedFinance.getId() > 0);
		
		assertEquals("Coxinha", persistedFinance.getName());
		assertEquals("Coxinha que comprei no lanche da tarde", persistedFinance.getDescription());
		assertEquals(4.50, persistedFinance.getValue());
		assertEquals(FinanceType.EXPENSE, persistedFinance.getType());
	}
	
	
	@Test
	@Order(4)
	void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockFinance();
		
		var content = 
		given().spec(specification)
		.contentType(TestConfigs.CONTENT_TYPE_JSON)
		.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_YTEST)
			.pathParam("id", finance.getId())
			.when()
			.get("{id}")
			.then()
				.statusCode(403)
			.extract()
				.body()
					.asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	
	
	private void mockFinance() {
		finance.setName("Coxinha");
		finance.setDescription("Coxinha que comprei no lanche da tarde");
		finance.setValue(4.50);
		finance.setType(FinanceType.EXPENSE);
	}
}
