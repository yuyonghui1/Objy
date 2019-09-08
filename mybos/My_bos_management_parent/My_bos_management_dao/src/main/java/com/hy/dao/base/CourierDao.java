package com.hy.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hy.domain.base.Courier;

public interface CourierDao extends JpaRepository<Courier,Integer>,JpaSpecificationExecutor<Courier>{
	@Modifying
	@Query("update Courier set deltag='1' where id=?")
	void del(int id);

	@Modifying
	@Query("update Courier set deltag='0' where id=?")
	void restore(int id);
	
	@Query("from Courier where deltag='0'")
	List<Courier> findOnWork();
	
	@Query(nativeQuery=true,value="select c.c_id from (select * from t_courier  where c_id in(select c_courier_id from t_fixedarea_courier where c_fixed_area_id=333)) c left join t_work_bill w on c.c_id=w.c_courier and c.c_id in(select c_courier_id from t_fixedarea_courier where c_fixed_area_id=?)  group by c.c_id order by count(*) ")
	List<Object> findFreeCourierWithFixedId(String fixedAreaId);
	
}
