package com.cryptic_cat.service.Impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cryptic_cat.dao.RefreshTokenRepository;
import com.cryptic_cat.entity.RefreshToken;
import com.cryptic_cat.entity.User;
import com.cryptic_cat.exception.InvalidTokenException;
import com.cryptic_cat.exception.InvalidUsernameOrPasswordException;
import com.cryptic_cat.payload.request.LoginRequest;
import com.cryptic_cat.payload.response.TokenResponse;
import com.cryptic_cat.security.JwtTokenProvider;
import com.cryptic_cat.service.AuthService;
import com.cryptic_cat.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;
	private UserService userService;
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider
			, UserService userService, RefreshTokenRepository refreshTokenRepository) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public TokenResponse authenticateAndGenerateToken(LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			User user = userService.findByUserName(loginRequest.getUserName());
			UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUserName());
			
			String accessToken = jwtTokenProvider.generateToken(userDetails);
            RefreshToken refreshToken = createRefreshToken(userDetails,user);
            refreshTokenRepository.save(refreshToken);
            
            TokenResponse tokenResponse = TokenResponse.builder()
            		.accessToken(accessToken)
            		.refreshToken(refreshToken.getToken()).build();
			return tokenResponse;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			throw new InvalidUsernameOrPasswordException("Invalid username or password.");
		}
	}
	
	public RefreshToken createRefreshToken(UserDetails userDetails,User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(jwtTokenProvider.generateRefreshToken(userDetails)); 
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(30)); 
        refreshToken.setUser(user); 
        return refreshToken;
    }

    public String refreshAccessToken(String refreshToken) {
        RefreshToken storedRefreshToken = refreshTokenRepository.findByToken(refreshToken);
        if (storedRefreshToken == null || storedRefreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Invalid refresh token.");
        }

        UserDetails userDetails = userService.loadUserByUsername(storedRefreshToken.getUser().getUserName());
        return jwtTokenProvider.generateToken(userDetails);
    }
}
