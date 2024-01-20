package com.francinjr.xpenses.domain.exception;

public class FinanceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FinanceNotFoundException(String message) {
		super(message);
	}
	
	public FinanceNotFoundException(String message, Long financeId) {
		this(String.format(message + "porque não existe uma finança com código %d", financeId));
	}
}
