package com.cryptic_cat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cryptic_cat.entity.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

	Like findByUserIdAndPostId(Long userId, Long postId);

	List<Like> findByPostId(Long postId);
}
