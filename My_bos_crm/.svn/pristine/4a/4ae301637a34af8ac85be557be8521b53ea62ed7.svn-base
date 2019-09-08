package com.hy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.CustomerDao;
import com.hy.domain.Customer;
@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Override
	public List<Customer> findAll() {

		List<Customer> list = customerDao.findAll();
		return list;
	}

	@Override
	public List<Customer> findNotAssociation() {

		List<Customer> list=customerDao.findByFixedAreaIdIsNull();
		return list;
	}

	@Override
	public List<Customer> findHasAssociationByFixedAreaId(String fixedAreaId) {

		List<Customer> list=customerDao.findByFixedAreaId(fixedAreaId);
		return list;
	}

	@Override
	public void assignCustomers2FixedArea(String id, Integer[] custIds) {
		
		customerDao.dropCustomerFromFixedArea(id);
		
		if(custIds!=null&&custIds.length>0){
			for (Integer custId : custIds) {
				
				customerDao.addCustomerToFixedArea(id,custId);
			}
		}
	}

	@Override
	public Boolean findCustomerExistWithTel(String telephone) {
		
		List<Customer> list=customerDao.findByTelephone(telephone);
		
		if(list!=null&&list.size()>0){
			return false;
		}
		
		return true;
	}

	@Override
	public void save(Customer customer) {
		customerDao.save(customer);
	}

	@Override
	public Customer findCustomerWithTel(String telephone) {
		
		List<Customer> list=customerDao.findByTelephone(telephone);
		
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public void activeCustomer(Integer id) {
		
		customerDao.activeCustomer(id);
	}

	@Override
	public Customer login(String telephone, String password) {

		Customer customer = customerDao.findByTelephoneAndPassword(telephone,password);
		
		return customer;
	}

	@Override
	public String findByAddress(String address) {

		String fixedAreaId = customerDao.findByAddress(address);
		
		return fixedAreaId;
	}
	
	
}
