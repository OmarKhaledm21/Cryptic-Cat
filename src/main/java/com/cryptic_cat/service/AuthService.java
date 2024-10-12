package com.cryptic_cat.service;

import com.cryptic_cat.payload.request.LoginRequest;


public interface AuthService {
	String authenticateAndGenerateToken(LoginRequest loginRequest);
}
