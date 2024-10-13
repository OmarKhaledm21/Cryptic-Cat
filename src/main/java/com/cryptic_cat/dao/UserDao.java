package com.cryptic_cat.dao;

import com.cryptic_cat.entity.User;

public interface UserDao {

	User findByUserName(String userName);

	User save(User user);

}
