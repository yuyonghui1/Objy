package com.hy.web.action.common;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	
	protected T model;
	
	@Override
	public T getModel() {
		return model;
	}

	public BaseAction() {

		Class clazz = this.getClass();
		
		Type type = clazz.getGenericSuperclass();
		ParameterizedType parameterizedType=(ParameterizedType) type;
		Type[] types = parameterizedType.getActualTypeArguments();
		Class tClass = (Class) types[0];
		
		try {
			model=(T) tClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected int rows;
	protected int page;
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setPage(int page) {
		this.page = page-1;
	}
	
	
	public void java2json(Page page,String[] excludes){
		long total = page.getTotalElements();
		List list = page.getContent();
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("rows",list);
		map.put("total",total);
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(excludes);
		String json = JSONObject.fromObject(map,config).toString();
		
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			
			response.setContentType("text/json;charset=utf-8");
			
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void java2json(List list,String[] excludes){
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(excludes);
		String json = JSONArray.fromObject(list,config).toString();
		
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			
			response.setContentType("text/json;charset=utf-8");
			
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
