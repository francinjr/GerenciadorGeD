package com.francinjr.xpenses.domain.exception;

public class InvalidFieldException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidFieldException(String message) {
		super(message);
	}
	
	public InvalidFieldException() {
		this("A descrição da finança não pode ter mais que 200 caracteres");
	}
}
