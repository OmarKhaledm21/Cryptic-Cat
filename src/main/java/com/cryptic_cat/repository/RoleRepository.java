package com.cryptic_cat.repository;

import com.cryptic_cat.entity.Role;
import com.cryptic_cat.enums.RoleType;

public interface RoleRepository {
	Role findRoleByName(RoleType roleType);

	Role save(Role role);
}
