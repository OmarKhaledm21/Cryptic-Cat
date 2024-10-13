package com.cryptic_cat.dao;

import com.cryptic_cat.entity.Role;
import com.cryptic_cat.enums.RoleType;

public interface RoleDao {
	Role findRoleByName(RoleType roleType);
}
