package com.hy.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.hy.domain.base.FixedArea;
import com.hy.service.CustomerService;
import com.hy.service.base.FixedAreaService;
import com.hy.web.action.common.BaseAction;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results(@Result(name="list",location="/pages/base/fixed_area.jsp"))
public class FixedAreaAction extends BaseAction<FixedArea>{
	
	@Autowired
	private FixedAreaService fixedAreaService;
	
	@Autowired
	private CustomerService customerService;
	
	private String[] subareaId;
	public void setSubareaId(String[] subareaId) {
		this.subareaId = subareaId;
	}

	@Action("fixedArea_save")
	public String save(){
		
		fixedAreaService.save(model,subareaId);
		
		return "list";
	}
	
	@Action("fixedArea_findPage")
	public void findPage(){
		
		Pageable pageable=new PageRequest(page, rows);
		
		Page<FixedArea> page = fixedAreaService.findPage(pageable);
		
		java2json(page, new String[]{"couriers","subareas"});
	}
	
	private List<Integer> customerIds;
	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}

	@Action("fixedArea_assignCustomers2FixedArea")
	public String assignCustomers2FixedArea(){

		customerService.assignCustomers2FixedArea(model.getId(),customerIds);
		
		return "list";
	}
	
	private Integer courierId;
	private Integer takeTimeId;
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	@Action("fixedArea_associationCourierToFixedArea")
	public String associationCourierToFixedArea(){
		
		fixedAreaService.associationCourierToFixedArea(model,courierId,takeTimeId);
		
		return "list";
	}
	
}
