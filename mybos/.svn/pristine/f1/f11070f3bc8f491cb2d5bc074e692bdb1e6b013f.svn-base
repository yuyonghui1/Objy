package com.hy.web.action.take_delivery;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
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

import com.hy.domain.take_delivery.WayBill;
import com.hy.service.take_delivery.WayBillService;
import com.hy.web.action.common.BaseAction;

@Controller("wayBillAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@ExceptionMappings({@ExceptionMapping(exception="org.apache.shiro.authz.UnauthorizedException",result="unauthorized")})
@Results({@Result(name="unauthorized",location="/unauthorized.jsp")})
public class WayBillAction extends BaseAction<WayBill>{
	
	@Autowired
	private WayBillService wayBillService;

	@Action("wayBill_findPage")
	public void findPage(){
		
		Pageable pageable=new PageRequest(page, rows);
		Page<WayBill> page = wayBillService.findPage(pageable);
		java2json(page, new String[]{});
		
	}
	
	@Action("wayBill_save")
	public void save(){
		
		wayBillService.save(model);
		
	}
	
	
	
}
