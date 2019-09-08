package com.hy.web.action.system;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

import com.hy.domain.system.User;
import com.hy.service.system.UserService;
import com.hy.utils.Md5Util;
import com.hy.web.action.common.BaseAction;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({@Result(name="index",type="redirect",location="/my/index.jsp"),
		@Result(name="login",location="/login.jsp"),
		@Result(name="list",location="/pages/system/user.jsp")
})
public class UserAction extends BaseAction<User>{

	@Autowired
	private UserService userService;
	
	private String checkCode;
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Action("user_login")
	public String login(){
		
		String realCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		
		if(StringUtils.isNotBlank(checkCode)&&StringUtils.isNotBlank(realCode)){
			
			Subject subject = SecurityUtils.getSubject();
			
			UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(),Md5Util.encode(model.getPassword()));
			
			try {
				subject.login(token);
				
				User user = (User) subject.getPrincipal();
				
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				
				return "index";
			} catch (UnknownAccountException e) {
				//账号错误
				System.out.println("账号错误");
				this.addActionError("账号错误");
			}catch (IncorrectCredentialsException e){
				//密码错误
				System.out.println("密码错误");
				this.addActionError("密码错误");
			}
			
		}
		
		return "login";
	}
	
	@Action("user_logout")
	public String logout(){
		
		SecurityUtils.getSubject().logout();
		
		return "login";
	}
	
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	@Action("user_save")
	public String save(){
		model.setPassword(Md5Util.encode(model.getPassword()));
		userService.save(model,roleIds);
		
		return "list";
	}
	
	@Action("user_findPage")
	public void findPage(){
		
		Pageable pageable = new PageRequest(page, rows);
		
		Page<User> page = userService.findPage(pageable);
		
		java2json(page, new String[]{"roles","birthday","hibernateLazyInitializer"});
	}
	
}
