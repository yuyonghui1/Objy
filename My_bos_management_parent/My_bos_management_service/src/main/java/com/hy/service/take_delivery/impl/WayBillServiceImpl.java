package com.hy.service.take_delivery.impl;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.take_delivery.WayBillDao;
import com.hy.domain.take_delivery.WayBill;
import com.hy.service.take_delivery.WayBillService;
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService{

	@Autowired
	private WayBillDao wayBillDao;

	@Override
	@RequiresPermissions("saveWayBill")//这个是异步访问,注解方式只会在控制台看到错误,处理之后的界面也只有控制台看的到
										//所以异步访问的方法,不要用注解方式控制权限
	public void save(WayBill model) {
		wayBillDao.save(model);
	}

	@Override
	public Page<WayBill> findPage(Pageable pageable) {

		Page<WayBill> page = wayBillDao.findAll(pageable);
		return page;
	}
	
}
