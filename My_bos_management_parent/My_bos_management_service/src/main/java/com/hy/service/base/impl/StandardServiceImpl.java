package com.hy.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.base.StandardDao;
import com.hy.domain.base.Standard;
import com.hy.service.base.StandardService;
@Service
@Transactional
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardDao standardDao;
	
	@Override
	public Page<Standard> findGridPage(Pageable pageable) {
		
		Page<Standard> page = standardDao.findAll(pageable);
		
		return page;
	}

	@Override
	public void save(Standard model) {
		standardDao.save(model);
	}

	@Override
	public void del(int id) {
		standardDao.del(id);
	}

	@Override
	public List<Standard> findAll() {

		List<Standard> list = standardDao.findAll();
		return list;
	}
	
	
	
}
