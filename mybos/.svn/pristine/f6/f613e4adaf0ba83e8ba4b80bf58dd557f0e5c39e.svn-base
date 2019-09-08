package com.hy.dao.base;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hy.domain.base.Standard;
import com.hy.service.base.StandardService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class StandardDaoTest {
	
	@Autowired
	private StandardDao standardDao;
	
	@Autowired
	private StandardService standardService;
	
	@Test
	public void test1() {
		
		Standard s = new Standard();
		
		s.setId(1);
		s.setName("hy");
		standardDao.save(s);
	}
	
	@Test
	public void test2() {
		
		Standard standard = standardDao.findById(3);
		System.out.println(standard.getName());
	}
	@Test
	public void test3() {
		
		Standard standard = standardDao.findXXX(3);
		System.out.println(standard.getName());
	}
	
	@Test
	public void test4() {
		
		System.out.println(standardService);
	}

}
