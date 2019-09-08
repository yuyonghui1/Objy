package com.hy.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hy.domain.base.SubArea;

public interface SubAreaService {

	void save(SubArea model);

	Page<SubArea> findPage(Pageable pageable);


	List<SubArea> findByFixedAreaIsNull();

	List<SubArea> findAll();

	List<Object[]> findChart();


}
