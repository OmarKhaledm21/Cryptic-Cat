package com.cryptic_cat.payload.request;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class LoginRequest {
	private String userName;
	private String password;
}
