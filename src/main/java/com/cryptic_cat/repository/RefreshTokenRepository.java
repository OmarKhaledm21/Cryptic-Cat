package com.cryptic_cat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cryptic_cat.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
    List<RefreshToken> findByUserIdOrderByExpiryDateDesc(long userId);
}
