package com.francinjr.xpenses.domain.exception;

public class GanhoNaoEncontradoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public GanhoNaoEncontradoException(String message) {
		super(message);
	}
	
	public GanhoNaoEncontradoException(Long ganhoId) {
		this(String.format("Não existe um ganho com código %d", ganhoId));
	}
}
