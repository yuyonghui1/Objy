package com.hy.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hy.domain.system.User;

public interface UserDao extends JpaRepository<User, Integer>{

	User findByUsername(String username);

}
