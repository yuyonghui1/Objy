package com.hy.web.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hy.dao.system.PermissionDao;
import com.hy.dao.system.RoleDao;
import com.hy.dao.system.UserDao;
import com.hy.domain.system.Permission;
import com.hy.domain.system.Role;
import com.hy.domain.system.User;
@Component("bosRealm")
public class BosRealm extends AuthorizingRealm{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PermissionDao permissionDao;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken utoken=(UsernamePasswordToken) token;
		
		String username = utoken.getUsername();
		
		User user=userDao.findByUsername(username);
		
		if(user==null){
			return null;
		}
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		
		return info;
	}

	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		SimpleAuthorizationInfo info =new  SimpleAuthorizationInfo();
		
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		
		List<Role> roleList=new ArrayList<>();
		List<Permission> permissionList=new ArrayList<>();
		
		if(user.getUsername().equals("admin")){
			//Admin全部权限都有
			roleList=roleDao.findAll();
			permissionList=permissionDao.findAll();
		}else{
			//根据用户id查找权限和角色
			roleList=roleDao.findByUserId(user.getId());
			permissionList=permissionDao.findByUserId(user.getId());
		}
		
		for (Role role : roleList) {
			info.addRole(role.getKeyword());
		}
		for (Permission permission : permissionList) {
			info.addStringPermission(permission.getKeyword());
		}
		
		
		//info.addRole("admin");
		//info.addStringPermission("saveWayBill");
		return info;
	}

}
