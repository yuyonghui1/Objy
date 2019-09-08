package com.hy.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hy.domain.system.Permission;

public interface PermissionDao extends JpaRepository<Permission, Integer>{

	@Query("select distinct p from Permission p inner join p.roles r inner join r.users u where u.id=?")
	List<Permission> findByUserId(Integer id);

}
