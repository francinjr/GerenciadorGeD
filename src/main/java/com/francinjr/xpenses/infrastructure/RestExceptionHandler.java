package com.francinjr.xpenses.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.francinjr.xpenses.domain.exception.FinanceNotFoundException;
import com.francinjr.xpenses.domain.exception.InvalidFieldException;
import com.francinjr.xpenses.domain.exception.InvalidJwtAuthenticationException;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				null,
				"Erro",
				ex.getMessage(),
				null);
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FinanceNotFoundException.class)
	private ResponseEntity<ExceptionResponse> financeNotFoundHandler(FinanceNotFoundException exception) {
		ExceptionResponse threatResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(),
				null, "Recurso não encontrado", exception.getMessage(), null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
	}

	//@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<ValidationField> validationFields = new ArrayList<ValidationField>();

		for (FieldError error : exception.getBindingResult().getFieldErrors()) {
			validationFields.add(new ValidationField(error.getField(), error.getDefaultMessage()));
		}

		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), null,
				"Campos inválidos", "Há campos que não foram preenchidos corretamente", validationFields);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}
	
	@ExceptionHandler(InvalidFieldException.class)
	private ResponseEntity<ExceptionResponse> invalidFieldHandler(InvalidFieldException exception) {
		ExceptionResponse threatResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
				null, "A finança é nula", exception.getMessage(), null);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
	}

	
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationExceptions(
			Exception ex, WebRequest request) {
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				HttpStatus.FORBIDDEN.value(),
				null,
				"Falha ao autenticar",
				ex.getMessage(),
				null);
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
	}
}
