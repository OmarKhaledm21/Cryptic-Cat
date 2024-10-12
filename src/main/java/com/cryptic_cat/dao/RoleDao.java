package com.cryptic_cat.dao;

import com.cryptic_cat.entity.Role;

public interface RoleDao {
	Role findRoleByName(String theRoleName);
}
