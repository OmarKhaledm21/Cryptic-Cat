package com.cryptic_cat.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.cryptic_cat.entity.RefreshToken;
import com.cryptic_cat.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class RefreshTokenRepositoryTest {

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private EntityManager entityManager;
	
	private User testUser;
	
	@BeforeEach
	@Transactional
	void setUp() {
		testUser = new User();
		testUser.setUserName("testuser");
		testUser.setPassword("testpass");
		testUser.setBirthDate(LocalDate.now());
		testUser.setCreatedAt(LocalDateTime.now());
		testUser.setEmail("testemail@test.com");
		testUser.setEnabled(true);
		testUser.setFirstName("test");
		testUser.setLastName("test");

	    entityManager.persist(testUser);
	    entityManager.flush();
	    entityManager.refresh(testUser);


		refreshTokenRepository.save(RefreshToken.builder().token("token1").expiryDate(LocalDateTime.now().plusDays(1))
				.user(testUser).build());

		refreshTokenRepository.save(RefreshToken.builder().token("token2").expiryDate(LocalDateTime.now().plusDays(2))
				.user(testUser).build());
		System.out.println(testUser.getId());
	}

	@Test
	@Transactional
	void findByToken_shouldReturnToken_whenTokenExists() {
		RefreshToken token = refreshTokenRepository.findByToken("token2");
		assertThat(token).isNotNull();
		assertThat(token.getToken()).isEqualTo("token2");
		assertThat(token.getUser().getUsername()).isEqualTo("testuser");

		
	}

	@Test
	@Transactional
	void findByUserIdOrderByExpiryDateDesc_shouldReturnTokensOrderedByExpiryDate() {
		List<RefreshToken> tokens = refreshTokenRepository.findByUserIdOrderByExpiryDateDesc(testUser.getId());
		assertThat(tokens).hasSize(2);
		assertThat(tokens.get(0).getToken()).isEqualTo("token2"); // Latest expiry date
		assertThat(tokens.get(1).getToken()).isEqualTo("token1"); // Earlier expiry date
	}

	@Test
	@Transactional
	void deleteByUserId_shouldRemoveTokens_whenUserIdExists() {
		refreshTokenRepository.deleteByUserId(testUser.getId());

		List<RefreshToken> tokens = refreshTokenRepository.findByUserIdOrderByExpiryDateDesc(testUser.getId());
		assertThat(tokens).isEmpty();
	}

}
