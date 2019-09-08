package com.hy.web.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hy.domain.system.Role;
import com.hy.service.system.RoleService;
import com.hy.web.action.common.BaseAction;
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Results({@Result(name="list",type="redirect",location="/pages/system/role.jsp")})
public class RoleAction extends BaseAction<Role>{
	
	@Autowired
	private RoleService roleService;
	
	private String[] permissionIds;
	private String menuIds;
	public void setPermissionIds(String[] permissionIds) {
		this.permissionIds = permissionIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}


	@Action("role_save")
	public String save(){
		
		roleService.save(model,permissionIds,menuIds);
		
		return "list";
	}
	
	@Action("role_findAll")
	public void findAll(){
		
		List<Role> list = roleService.findAll();
		
		java2json(list, new String[]{"users","permissions","menus"});
	}
	
	
}
