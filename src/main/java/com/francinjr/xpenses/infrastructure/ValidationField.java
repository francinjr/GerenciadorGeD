package com.francinjr.xpenses.infrastructure;

public class ValidationField {
	private String name;
	private String message;
	
	public ValidationField(String name, String message) {
		setName(name);
		setMessage(message);
	}
	
	public ValidationField() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
