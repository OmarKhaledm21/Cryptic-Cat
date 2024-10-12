package com.cryptic_cat.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.cryptic_cat.exception.InvalidUsernameOrPasswordException;
import com.cryptic_cat.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private UserService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException, InvalidUsernameOrPasswordException {
		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			final String jwt = authHeader.substring(7);
			final String userName = tokenProvider.extractUsername(jwt);

			
			if (userName != null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
				if (tokenProvider.isTokenValid(jwt, userDetails)) {

					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		} catch (SignatureException ex) {
			System.out.println("Error in JWT Authentication Filter: " + ex.getMessage() + " " + ex.getClass());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature.");
			return;
		} catch (ExpiredJwtException ex) {

			System.out.println("Error in JWT Authentication Filter: " + ex.getMessage() + " " + ex.getClass());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT has expired.");
			return;
		} catch (Exception ex) {
			System.out.println("Error in JWT Authentication Filter: " + ex.getMessage() + " " + ex.getClass());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
			return;
		}
		filterChain.doFilter(request, response);
	}

}
