package com.hy.web.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hy.domain.system.Menu;
import com.hy.domain.system.User;
import com.hy.service.system.MenuService;
import com.hy.web.action.common.BaseAction;
@Controller("menuAction")
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Results({@Result(name="list",type="redirect",location="/pages/system/menu.jsp")})
public class MenuAction extends BaseAction<Menu>{

	@Autowired
	private MenuService menuService;
	
	
	@Action("menu_findTree")
	public void findTree(){
		
		List<Menu> list = menuService.findTree();
		
		java2json(list, new String[]{"parentMenu","roles","childrenMenus"});
		
	}
	
	@Action("menu_findSimpleTree")
	public void findSimpleTree(){
		
		List<Menu> list;
		
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if(user.getUsername().equals("admin")){
			list = menuService.findAll();
		}else{
			Integer userId = user.getId();
			list = menuService.findByUserId(userId);
		}
		
		java2json(list, new String[]{"parentMenu","roles","children","childrenMenus"});
		
	}
	
	@Action("menu_save")
	public String save(){
		if(model.getParentMenu()!=null&&model.getParentMenu().getId()==null){
			model.setParentMenu(null);
		}
		menuService.save(model);
		return "list";
	}
}
