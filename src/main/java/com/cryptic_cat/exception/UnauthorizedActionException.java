package com.cryptic_cat.exception;

public class UnauthorizedActionException extends RuntimeException {
	public UnauthorizedActionException(String message) {
		super(message);
	}
}
