package com.cryptic_cat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cryptic_cat.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
}
