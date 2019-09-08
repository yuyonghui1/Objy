package com.hy.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hy.domain.base.SubArea;

public interface SubAreaDao extends JpaRepository<SubArea, String>{
	
	public List<SubArea> findByFixedAreaIsNull();
	
	@Query("select a.province,count(*) from SubArea s inner join s.area a group by a.province")
	public List<Object[]> findChar();
}
