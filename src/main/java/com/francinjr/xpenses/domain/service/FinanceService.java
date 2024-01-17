package com.francinjr.xpenses.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.francinjr.xpenses.domain.exception.FinanceNotFoundException;
import com.francinjr.xpenses.domain.model.Finance;
import com.francinjr.xpenses.domain.repository.FinanceRepository;
import com.francinjr.xpenses.dto.FinanceDTO;

@Service
public class FinanceService {

	@Autowired
	private FinanceRepository financeRepository;

	public List<FinanceDTO> findAll() {
		List<Finance> finances = financeRepository.findAll();

		return finances.stream().map(finance -> new FinanceDTO(finance)).toList();
	}
	
	
	public FinanceDTO findById(Long financeId) {
		Finance finance = financeRepository.findById(financeId)
				.orElseThrow(() -> new FinanceNotFoundException(financeId));
		
		FinanceDTO financeDTO = new FinanceDTO(finance);
		return financeDTO;
	}
	
	
	@Transactional
	public FinanceDTO create(FinanceDTO financeDTO) throws Exception {
		Finance finance = new Finance(financeDTO);
		
		try {
			Finance createdFinance = financeRepository.save(finance);
			
			FinanceDTO createdFinanceDTO = new FinanceDTO(createdFinance);
			return createdFinanceDTO;
		} catch(Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}
	
	
	@Transactional
	public FinanceDTO update(FinanceDTO financeDTO) throws Exception {
		FinanceDTO foundFinanceDTO = findById(financeDTO.id());
		
		if(foundFinanceDTO != null) {
			Finance finance = new Finance(financeDTO);
			
			try {
				finance = financeRepository.save(finance);
			} catch(Exception exception) {
				throw new Exception(exception.getMessage());
			}
			
			FinanceDTO updatedFinance = new FinanceDTO(finance);
			return updatedFinance;
		} else {
			throw new FinanceNotFoundException(financeDTO.id());
		}
	}

	
	@Transactional
	public void delete(Long financeId) throws Exception {
		FinanceDTO foundFinanceDTO = findById(financeId);
		
		if(foundFinanceDTO != null) {
			try {
				financeRepository.deleteById(financeId);
			} catch(Exception exception) {
				new Exception(exception.getMessage());
			}
		} else {
			throw new FinanceNotFoundException(financeId);
		}
	}
}
