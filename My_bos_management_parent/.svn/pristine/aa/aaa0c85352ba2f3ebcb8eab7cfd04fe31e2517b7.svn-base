package com.hy.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.base.AreaDao;
import com.hy.domain.base.Area;
import com.hy.service.base.AreaService;
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaDao areaDao;
	
	@Override
	public void saveArea(List<Area> list) {
		
		for (Area area : list) {
			
			Area save = areaDao.save(area);
		}
	}

	@Override
	public Page<Area> findPage(Pageable pageAble) {
		
		Page<Area> aPage = areaDao.findAll(pageAble);
		
		return aPage;
	}

	@Override
	public List<Area> findAll() {
		// TODO Auto-generated method stub
		List<Area> list = areaDao.findAll();
		return list;
	}

}
