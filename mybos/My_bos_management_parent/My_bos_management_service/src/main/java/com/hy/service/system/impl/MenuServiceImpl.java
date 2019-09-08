package com.hy.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.system.MenuDao;
import com.hy.domain.system.Menu;
import com.hy.service.system.MenuService;
@Service
@Transactional
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuDao menuDao;

	@Override
	public List<Menu> findTree() {
		List<Menu> list = menuDao.findByParentMenuIsNull();
		return list;
	}

	@Override
	public List<Menu> findAll() {

		List<Menu> list = menuDao.findAll();
		
		return list;
	}

	@Override
	public List<Menu> findByUserId(Integer userId) {

		List<Menu> list = menuDao.findByUserId(userId);
		return list;
	}
	
	@Override
	public void save(Menu model) {
		menuDao.save(model);
	}


}
