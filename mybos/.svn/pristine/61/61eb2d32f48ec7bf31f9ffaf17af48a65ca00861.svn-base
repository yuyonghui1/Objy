package com.hy.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.system.RoleDao;
import com.hy.domain.system.Menu;
import com.hy.domain.system.Permission;
import com.hy.domain.system.Role;
import com.hy.service.system.RoleService;
@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public void save(Role model, String[] permissionIds, String menuIds) {
		
		roleDao.save(model);
		
		String[] split = menuIds.split(",");
		for (String menuId : split) {
			
			Menu menu = new Menu();
			menu.setId(Integer.parseInt(menuId));
			model.getMenus().add(menu);
		}
		
		for (String permissionId : permissionIds) {

			Permission permission = new Permission();
			permission.setId(Integer.parseInt(permissionId));
			model.getPermissions().add(permission);
		}
		
	}

	@Override
	public List<Role> findAll() {

		List<Role> list = roleDao.findAll();
		return list;
	}
	
}
