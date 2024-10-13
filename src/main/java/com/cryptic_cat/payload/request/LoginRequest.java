package com.cryptic_cat.payload.request;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
	private String userName;
	private String password;
}
