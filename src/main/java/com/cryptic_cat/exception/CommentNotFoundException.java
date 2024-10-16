package com.cryptic_cat.exception;

public class CommentNotFoundException extends RuntimeException {
	public CommentNotFoundException(String message) {
		super(message);
	}

}
