package com.francinjr.xpenses.domain.exception;

public class GanhoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GanhoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public GanhoNaoEncontradoException(Long ganhoId) {
		this(String.format("Não existe um cadastro de ganho com código %d", ganhoId));
	}
	
}
