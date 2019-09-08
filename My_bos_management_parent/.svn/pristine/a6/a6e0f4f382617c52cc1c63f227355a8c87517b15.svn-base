package com.hy.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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

import com.hy.domain.base.Courier;
import com.hy.service.base.CourierService;
import com.hy.web.action.common.BaseAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results(@Result(name="list",location="/pages/base/courier.jsp"))
public class CourierAction extends BaseAction<Courier>{
	
//	private Courier model=new Courier();
//	@Override
//	public Courier getModel() {
//		return model;
//	}
	
	@Autowired
	private CourierService courierService;
	
	@Action("courier_save")
	public String save(){
		courierService.save(model);
		return "list";
	}
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action("courier_del")
	public String del(){

		courierService.del(ids);
		return "list";
	}
	
	@Action("courier_restore")
	public String restore(){
		
		courierService.restore(ids);
		return "list";
	}
	
	@Action("courier_findAll")
	public void findAll(){
		
		List<Courier> list=courierService.findOnWork();
		
		java2json(list, new String[]{"fixedAreas"});
	}
	
//	private int page;
//	private int rows;
//	public void setPage(int page) {
//		this.page = page;
//	}
//	public void setRows(int rows) {
//		this.rows = rows;
//	}

	@Action("courier_findPage")
	public void findPage(){
		
		Pageable pageable = new PageRequest(page, rows);
		
		Page<Courier> cPage = courierService.findPage(model,pageable);
		
		java2json(cPage, new String[]{"fixedAreas"});
		
//		List<Courier> list = cPage.getContent();
//		long total = cPage.getTotalElements();
//		
//		HashMap<String, Object> map = new HashMap<>();
//		
//		map.put("total",total);
//		map.put("rows",list);
//		
//		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.setExcludes(new String[]{"company","fixedAreas"});
//		
//		String json = JSONObject.fromObject(map).toString();
//		
//		try {
//			HttpServletResponse response = ServletActionContext.getResponse();
//			
//			response.setContentType("text/json;charset=utf-8");
//			
//			response.getWriter().write(json);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
}
