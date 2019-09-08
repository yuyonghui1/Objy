package com.hy.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.hy.domain.base.Standard;
import com.hy.service.base.StandardService;
import com.hy.web.action.common.BaseAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({@Result(name="list",type="redirect",location="/pages/base/standard.jsp")})
public class StandardAction extends BaseAction<Standard>{
	
//	private Standard model=new Standard();
//	@Override
//	public Standard getModel() {
//		return model;
//	}
	
	@Autowired
	private StandardService standardService;

	
	@Action("standard_save")
	public String save(){
		
		standardService.save(model);
		
		return "list";
	}
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action("standard_del")
	public String del(){
		
		String[] split = ids.split(",");
		
		for (String str : split) {
			
			int id=Integer.valueOf(str);
			
			standardService.del(id);
		}
		
		return "list";
	}
	
	
//	private int page;
//	private int rows;
//	public void setPage(int page) {
//		this.page = page;
//	}
//	public void setRows(int rows) {
//		this.rows = rows;
//	}
	@Action("standard_findPage")
	public void findPage(){
		
		Pageable pageable=new PageRequest(page,rows);
		
		Page<Standard> sPage = standardService.findGridPage(pageable);
		
		java2json(sPage, null);
		
//		long totalPage = sPage.getTotalElements();
//		List<Standard> list = sPage.getContent();
//		
//		Map<String, Object> map = new HashMap<>();
//		
//		map.put("total",totalPage);
//		map.put("rows",list);
//		
//		String json = JSONObject.fromObject(map).toString();
//		
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("text/json;charset=utf-8");
//		try {
//			response.getWriter().write(json);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@Action("standard_findAll")
	public void findAll(){
		
		List<Standard> list=standardService.findAll();
		java2json(list, null);
		
//		String json = JSONArray.fromObject(list).toString();
//		
//		HttpServletResponse response = ServletActionContext.getResponse();
//		response.setContentType("text/json;charset=utf-8");
//		try {
//			response.getWriter().write(json);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
