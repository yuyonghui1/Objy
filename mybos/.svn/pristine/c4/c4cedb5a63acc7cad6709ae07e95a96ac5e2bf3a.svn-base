package com.hy.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hy.domain.system.Role;

public interface RoleDao extends JpaRepository<Role, Integer>{

	@Query("select r from Role r inner join r.users u where u.id=?")
	List<Role> findByUserId(Integer id);

}
