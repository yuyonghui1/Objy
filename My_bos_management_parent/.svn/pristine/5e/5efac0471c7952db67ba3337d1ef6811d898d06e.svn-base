package com.hy.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hy.service.Customer;
import com.hy.service.CustomerService;
import com.hy.web.action.common.BaseAction;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class CustomerAcion extends BaseAction<Customer>{
	
	@Autowired
	private CustomerService customerService;
	
	@Action("fixedArea_findNotAssociation")
	public void findNotAssociation(){
		List<Customer> list = customerService.findNotAssociation();
		
		java2json(list, null);
	}
	
	private String fId;
	public void setFId(String fId) {
		this.fId = fId;
	}

	@Action("fixedArea_findHasAssociationByFixedAreaId")
	public void findHasAssociationByFixedAreaId(){
		List<Customer> list = customerService.findHasAssociationByFixedAreaId(fId);
		
		java2json(list, null);
	}
	
}
