package com.cryptic_cat.repository.impl;

import org.springframework.stereotype.Repository;

import com.cryptic_cat.entity.Role;
import com.cryptic_cat.enums.RoleType;
import com.cryptic_cat.repository.RoleRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
	private EntityManager entityManager;

	public RoleRepositoryImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	public Role findRoleByName(RoleType roleType) {
		TypedQuery<Role> theQuery = entityManager.createQuery("FROM Role WHERE name=:roleName", Role.class);
		theQuery.setParameter("roleName", roleType);
		
		Role theRole = null;
		
		try {
			theRole = theQuery.getSingleResult();
		} catch (Exception e) {
			theRole = null;
		}
		
		return theRole;
	}
}
