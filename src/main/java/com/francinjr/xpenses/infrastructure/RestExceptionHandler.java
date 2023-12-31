package com.francinjr.xpenses.infrastructure;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.francinjr.xpenses.domain.exception.GanhoNaoEncontradoException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(GanhoNaoEncontradoException.class)
	private ResponseEntity<RestErrorMessage> ganhoNaoEncontradoHandler(GanhoNaoEncontradoException exception) {
		RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
	}
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String message = "";
		for(ObjectError error: ex.getBindingResult().getAllErrors()) {
			message = message + error.getDefaultMessage() + "\n";
		}
		return handleExceptionInternal(ex, message, headers, status, request);
	}
	/*@ExceptionHandler(GanhoNaoEncontradoException.class)
	private ResponseEntity<String> ganhoNaoEncontradoHandler(GanhoNaoEncontradoException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ganho não encontrado");
	}*/
}
