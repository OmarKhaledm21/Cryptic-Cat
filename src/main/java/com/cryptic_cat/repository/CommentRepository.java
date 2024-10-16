package com.cryptic_cat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cryptic_cat.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
	List<Comment> findByPostIdWithUser(@Param("postId") Long postId);

}
