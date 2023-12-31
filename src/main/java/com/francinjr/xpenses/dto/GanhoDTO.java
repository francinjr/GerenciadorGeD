package com.francinjr.xpenses.dto;

import com.francinjr.xpenses.domain.model.Ganho;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GanhoDTO(@Size(min = 5, max = 30, message = "O nome deve ter entre 5 e 30 caracteres") String nome,
		@NotNull(message = "O valor não pode ser nulo") Double valor, String descricao, Long id) {

	public GanhoDTO(Ganho ganho) {
		this(ganho.getNome(), ganho.getValor(), ganho.getDescricao(), ganho.getId());
	}
}
