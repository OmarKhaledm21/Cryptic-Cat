package com.cryptic_cat.repository.impl;

import org.springframework.stereotype.Repository;

import com.cryptic_cat.entity.User;
import com.cryptic_cat.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserRepositoryImpl implements UserRepository {
	private EntityManager entityManager;

	public UserRepositoryImpl(EntityManager theEntityManager) {
		this.entityManager = theEntityManager;
	}

	@Override
	public User findByUserName(String theUserName) {
		TypedQuery<User> theQuery = entityManager.createQuery("from User where userName=:uName and enabled=true",
				User.class);
		theQuery.setParameter("uName", theUserName);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

	@Override
	public User save(User user) {
		User theUser = this.entityManager.merge(user);
		return theUser;
	}

	@Override
	public User findById(Long id) {
		TypedQuery<User> theQuery = entityManager.createQuery("from User where id=:userId and enabled=true",
				User.class);
		theQuery.setParameter("userId", id);

		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			theUser = null;
		}

		return theUser;
	}

}
