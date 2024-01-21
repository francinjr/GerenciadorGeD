package com.francinjr.xpenses.unittests.mockito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.francinjr.xpenses.domain.exception.InvalidFieldException;
import com.francinjr.xpenses.domain.model.Finance;
import com.francinjr.xpenses.domain.repository.FinanceRepository;
import com.francinjr.xpenses.domain.service.FinanceService;
import com.francinjr.xpenses.dto.FinanceDTO;
import com.francinjr.xpenses.unittests.mapper.mocks.MockFinance;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class FinanceServiceTest {

	MockFinance input;
	
	@InjectMocks
	private FinanceService financeService;
	
	@Mock
	FinanceRepository financeRepository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockFinance();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		List<Finance> list = input.mockEntityList();
		
		when(financeRepository.findAll()).thenReturn(list);
		
		var finances = financeService.findAll();
		assertNotNull(finances);
		assertEquals(14, finances.size());
		
		
		var financeOne = finances.get(1);

		assertNotNull(financeOne);
		assertNotNull(financeOne.getKey());
		assertNotNull(financeOne.getLinks());

		assertTrue(financeOne.toString().contains("links: [</api/finances/1>;rel=\"self\"]"));
		
		assertEquals("Name Test1", financeOne.getName());
		assertEquals((Double) 25.75, financeOne.getValue());
		assertEquals("Description Test1", financeOne.getDescription());
		assertEquals("Ganho", financeOne.getType().getTypeDescription());
		
		
		var financeThree = finances.get(3);
		
		assertNotNull(financeThree);
		assertNotNull(financeThree.getKey());
		assertNotNull(financeThree.getLinks());
		
		assertTrue(financeThree.toString().contains("links: [</api/finances/3>;rel=\"self\"]"));
		
		assertEquals("Name Test3", financeThree.getName());
		assertEquals((Double) 25.75, financeThree.getValue());
		assertEquals("Description Test3", financeThree.getDescription());
		assertEquals("Ganho", financeThree.getType().getTypeDescription());
		
		
		
		var financeNine = finances.get(9);
		
		assertNotNull(financeNine);
		assertNotNull(financeNine.getKey());
		assertNotNull(financeNine.getLinks());
		
		assertTrue(financeNine.toString().contains("links: [</api/finances/9>;rel=\"self\"]"));
		
		assertEquals("Name Test9", financeNine.getName());
		assertEquals((Double) 25.75, financeNine.getValue());
		assertEquals("Description Test9", financeNine.getDescription());
		assertEquals("Ganho", financeNine.getType().getTypeDescription());
	}

	@Test
	void testFindById() {
		Finance entity = input.mockEntity(21);
		entity.setId(21L);
		
		when(financeRepository.findById(21L)).thenReturn(Optional.of(entity));
		
		var result = financeService.findById(21L);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		System.out.println(result.toString());
		assertTrue(result.toString().contains("links: [</api/finances/21>;rel=\"self\"]"));
		assertEquals("Name Test21", result.getName());
		assertEquals((Double) 25.75, result.getValue());
		assertEquals("Description Test21", result.getDescription());
		assertEquals("Ganho", result.getType().getTypeDescription());
		//assertEquals("2024-01-20T15:30:45.123456789", result.getPaiday());
		// criar o servico de salvar a data e depois terminar aqui
	}

	@Test
	void testCreate() {
		Finance entity = input.mockEntity(21);

		Finance persisted = entity;
		persisted.setId(21L);

		FinanceDTO dto = input.mockVO(21);
		dto.setKey(21L);

		when(financeRepository.save(entity)).thenReturn(persisted);


		var result = financeService.create(dto);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/finances/21>;rel=\"self\"]"));
		
		assertEquals("Name Test21", result.getName());
		assertEquals((Double) 25.75, result.getValue());
		assertEquals("Description Test21", result.getDescription());
		assertEquals("Ganho", result.getType().getTypeDescription());
		//assertEquals("2024-01-20T15:30:45.123456789", result.getPaiday());
		// criar o servico de salvar a data e depois terminar aqui
	}
	
	
	@Test
	void testCreateWithInvalidDescription() {
		FinanceDTO finance = new FinanceDTO();
		String descriptionTest = "abcdefgh";
		for(int i = 0; i < 5; i++) {
			descriptionTest = descriptionTest + descriptionTest;
		}
		finance.setDescription(descriptionTest);
		System.out.println("Tamanho de description: " + finance.getDescription().length());
		Exception exception = assertThrows(InvalidFieldException.class, () -> {
			financeService.create(finance);
		});
		
		String expectedMessage = "A descrição da finança não pode ter mais que 200 caracteres";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	

	@Test
	void testUpdate() {
		Finance entity = input.mockEntity(21);
		entity.setId(21L);
		
		Finance persisted = entity;
		persisted.setId(21L);

		FinanceDTO dto = input.mockVO(21);
		dto.setKey(21L);

		when(financeRepository.findById(21L)).thenReturn(Optional.of(entity));
		
		when(financeRepository.save(entity)).thenReturn(persisted);


		var result = financeService.update(dto);

		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());

		assertTrue(result.toString().contains("links: [</api/finances/21>;rel=\"self\"]"));
		
		assertEquals("Name Test21", result.getName());
		assertEquals((Double) 25.75, result.getValue());
		assertEquals("Description Test21", result.getDescription());
		assertEquals("Ganho", result.getType().getTypeDescription());
		//assertEquals("2024-01-20T15:30:45.123456789", result.getPaiday());
		// criar o servico de salvar a data e depois terminar aqui
	}
	
	@Test
	void testUpdateWithInvalidDescription() {
		FinanceDTO finance = new FinanceDTO();
		String descriptionTest = "abcdefgh";
		for(int i = 0; i < 5; i++) {
			descriptionTest = descriptionTest + descriptionTest;
		}
		
		finance.setDescription(descriptionTest);
		System.out.println("Tamanho de description: " + finance.getDescription().length());
		Exception exception = assertThrows(InvalidFieldException.class, () -> {
			financeService.create(finance);
		});
		
		String expectedMessage = "A descrição da finança não pode ter mais que 200 caracteres";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testDelete() {
		Finance entity = input.mockEntity(21);
		entity.setId(21L);
		
		when(financeRepository.findById(21L)).thenReturn(Optional.of(entity));
		
		financeService.delete(21L);
	}

}
