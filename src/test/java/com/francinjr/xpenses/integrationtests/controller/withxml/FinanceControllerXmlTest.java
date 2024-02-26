package com.francinjr.xpenses.integrationtests.controller.withxml;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.francinjr.xpenses.configs.TestConfigs;
import com.francinjr.xpenses.domain.model.FinanceType;
import com.francinjr.xpenses.dto.security.TokenDTO;
import com.francinjr.xpenses.integrationtests.dto.AccountCredentialsDTO;
import com.francinjr.xpenses.integrationtests.dto.FinanceDTO;
import com.francinjr.xpenses.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class FinanceControllerXmlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static XmlMapper objectMapper;
	private static FinanceDTO finance;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new XmlMapper();
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
					.contentType(TestConfigs.CONTENT_TYPE_XML)
					.accept(TestConfigs.CONTENT_TYPE_XML)
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
		.contentType(TestConfigs.CONTENT_TYPE_XML)
		.accept(TestConfigs.CONTENT_TYPE_XML)
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
		
		assertEquals("Pastel", persistedFinance.getName());
		assertEquals("Pastel comprado no lanche", persistedFinance.getDescription());
		assertEquals(5.75, persistedFinance.getValue());
		assertEquals(FinanceType.EXPENSE, persistedFinance.getType());
	}
	
	
	@Test
	@Order(2)
	void testUpdate() throws JsonMappingException, JsonProcessingException {
		finance.setDescription("Pastel de Frango comprado no lanche");
		var content = 
		given().spec(specification)
		.contentType(TestConfigs.CONTENT_TYPE_XML)
		.accept(TestConfigs.CONTENT_TYPE_XML)
			.body(finance)
			.when()
			.put()
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
		
		assertEquals(finance.getId(), persistedFinance.getId());
		
		assertEquals("Pastel", persistedFinance.getName());
		assertEquals("Pastel de Frango comprado no lanche", persistedFinance.getDescription());
		assertEquals(5.75, persistedFinance.getValue());
		assertEquals(FinanceType.EXPENSE, persistedFinance.getType());
	}


	
	
	@Test
	@Order(3)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		mockFinance();
		
		var content = 
		given().spec(specification)
		.contentType(TestConfigs.CONTENT_TYPE_XML)
		.accept(TestConfigs.CONTENT_TYPE_XML)
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
		
		assertEquals(finance.getId(), persistedFinance.getId());
		
		assertEquals("Pastel", persistedFinance.getName());
		assertEquals("Pastel de Frango comprado no lanche", persistedFinance.getDescription());
		assertEquals(5.75, persistedFinance.getValue());
		assertEquals(FinanceType.EXPENSE, persistedFinance.getType());
	}
	
	
	@Test
	@Order(4)
	void testDelete() throws JsonMappingException, JsonProcessingException {
		
		given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_XML)
			.accept(TestConfigs.CONTENT_TYPE_XML)
			.pathParam("id", finance.getId())
			.when()
			.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	
	@Test
	@Order(5)
	void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = 
		given().spec(specification)
		.contentType(TestConfigs.CONTENT_TYPE_XML)
		.accept(TestConfigs.CONTENT_TYPE_XML)
			.when()
			.get()
			.then()
				.statusCode(200)
			.extract()
				.body()
				.asString();
		
		List<FinanceDTO> finances = objectMapper.readValue(content, new TypeReference<List<FinanceDTO>>() {});
		
		FinanceDTO foundFinanceOne = finances.get(0);
		finance = foundFinanceOne;
		
		assertNotNull(foundFinanceOne.getName());
		assertNotNull(foundFinanceOne.getDescription());
		assertNotNull(foundFinanceOne.getValue());
		//assertNotNull(foundFinanceOne.getType());
		
		assertEquals(1, foundFinanceOne.getId());
		
		assertEquals("Cafézinho sinistro", foundFinanceOne.getName());
		assertEquals("Cafézinho da tarde", foundFinanceOne.getDescription());
		assertEquals(2.50, foundFinanceOne.getValue());
		//assertEquals(FinanceType.EXPENSE, foundFinanceOne.getType());
		
		
		
		FinanceDTO foundFinanceTwo = finances.get(1);
		finance = foundFinanceTwo;
		
		assertNotNull(foundFinanceTwo.getName());
		assertNotNull(foundFinanceTwo.getDescription());
		assertNotNull(foundFinanceTwo.getValue());
		//assertNotNull(foundFinanceTwo.getType());
		
		assertEquals(2, foundFinanceTwo.getId());
		
		assertEquals("Lanche bom", foundFinanceTwo.getName());
		assertEquals("Lancinho da tarde", foundFinanceTwo.getDescription());
		assertEquals(3.75, foundFinanceTwo.getValue());
		//assertEquals(FinanceType.EXPENSE, foundFinanceOne.getType());
	}
	
	
	@Test
	@Order(6)
	void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
		.setBasePath("/api/finances/v1")
		.setPort(TestConfigs.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
		.build();
		
		
		given().spec(specificationWithoutToken)
		.contentType(TestConfigs.CONTENT_TYPE_XML)
		.accept(TestConfigs.CONTENT_TYPE_XML)
			.when()
			.get()
			.then()
				.statusCode(403);
	}
	
	
	private void mockFinance() {
		finance.setName("Pastel");
		finance.setDescription("Pastel comprado no lanche");
		finance.setValue(5.75);
		finance.setType(FinanceType.EXPENSE);
	}
}

