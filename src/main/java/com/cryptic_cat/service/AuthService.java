package com.cryptic_cat.service;

import com.cryptic_cat.entity.RefreshToken;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.payload.request.LoginRequest;
import com.cryptic_cat.payload.response.TokenResponse;

public interface AuthService {
	TokenResponse authenticateAndGenerateToken(LoginRequest loginRequest);

	RefreshToken createRefreshToken(User userDetails);

	TokenResponse refreshAccessToken(String refreshToken);

	User getCurrentUser();

	void logout();
}
