package com.cryptic_cat.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "likes_count")
	private int likesCount;

	@Column(name = "comments_count")
	private int commentsCount;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments;
}
