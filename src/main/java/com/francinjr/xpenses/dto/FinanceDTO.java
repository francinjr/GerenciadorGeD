package com.francinjr.xpenses.dto;

import com.francinjr.xpenses.domain.model.Finance;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FinanceDTO(@Size(min = 5, max = 30, message = "O nome deve ter entre 5 e 30 caracteres") String name,
		@NotNull(message = "O valor não pode ser nulo") Double value, String description, Long id) {

	public FinanceDTO(Finance finance) {
		this(finance.getName(), finance.getValue(), finance.getDescription(), finance.getId());
	}
}
