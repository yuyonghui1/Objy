package com.hy.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.hy.service.Customer;
import com.hy.service.CustomerService;
import com.hy.utils.MailUtils;
import com.hy.utils.Md5Util;
import com.hy.utils.SMSUtil;
import com.hy.web.action.common.BaseAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
@Controller("customerAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({@Result(name="sign",location="/signup.html"),
		@Result(name="login",location="/login.html"),
		@Result(name="index",type="redirect",location="/index.html")
})
public class CustomerAction extends BaseAction<Customer>{
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Action("customer_findCustomerWithTel")
	public void findCustomerWithTel(){
		try {
			
			Boolean flag=customerService.findCustomerExistWithTel(model.getTelephone());
			
			String str = flag.toString();
			
			ServletActionContext.getResponse().getWriter().write(str);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
	
	@Action("customer_getCheckcode")
	public void getCheckcode(){
		try {
			
			final String checkCode = RandomStringUtils.randomNumeric(4);
			System.out.println("验证码为:"+checkCode);
			String telephone = model.getTelephone();
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			
			session.setAttribute(telephone, checkCode);
			//map封装验证码,用来作为参数,调用发送短信的类
//			HashMap<String, String> map = new HashMap<>();
//			map.put("code", checkCode);
//			Boolean isSend = SMSUtil.sendMsg(telephone,"SMS_130810176",map);
			
			jmsTemplate.send("sms_msg",new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {

					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setString("tel", model.getTelephone());
					mapMessage.setString("templateCode", "SMS_130810176");
					Map<String, String> map = new HashMap<>();
					map.put("code", checkCode);
					mapMessage.setObject("map", map);
					return mapMessage;
				}
			});
			
			ServletActionContext.getResponse().getWriter().write("1");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String checkCode;
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Action(value="customer_saveCustomer",results={@Result(name="success",location="/signup-success.html")})
	public String saveCustomer(){
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		String realCode = (String) session.getAttribute(model.getTelephone());
		
		if(StringUtils.isNoneBlank(realCode)){
			if(realCode.equals(checkCode)){
				//验证码正确
				//密码加密后储存用户
				model.setPassword(Md5Util.encode(model.getPassword()));
				customerService.save(model);
				//移除session中的验证码
				session.removeAttribute(model.getTelephone());

				//生成邮件中需要用的激活码
				String activeCode=UUID.randomUUID().toString();
				//将激活码存放在redis中  --- key:tel  value:code
				redisTemplate.opsForValue().set(model.getTelephone(), activeCode,24,TimeUnit.HOURS);
				//发送邮件
				final String content="恭喜您注册成功,"
						+ "<a href='"+MailUtils.activeUrl+"?telephone="+model.getTelephone()+"&activeCode="+activeCode+"'>点击此处激活</a>";
				
				//MailUtils.sendMail("恭喜注册成功",content, model.getEmail());
				jmsTemplate.send("mail_msg", new MessageCreator() {
					@Override
					public Message createMessage(Session session) throws JMSException {
						MapMessage mapMessage = session.createMapMessage();
						mapMessage.setString("title", "恭喜注册成功");
						mapMessage.setString("content",content);
						mapMessage.setString("mail", model.getEmail());
						return mapMessage;
					}
				});
				
				return SUCCESS;
			}
		}
		return "sign";
	}
	
	private String activeCode;
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	@Action("customer_activeCustomer")
	public void activeCustomer(){
		try {
			
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			if(StringUtils.isNoneBlank(activeCode)&&StringUtils.isNoneBlank(model.getTelephone())){
				//从redis中用 tel 取得正确的  激活码 
				String realCode=redisTemplate.opsForValue().get(model.getTelephone());
				Customer customer=customerService.findCustomerWithTel(model.getTelephone());
				if(customer!=null){
					if(realCode!=null){
						if(realCode.equals(activeCode)){
							if(customer.getType()==null){
								customerService.activeCustomer(customer.getId());
								writer.write("激活成功!");
							}else{
								writer.write("账号已经激活过了");
							}
							
						}else{
							writer.write("激活码错误");
						}
					}else{
						writer.write("手机号错误,无对应激活码");
					}
				}else{
					writer.write("手机号错误,没有对应用户");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Action("customer_login")
	public String login(){
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		String realCode = (String) session.getAttribute("key");
		if(StringUtils.isNotBlank(realCode)){
			if(checkCode.equals(realCode)){
				String telephone = model.getTelephone();
				String password = Md5Util.encode(model.getPassword());
				Customer customer=customerService.login(telephone,password);
				if(customer!=null){
					session.setAttribute("customer", customer);
					
					return "index";
				}
			}
		}
		
		return "login";
	}
}
