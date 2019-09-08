package com.hy.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.base.CourierDao;
import com.hy.dao.base.FixedAreaDao;
import com.hy.dao.base.SubAreaDao;
import com.hy.domain.base.Courier;
import com.hy.domain.base.FixedArea;
import com.hy.domain.base.SubArea;
import com.hy.domain.base.TakeTime;
import com.hy.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaDao fixedAreaDao;
	@Autowired
	private SubAreaDao subAreaDao;
	@Autowired
	private CourierDao courierDao;

	@Override
	public void save(FixedArea model,String[] subareaId) {
		
		model= fixedAreaDao.save(model);
		if(subareaId!=null&&subareaId.length>0){
			SubArea subArea;
			for (String id : subareaId) {
				
				subArea=subAreaDao.findOne(id);
				subArea.setFixedArea(model);
			}
		}
	}

	@Override
	public Page<FixedArea> findPage(Pageable pageable) {
		
		Page<FixedArea> page = fixedAreaDao.findAll(pageable);
		
		return page;
	}

	@Override
	public void associationCourierToFixedArea(FixedArea model,Integer courierId, Integer takeTimeId) {
		
		Courier courier = courierDao.findOne(courierId);
		
		TakeTime takeTime = new TakeTime();
		takeTime.setId(takeTimeId);
		
		courier.setTakeTime(takeTime);
		
		FixedArea fixedArea = fixedAreaDao.findOne(model.getId());
		fixedArea.getCouriers().add(courier);
	}
	
}
