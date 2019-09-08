package com.hy.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hy.domain.base.Area;

public interface AreaDao extends JpaRepository<Area, String> {

	List<Area> findByProvinceAndCityAndDistrict(String province, String city, String district);

}
