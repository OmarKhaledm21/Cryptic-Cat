package com.cryptic_cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cryptic_cat.entity.User;
import com.cryptic_cat.entity.UserFollow;
import java.util.List;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

	UserFollow findByUserIdAndFollowedUserId(Long id, Long id2);

	@Query("SELECT uf.followedUser FROM UserFollow uf WHERE uf.user.id = :userId")
	List<User> findFollowingUsersByUserId(@Param("userId") Long userId);

	@Query("SELECT uf.user FROM UserFollow uf WHERE uf.followedUser.id = :followedUserId")
	List<User> findFollowersByUserId(@Param("followedUserId") Long followedUserId);
}
