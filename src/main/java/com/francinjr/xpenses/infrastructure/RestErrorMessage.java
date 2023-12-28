package com.francinjr.xpenses.infrastructure;

import org.springframework.http.HttpStatus;

public class RestErrorMessage {
	private HttpStatus status;
	private String mensagem;
	
	public RestErrorMessage(HttpStatus status, String mensagem) {
		setStatus(status);
		setMensagem(mensagem);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String menssagem) {
		this.mensagem = menssagem;
	}
	
	
}
