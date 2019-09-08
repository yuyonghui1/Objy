package com.hy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hy.domain.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{
	
	public List<Customer> findByFixedAreaIdIsNull();
 
	public List<Customer> findByFixedAreaId(String fixedAreaId);

	@Query("update Customer set fixedAreaId=? where id=?")
	@Modifying
	public void addCustomerToFixedArea(String id, Integer custId);

	@Modifying
	@Query("update Customer set fixedAreaId=null where fixedAreaId=?")
	public void dropCustomerFromFixedArea(String id);

	public List<Customer> findByTelephone(String telephone);

	@Modifying
	@Query("update Customer set type='1' where id=?")
	public void activeCustomer(Integer id);

	public Customer findByTelephoneAndPassword(String telephone, String password);
	
	
	@Query("select fixedAreaId from Customer  where address=?")
	public String findByAddress(String address);

}
