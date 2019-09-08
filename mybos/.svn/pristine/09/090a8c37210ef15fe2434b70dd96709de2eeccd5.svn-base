package com.hy.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hy.domain.base.FixedArea;

public interface FixedAreaService {

	void save(FixedArea model,String[] subareaId);

	Page<FixedArea> findPage(Pageable pageable);

	void associationCourierToFixedArea(FixedArea model,Integer courierId, Integer takeTimeId);

}
