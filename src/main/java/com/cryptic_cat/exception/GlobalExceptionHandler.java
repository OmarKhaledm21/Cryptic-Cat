package com.cryptic_cat.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cryptic_cat.payload.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ InvalidUsernameOrPasswordException.class })
	public ResponseEntity<ErrorResponse> handleInvalidUsernameOrPasswordException(
			InvalidUsernameOrPasswordException exception) {

		ErrorResponse errorResponse = ErrorResponse.builder().message("Invalid username or password.")
				.errorCode(HttpStatus.UNAUTHORIZED.value()).status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
				.timestamp(LocalDateTime.now()) // Ensure you have imported LocalDateTime
				.build();

		return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	}

}
