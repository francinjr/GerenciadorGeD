package com.francinjr.xpenses.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.francinjr.xpenses.domain.exception.GanhoNaoEncontradoException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(GanhoNaoEncontradoException.class)
	private ResponseEntity<RestErrorMessage> ganhoNaoEncontradoHandler(GanhoNaoEncontradoException exception) {
		RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
	}
	/*@ExceptionHandler(GanhoNaoEncontradoException.class)
	private ResponseEntity<String> ganhoNaoEncontradoHandler(GanhoNaoEncontradoException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ganho não encontrado");
	}*/
}
