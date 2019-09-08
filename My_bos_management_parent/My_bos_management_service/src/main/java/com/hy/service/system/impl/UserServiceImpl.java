package com.hy.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.system.UserDao;
import com.hy.domain.system.Role;
import com.hy.domain.system.User;
import com.hy.service.system.UserService;
@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;

	@Override
	public void save(User model, String[] roleIds) {

		userDao.save(model);
		
		for (String roleId : roleIds) {
			
			Role role = new Role();
			role.setId(Integer.parseInt(roleId));
			
			model.getRoles().add(role);
		}
	}

	@Override
	public Page<User> findPage(Pageable pageable) {
		
		Page<User> page = userDao.findAll(pageable);
		return page;
	}
	
	
	
}
