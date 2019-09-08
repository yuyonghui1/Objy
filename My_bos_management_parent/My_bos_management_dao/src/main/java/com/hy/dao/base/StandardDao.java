package com.hy.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hy.domain.base.Standard;

public interface StandardDao extends JpaRepository<Standard,Integer>{
	
	public Standard findById(int i);
	
	@Query("from Standard where id=?")
	public Standard findXXX(int i);
	
	@Modifying
	@Query("update Standard set name=:name where id=:id")
	public void edit(@Param("name")String name,@Param("id") Integer id);
	
	@Modifying
	@Query("delete from Standard where id=:id")
	public void del(@Param("id") Integer id);
	
	
}
