package com.cryptic_cat.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
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

		ErrorResponse errorResponse = ErrorResponse.builder()
				.message(exception.getMessage())
				.errorCode(HttpStatus.UNAUTHORIZED.value())
				.status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
				.timestamp(LocalDateTime.now())
				.build();

		return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	}
	
	 @ExceptionHandler(UserNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
	        ErrorResponse errorResponse = ErrorResponse.builder()
	                .message(exception.getMessage())
	                .errorCode(HttpStatus.NOT_FOUND.value())
	                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
	                .timestamp(LocalDateTime.now())
	                .build();
	        return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	    }
	 
	  @ExceptionHandler(DataIntegrityViolationException.class)
	    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
			String message = exception.getMessage();
			if (message.contains("user.username")) {
				message = "This username is already taken. Please choose a different username.";
			} else if (message.contains("user.email")) {
				message = "This email is already in use. Please choose a different email.";
			} else {
				message = "Invalid user object. Please try again.";
			}

			ErrorResponse errorResponse = ErrorResponse.builder().message(message)
					.errorCode(HttpStatus.BAD_REQUEST.value()).status(HttpStatus.BAD_REQUEST.getReasonPhrase())
					.timestamp(LocalDateTime.now()).build();
			return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
		}
	
}
