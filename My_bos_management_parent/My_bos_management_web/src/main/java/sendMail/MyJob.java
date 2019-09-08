package sendMail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.ServletContextResource;

import com.hy.dao.take_delivery.WorkBillDao;
import com.hy.domain.take_delivery.WorkBill;

public class MyJob {
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@Autowired
	private WorkBillDao workBillDao;
	
	public void send(){
		try {
			List<WorkBill> workBillList = workBillDao.findAll();
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
			helper.setTo("88546914@qq.com");
			helper.setFrom(mailSender.getUsername());
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			helper.setSubject(dateFormat.format(new Date())+"日工单");
			
			String content="";
			
			for (WorkBill workBill : workBillList) {
				
				content+="<tr><td>"+workBill.getId()+"</td><td>"+workBill.getPickstate()+"</td><td>"
					+dateFormat.format(workBill.getBuildtime()==null?new Date():workBill.getBuildtime())
					+"</td><td>"+workBill.getCourier().getName()+"</td></tr>";
				
			}
			
			helper.setText("<!DOCTYPE html><html><body>" + "<table border='1' bordercolor='#000000' style='width: 99%'>"
					+ "<tr>" + "<th colspan='4'>" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "</th>"
					+ "</tr><tr>" + "<td>工单编号</td>" + "<td>取件状态</td>" + "<td>生成时间</td>" + "<td>快递员</td></tr>" 
					+ content
					+ "</table>" + "<br><br><br>" + "<img src='cid:file'/></body></html>", true);
			
			
			WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
			ServletContext servletContext = applicationContext.getServletContext();
			ServletContextResource resource = new ServletContextResource(servletContext,"/xxx.jpg");
			
			helper.addInline("file", resource);
			
			mailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
