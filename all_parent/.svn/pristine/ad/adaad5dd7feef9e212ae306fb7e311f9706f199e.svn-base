package com.hy.sms;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

import com.hy.utils.SMSUtil;
@Component("SMSListener")
public class SMSListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		try {
			MapMessage mapMessage=(MapMessage) message;
			String tel = mapMessage.getString("tel");
			String templateCode = mapMessage.getString("templateCode");
			Map<String,String> map = (Map<String,String>) mapMessage.getObject("map");
			
			Boolean b=SMSUtil.sendMsg(tel, templateCode, map);
			System.out.println(b);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
