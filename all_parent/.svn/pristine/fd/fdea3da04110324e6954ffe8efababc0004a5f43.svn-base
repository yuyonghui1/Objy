package com.hy.sms;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.hy.utils.MailUtils;
import com.hy.utils.SMSUtil;
@Component("mailListene")
public class MailListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		try {
			MapMessage mapMessage=(MapMessage) message;
			String title = mapMessage.getString("title");
			String content = mapMessage.getString("content");
			String mail = mapMessage.getString("mail");
			MailUtils.sendMail(title,content,mail);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
