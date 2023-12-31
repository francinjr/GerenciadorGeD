package com.francinjr.xpenses.infrastructure;

import org.springframework.http.HttpStatus;

public class RestErrorMessage {
	private HttpStatus status;
	private String message;
	
	public RestErrorMessage(HttpStatus status, String message) {
		setStatus(status);
		setMessage(message);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
