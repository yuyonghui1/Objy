package com.hy.web.action.system;

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

import com.hy.domain.system.Permission;
import com.hy.service.system.PermissionService;
import com.hy.web.action.common.BaseAction;
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Results({@Result(name="list",type="redirect",location="/pages/system/permission.jsp")})
public class PermissionAction extends BaseAction<Permission>{

	@Autowired
	private PermissionService permissionService;
	
	@Action("permission_save")
	public String save(){
		
		permissionService.save(model);
		
		return "list";
	}
	
	@Action("permission_findAll")
	public void findAll(){
		
		List<Permission> list = permissionService.findAll();
		
		java2json(list, new String[]{"roles"});
		
	}
	
}
