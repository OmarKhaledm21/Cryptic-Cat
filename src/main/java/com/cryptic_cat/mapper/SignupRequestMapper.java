package com.cryptic_cat.mapper;

import com.cryptic_cat.payload.request.SignupRequest;
import com.cryptic_cat.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SignupRequestMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignupRequestMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toUser(SignupRequest signupRequest) {
        return User.builder()
                .email(signupRequest.getEmail())
                .userName(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .enabled(true)
                .build();
    }
}
