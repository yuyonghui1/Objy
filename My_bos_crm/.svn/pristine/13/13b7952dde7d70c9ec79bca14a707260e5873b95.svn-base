package com.hy.service;

import java.util.List;

import javax.jws.WebService;

import com.hy.domain.Customer;

@WebService
public interface CustomerService {
	
	public List<Customer> findAll();
	
	public List<Customer> findNotAssociation();
	
	public List<Customer> findHasAssociationByFixedAreaId(String fixedAreaId);
	
	public void assignCustomers2FixedArea(String id,Integer[] custIds);
	
	public Boolean findCustomerExistWithTel(String telephone);
	
	public void save(Customer customer);
	
	public Customer findCustomerWithTel(String telephone);
	
	public void activeCustomer(Integer id);
	
	public Customer login(String telephone,String password);
	
	public String findByAddress(String address);
}
