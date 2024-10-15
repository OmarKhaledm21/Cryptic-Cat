package com.cryptic_cat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_follow")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFollow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "followed_user_id", nullable = false)
	private User followedUser;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;
}
