package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class LibraryEventControllerAdvice {
	
	Logger logger = LoggerFactory.getLogger(LibraryEventControllerAdvice.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleRequestBody(MethodArgumentNotValidException ex){
		
		List<FieldError> errorList = ex.getBindingResult().getFieldErrors();
		
		String errString = errorList.stream()
				.map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage())
				.sorted()
				.collect(Collectors.joining(", "));
		
		logger.info("Error Message: {}", errString);
		
		return new ResponseEntity<>(errString, HttpStatus.BAD_REQUEST);
	}
}
