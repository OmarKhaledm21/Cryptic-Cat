package com.cryptic_cat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cryptic_cat.service.UserService;


@Configuration
public class SecurityConfig {
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/api/v1/auth/signup").permitAll()
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/auth/test").hasRole("USER")
                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .logout(logout -> logout.permitAll())
                .csrf(csrf-> csrf.disable());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
