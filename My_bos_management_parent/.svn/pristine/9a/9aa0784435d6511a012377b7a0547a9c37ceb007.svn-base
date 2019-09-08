package com.hy.service.base.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.base.SubAreaDao;
import com.hy.domain.base.SubArea;
import com.hy.service.base.SubAreaService;
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
	
	@Autowired
	private SubAreaDao subAreaDao;
	
	@Override
	public void save(SubArea model) {
		model.setId(UUID.randomUUID().toString());
		subAreaDao.save(model);
	}

	@Override
	public Page<SubArea> findPage(Pageable pageable) {
		Page<SubArea> page = subAreaDao.findAll(pageable);
		return page;
	}

	@Override
	public List<SubArea> findByFixedAreaIsNull() {
		
		return subAreaDao.findByFixedAreaIsNull();
	}

	@Override
	public List<SubArea> findAll() {

		List<SubArea> list = subAreaDao.findAll();
		return list;
	}

	@Override
	public List<Object[]> findChart() {

		return subAreaDao.findChar();
	}


}
