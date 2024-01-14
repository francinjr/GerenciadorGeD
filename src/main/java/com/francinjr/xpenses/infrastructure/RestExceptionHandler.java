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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.francinjr.xpenses.domain.exception.GanhoNaoEncontradoException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(GanhoNaoEncontradoException.class)
	private ResponseEntity<ExceptionResponse> ganhoNaoEncontradoHandler(GanhoNaoEncontradoException exception) {
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

	/*
	 * protected ResponseEntity<Object>
	 * handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders
	 * headers, HttpStatusCode status, WebRequest request) {
	 * 
	 * List<RestErrorMessage> fieldErrors = new ArrayList<RestErrorMessage>();
	 * 
	 * for (ObjectError error : ex.getBindingResult().getAllErrors()) {
	 * fieldErrors.add(new RestErrorMessage(HttpStatus.BAD_REQUEST.value(),
	 * error.getDefaultMessage())); }
	 * 
	 * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrors); }
	 */

	/*
	 * protected ResponseEntity<Object> handleMethodArgumentNotValid(
	 * MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode
	 * status, WebRequest request) {
	 * 
	 * String message = ""; for(ObjectError error:
	 * ex.getBindingResult().getAllErrors()) { message = message +
	 * error.getDefaultMessage() + "\n"; } return handleExceptionInternal(ex,
	 * message, headers, status, request); }
	 */
	/*
	 * @ExceptionHandler(GanhoNaoEncontradoException.class) private
	 * ResponseEntity<String> ganhoNaoEncontradoHandler(GanhoNaoEncontradoException
	 * exception) { return
	 * ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ganho não encontrado"); }
	 */
}
