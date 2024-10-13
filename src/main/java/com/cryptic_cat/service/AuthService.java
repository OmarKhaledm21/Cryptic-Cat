package com.cryptic_cat.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.cryptic_cat.entity.RefreshToken;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.payload.request.LoginRequest;
import com.cryptic_cat.payload.response.TokenResponse;

public interface AuthService {
	TokenResponse authenticateAndGenerateToken(LoginRequest loginRequest);

	RefreshToken createRefreshToken(UserDetails userDetails, User user);

	String refreshAccessToken(String refreshToken);
}
