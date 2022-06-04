package com.lrng.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lrng.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {

		String message = exception.toStringCustom();
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, null, false), HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// return super.handleHttpRequestMethodNotSupported(ex, headers, status,
		// request);
		return new ResponseEntity<Object>(new ApiResponse(ex.getMessage(), null, false), status);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<Object, Object> responseMap = new HashMap<Object, Object>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String field = ((FieldError) error).getField();
			String deaultMessage = ((FieldError) error).getDefaultMessage();
			responseMap.put(field, deaultMessage);
		});
		return new ResponseEntity<Object>(new ApiResponse(null, responseMap, false), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = PropertyReferenceException.class)
	public ResponseEntity<ApiResponse> handlePropertyReferenceException(PropertyReferenceException exception) {

		String message = exception.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, null, false), HttpStatus.BAD_REQUEST);
	}
	
}
