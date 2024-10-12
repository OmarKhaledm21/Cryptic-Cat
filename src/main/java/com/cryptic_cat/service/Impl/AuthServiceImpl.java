package com.cryptic_cat.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cryptic_cat.payload.request.LoginRequest;
import com.cryptic_cat.security.JwtTokenProvider;
import com.cryptic_cat.service.AuthService;
import com.cryptic_cat.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;
	private UserService userService;

	@Autowired
	public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}

	public String authenticateAndGenerateToken(LoginRequest loginRequest) throws UsernameNotFoundException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUserName());
		
		String token = jwtTokenProvider.generateToken(userDetails);
		return token;
	}
}
