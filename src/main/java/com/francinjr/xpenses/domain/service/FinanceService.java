package com.francinjr.xpenses.domain.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.francinjr.xpenses.controller.FinanceController;
import com.francinjr.xpenses.domain.exception.FinanceNotFoundException;
import com.francinjr.xpenses.domain.exception.InvalidFieldException;
import com.francinjr.xpenses.domain.model.Finance;
import com.francinjr.xpenses.domain.repository.FinanceRepository;
import com.francinjr.xpenses.dto.FinanceDTO;
import com.francinjr.xpenses.mapper.Mapper;

@Service
public class FinanceService {

	@Autowired
	private FinanceRepository financeRepository;

	public List<FinanceDTO> findAll() {
		List<FinanceDTO> finances = Mapper.parseListObjects(financeRepository.findAll(), FinanceDTO.class);

		finances.stream()
				.forEach(finance -> finance
						.add(linkTo(methodOn(FinanceController.class).findById(finance.getKey())).withSelfRel()));
		return finances;
	}

	public FinanceDTO findById(Long financeId) {
		Finance entity = financeRepository.findById(financeId)
				.orElseThrow(() -> new FinanceNotFoundException("Finança não encontrada, ", financeId));

		FinanceDTO financeDTO = Mapper.parseObject(entity, FinanceDTO.class);

		financeDTO.add(linkTo(methodOn(FinanceController.class).findById(financeId)).withSelfRel());
		return financeDTO;
	}

	@Transactional
	public FinanceDTO create(FinanceDTO finance) {
		if(finance.getDescription().length() > 200) {
			throw new InvalidFieldException();
		}
		
		Finance entity = Mapper.parseObject(finance, Finance.class);

		FinanceDTO dto = Mapper.parseObject(financeRepository.save(entity), FinanceDTO.class);
		dto.add(linkTo(methodOn(FinanceController.class).findById(dto.getKey())).withSelfRel());
		return dto;
	}

	@Transactional
	public FinanceDTO update(FinanceDTO finance) {
		if(finance.getDescription().length() > 200) {
			throw new InvalidFieldException();
		}
		
		Finance entity = financeRepository.findById(finance.getKey())
				.orElseThrow(() -> new FinanceNotFoundException("Não foi possível atualizar, ", finance.getKey()));

		entity = Mapper.parseObject(finance, Finance.class);

		FinanceDTO dto = Mapper.parseObject(financeRepository.save(entity), FinanceDTO.class);
		dto.add(linkTo(methodOn(FinanceController.class).findById(dto.getKey())).withSelfRel());
		return dto;
	}

	@Transactional
	public void delete(Long financeId) {
		Finance entity = financeRepository.findById(financeId)
				.orElseThrow(() -> new FinanceNotFoundException("Não foi possível deletar, ", financeId));

		financeRepository.delete(entity);
	}
}
