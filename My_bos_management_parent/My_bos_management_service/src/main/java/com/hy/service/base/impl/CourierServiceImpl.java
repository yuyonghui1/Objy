package com.hy.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.base.CourierDao;
import com.hy.domain.base.Courier;
import com.hy.domain.base.Standard;
import com.hy.service.base.CourierService;
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	
	@Autowired
	private CourierDao courierDao;
	
	@Override
	public void save(Courier model) {
		
		courierDao.save(model);
	}

	@Override
	public Page<Courier> findPage(final Courier model,Pageable pageable) {
		
		Specification<Courier> specification = new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				ArrayList<Predicate> list = new ArrayList<>();
				
				String type = model.getType();
				String company = model.getCompany();
				String courierNum = model.getCourierNum();
				
				if(StringUtils.isNotBlank(type)){
					
					Predicate p1 = cb.equal(root.get("type").as(String.class),type);
					list.add(p1);
				}
				
				if(StringUtils.isNotBlank(company)){
					
					Predicate p2 = cb.equal(root.get("company").as(String.class),company);
					list.add(p2);
				}
				
				if(StringUtils.isNotBlank(courierNum)){
					
					Predicate p3 = cb.equal(root.get("courierNum").as(String.class),courierNum);
					list.add(p3);
				}
				
				Standard standard = model.getStandard();
				
				if(standard!=null){
					
					String name = standard.getName();
					
					if(StringUtils.isNotBlank(name)){
						
						Join<Object, Object> join = root.join("standard",JoinType.INNER);
						
						Predicate p4 = cb.equal(join.get("name").as(String.class),name);
						list.add(p4);
					}
				}
				
				if(list.size()==0){
					return null;				
				}
				
				Predicate [] predicates = new Predicate[list.size()];
				//将list结合中断言对象存在 数组中predicates
				predicates = list.toArray(predicates);
				
				return cb.and(predicates);
			}
		};
		
		Page<Courier> page = courierDao.findAll(specification,pageable);
		
		return page;
	}

	@Override
	public void del(String ids) {
		
		String[] split = ids.split(",");
		for (String str : split) {
			
			int id=Integer.valueOf(str);
			courierDao.del(id);
		}
		
	}

	@Override
	public void restore(String ids) {

		String[] split = ids.split(",");
		for (String str : split) {
			
			int id=Integer.valueOf(str);
			courierDao.restore(id);
		}
		
	}

	@Override
	public List<Courier> findOnWork() {
		//TODO 应该查询没有废除的快递员
		return courierDao.findOnWork();
	}

}
