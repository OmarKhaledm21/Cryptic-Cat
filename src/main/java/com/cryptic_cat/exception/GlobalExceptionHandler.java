package com.cryptic_cat.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cryptic_cat.payload.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({ InvalidUsernameOrPasswordException.class })
	public ResponseEntity<ErrorResponse> handleInvalidUsernameOrPasswordException(
			InvalidUsernameOrPasswordException exception) {

		ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage())
				.errorCode(HttpStatus.UNAUTHORIZED.value()).status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
				.timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	}

	@ExceptionHandler({ InvalidTokenException.class })
	public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException exception) {
		ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage())
				.errorCode(HttpStatus.UNAUTHORIZED.value()).status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
				.timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
		ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage())
				.errorCode(HttpStatus.NOT_FOUND.value()).status(HttpStatus.NOT_FOUND.getReasonPhrase())
				.timestamp(LocalDateTime.now()).build();
		return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
			DataIntegrityViolationException exception) {
		String message = exception.getMessage();
		if (message.contains("user.username")) {
			message = "This username is already taken. Please choose a different username.";
		} else if (message.contains("user.email")) {
			message = "This email is already in use. Please choose a different email.";
		} else {
			message = "Invalid user object. Please try again.";
		}

		ErrorResponse errorResponse = ErrorResponse.builder().message(message).errorCode(HttpStatus.BAD_REQUEST.value())
				.status(HttpStatus.BAD_REQUEST.getReasonPhrase()).timestamp(LocalDateTime.now()).build();
		return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	}

	@ExceptionHandler(UserFollowException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateFollowException(UserFollowException exception) {
		ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage())
				.errorCode(HttpStatus.BAD_REQUEST.value()).status(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.timestamp(LocalDateTime.now()).build();
		return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException exception) {
		ErrorResponse errorResponse = ErrorResponse.builder()
				.message("Invalid type please provide valid " + exception.getRequiredType().toString())
				.errorCode(HttpStatus.BAD_REQUEST.value()).status(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.timestamp(LocalDateTime.now()).build();
		return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException exception) {
		ErrorResponse errorResponse = ErrorResponse.builder()
				.message("Invalid request parameters, please provide valid parameters")
				.errorCode(HttpStatus.BAD_REQUEST.value()).status(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.timestamp(LocalDateTime.now()).build();
		return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException exception) {
		ErrorResponse errorResponse = ErrorResponse.builder()
				.message("Invalid http request method, this endpoint only supports: "
						+ exception.getSupportedHttpMethods().toString())
				.errorCode(HttpStatus.BAD_REQUEST.value()).status(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.timestamp(LocalDateTime.now()).build();
		return ResponseEntity.status(errorResponse.getErrorCode()).body(errorResponse);
	}

}
