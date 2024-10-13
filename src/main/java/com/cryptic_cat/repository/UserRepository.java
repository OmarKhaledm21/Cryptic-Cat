package com.cryptic_cat.repository;

import com.cryptic_cat.entity.User;

public interface UserRepository {

	User findByUserName(String userName);

	User save(User user);

}
