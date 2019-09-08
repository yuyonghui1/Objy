package com.hy.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hy.domain.system.User;

public interface UserService {

	void save(User model, String[] roleIds);

	Page<User> findPage(Pageable pageable);

}
