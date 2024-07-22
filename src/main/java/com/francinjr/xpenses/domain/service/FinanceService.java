package com.francinjr.xpenses.domain.service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.francinjr.xpenses.controller.FinanceController;
import com.francinjr.xpenses.domain.exception.FinanceNotFoundException;
import com.francinjr.xpenses.domain.exception.InvalidFieldException;
import com.francinjr.xpenses.domain.model.Finance;
import com.francinjr.xpenses.domain.repository.FinanceRepository;
import com.francinjr.xpenses.dto.finance.FinanceDTO;
import com.francinjr.xpenses.dto.finance.UpdateFinanceDescriptionDTO;
import com.francinjr.xpenses.mapper.Mapper;

@Service
public class FinanceService {

	@Autowired
	private FinanceRepository financeRepository;
	
	@Autowired
	PagedResourcesAssembler<FinanceDTO> assembler;

	public PagedModel<EntityModel<FinanceDTO>> findAll(Pageable pageable) {
		
		var financePage = financeRepository.findAll(pageable);
		
		var financeDTOsPage = financePage.map(f -> Mapper.parseObject(f, FinanceDTO.class));
		
		financeDTOsPage.map(
				f -> f.add(
						linkTo(methodOn(FinanceController.class)
								.findById(f.getKey())).withSelfRel()));
		
		Link link = linkTo(
				methodOn(FinanceController.class)
					.findAll(pageable.getPageNumber(),
							pageable.getPageSize(),
							"asc")).withSelfRel();
		return assembler.toModel(financeDTOsPage, link);
	}


	public PagedModel<EntityModel<FinanceDTO>> findFinancesByName(String name, Pageable pageable) {

		var financePage = financeRepository.findFinancesByName(name, pageable);

		var financeDTOsPage = financePage.map(f -> Mapper.parseObject(f, FinanceDTO.class));

		financeDTOsPage.map(
				f -> f.add(
						linkTo(methodOn(FinanceController.class)
								.findById(f.getKey())).withSelfRel()));

		Link link = linkTo(
				methodOn(FinanceController.class)
						.findAll(pageable.getPageNumber(),
								pageable.getPageSize(),
								"asc")).withSelfRel();
		return assembler.toModel(financeDTOsPage, link);
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
	public FinanceDTO updateDescription(UpdateFinanceDescriptionDTO finance) {
		if(finance.getDescription().length() > 200) {
			throw new InvalidFieldException();
		}
		
		Finance entity = financeRepository.findById(finance.getKey())
				.orElseThrow(() -> new FinanceNotFoundException("Não foi possível atualizar, ", finance.getKey()));

		entity.setId(finance.getKey());
		entity.setDescription(finance.getDescription());

		financeRepository.updateDescription(entity.getDescription(), entity.getId());
		
		Finance updatedFinance = financeRepository.findById(entity.getId())
				.orElseThrow(() -> new FinanceNotFoundException("Não foi possível atualizar, ", entity.getId()));
		
		FinanceDTO dto = Mapper.parseObject(updatedFinance, FinanceDTO.class);
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
