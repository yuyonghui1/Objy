package com.hy.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hy.domain.base.Area;
import com.hy.domain.take_delivery.Order;
import com.hy.service.Customer;
import com.hy.service.take_delivery.OrderService;
import com.hy.web.action.common.BaseAction;
@Controller("orderAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({@Result(name="index",location="/index.html")})
public class OrederAction extends BaseAction<Order> {
	
	@Autowired
	private OrderService orderService;
	
	private String sendAreaInfo;
	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}
	private String recAreaInfo;
	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}

	@Action("order_save")
	public String save(){
		
		if(StringUtils.isNotBlank(sendAreaInfo)){
			String[] split = sendAreaInfo.split("/");
			String province=split[0];
			String city=split[1];
			String district=split[2];
			Area sendArea = new Area(province, city, district);
			model.setSendArea(sendArea);
		}
		
		if(StringUtils.isNotBlank(recAreaInfo)){
			String[] split = recAreaInfo.split("/");
			String province=split[0];
			String city=split[1];
			String district=split[2];
			Area recArea = new Area(province, city, district);
			model.setRecArea(recArea);
		}
		
		Customer customer = (Customer) ServletActionContext.getRequest().getSession().getAttribute("customer");
		if(customer!=null){
			model.setCustomer_id(customer.getId());
		}
		
		orderService.save(model);
		
		return "index";
	}
	
}
