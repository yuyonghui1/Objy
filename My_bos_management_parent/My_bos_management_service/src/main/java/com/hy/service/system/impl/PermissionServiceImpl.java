package com.hy.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.system.PermissionDao;
import com.hy.domain.system.Permission;
import com.hy.service.system.PermissionService;
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{

	@Autowired
	private PermissionDao permissionDao;

	@Override
	public void save(Permission model) {
		permissionDao.save(model);
	}


	@Override
	public List<Permission> findAll() {

		List<Permission> list = permissionDao.findAll();
		return list;
	}
	
}
