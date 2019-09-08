package com.hy.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hy.domain.system.Permission;

public interface PermissionService {

	void save(Permission model);


	List<Permission> findAll();

}
