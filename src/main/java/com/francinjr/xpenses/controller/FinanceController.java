package com.francinjr.xpenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.francinjr.xpenses.domain.service.FinanceService;
import com.francinjr.xpenses.dto.FinanceDTO;
import com.francinjr.xpenses.util.MediaType;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/finances")
public class FinanceController {

	@Autowired
	FinanceService financeService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML })
	public ResponseEntity<List<FinanceDTO>> findAll() {
		List<FinanceDTO> allFinances = financeService.findAll();
		return new ResponseEntity<List<FinanceDTO>>(allFinances, HttpStatus.OK);
	}

	@GetMapping(value = "/{financeId}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML })
	public ResponseEntity<FinanceDTO> findById(@PathVariable Long financeId) {
		FinanceDTO financeDTO = financeService.findById(financeId);
		return new ResponseEntity<FinanceDTO>(financeDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML })
	public ResponseEntity<FinanceDTO> create(@Valid @RequestBody FinanceDTO financeDTO)
			throws Exception {
		FinanceDTO createdFinance = financeService.create(financeDTO);
		return new ResponseEntity<FinanceDTO>(createdFinance, HttpStatus.CREATED);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML })
	public ResponseEntity<FinanceDTO> update(@Valid @RequestBody FinanceDTO financeDTO) throws Exception {
		FinanceDTO updatedFinance = financeService.update(financeDTO);
		return new ResponseEntity<FinanceDTO>(updatedFinance, HttpStatus.OK);
	}

	@DeleteMapping("/{financeId}")
	public ResponseEntity<Void> delete(@PathVariable Long financeId) throws Exception {
		financeService.delete(financeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
