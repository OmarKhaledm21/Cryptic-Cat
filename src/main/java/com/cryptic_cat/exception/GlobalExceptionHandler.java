package com.cryptic_cat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({InvalidUsernameOrPasswordException.class})
    public ResponseEntity<Object> handleInvalidUsernameOrPasswordException(InvalidUsernameOrPasswordException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }
	
}
