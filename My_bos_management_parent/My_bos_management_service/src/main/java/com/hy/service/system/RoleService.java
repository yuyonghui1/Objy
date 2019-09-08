package com.hy.service.system;

import java.util.List;

import com.hy.domain.system.Role;

public interface RoleService {

	void save(Role model, String[] permissionIds, String menuIds);

	List<Role> findAll();

}
