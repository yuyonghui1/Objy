package com.hy.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hy.domain.base.Courier;

public interface CourierService {

	void save(Courier model);

	Page<Courier> findPage(Courier model, Pageable pageable);

	void del(String ids);
	void restore(String ids);

	List<Courier> findOnWork();

}
