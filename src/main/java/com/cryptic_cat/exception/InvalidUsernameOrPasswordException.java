package com.cryptic_cat.exception;

public class InvalidUsernameOrPasswordException extends RuntimeException{
	public InvalidUsernameOrPasswordException(String message) {
		super(message);
	}
}
