package com.hy.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hy.domain.system.Menu;

public interface MenuDao extends JpaRepository<Menu, Integer>{

	List<Menu> findByParentMenuIsNull();

	@Query("select distinct m from Menu m inner join m.roles r inner join r.users u  where u.id=?")
	List<Menu> findByUserId(Integer userId);

}
