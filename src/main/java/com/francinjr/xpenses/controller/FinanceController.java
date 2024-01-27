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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/finances/v1")
@Tag(name = "Finances", description = "Endpoint for Managing Finances")
public class FinanceController {

	@Autowired
	FinanceService financeService;

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds all finances", description = "Finds all finances", tags = {
			"Finances" }, responses = {
					@ApiResponse(description = "Sucess", responseCode = "200", content = {
							@Content(
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = FinanceDTO.class))
							)
							
					}),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<List<FinanceDTO>> findAll() {
		List<FinanceDTO> allFinances = financeService.findAll();
		return new ResponseEntity<List<FinanceDTO>>(allFinances, HttpStatus.OK);
	}

	@GetMapping(value = "/{financeId}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML })
	@Operation(summary = "Finds a finance", description = "Finds a finance", tags = {
	"Finances" }, responses = {
			@ApiResponse(description = "Sucess", responseCode = "200",
					content = @Content(schema = @Schema(implementation = FinanceDTO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
	})
	public ResponseEntity<FinanceDTO> findById(@PathVariable Long financeId) {
		FinanceDTO financeDTO = financeService.findById(financeId);
		return new ResponseEntity<FinanceDTO>(financeDTO, HttpStatus.OK);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML })
	@Operation(summary = "Adds a new finance", description = "Adds a new finance by passing in a JSON, XML or YML representation of the finance!", tags = {
	"Finances" }, responses = {
			@ApiResponse(description = "Created", responseCode = "201",
					content = @Content(schema = @Schema(implementation = FinanceDTO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
	})
	public ResponseEntity<FinanceDTO> create(@Valid @RequestBody FinanceDTO financeDTO) throws Exception {
		FinanceDTO createdFinance = financeService.create(financeDTO);
		return new ResponseEntity<FinanceDTO>(createdFinance, HttpStatus.CREATED);
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML })
	@Operation(summary = "Updates a finance", description = "Updates a finance by passing in a JSON, XML or YML representation of the finance!", tags = {
	"Finances" }, responses = {
			@ApiResponse(description = "Updated", responseCode = "200",
					content = @Content(schema = @Schema(implementation = FinanceDTO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
	})
	public ResponseEntity<FinanceDTO> update(@Valid @RequestBody FinanceDTO financeDTO) throws Exception {
		FinanceDTO updatedFinance = financeService.update(financeDTO);
		return new ResponseEntity<FinanceDTO>(updatedFinance, HttpStatus.OK);
	}

	@DeleteMapping("/{financeId}")
	@Operation(summary = "Deletes a finance", description = "Deletes a finance", tags = {
	"Finances" }, responses = {
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
	})
	public ResponseEntity<Void> delete(@PathVariable Long financeId) throws Exception {
		financeService.delete(financeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
