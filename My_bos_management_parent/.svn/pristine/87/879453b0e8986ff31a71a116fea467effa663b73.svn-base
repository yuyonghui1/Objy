package com.hy.service.take_delivery;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hy.dao.base.AreaDao;
import com.hy.dao.base.CourierDao;
import com.hy.dao.base.FixedAreaDao;
import com.hy.dao.take_delivery.OrderDao;
import com.hy.dao.take_delivery.WorkBillDao;
import com.hy.domain.base.Area;
import com.hy.domain.base.Courier;
import com.hy.domain.base.FixedArea;
import com.hy.domain.base.SubArea;
import com.hy.domain.take_delivery.Order;
import com.hy.domain.take_delivery.WorkBill;
import com.hy.service.CustomerService;
import com.hy.utils.SMSUtil;


@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CourierDao courierDao;
	@Autowired
    private WorkBillDao workBillDao;
	
	
	@Override
	public void save(Order order) {
		
		order.setOrderTime(new Date());
		order.setOrderNum(UUID.randomUUID().toString());
		
		Area sendArea = order.getSendArea();
		Area recArea = order.getRecArea();
		if(sendArea!=null){
			//之所以返回list,因为数据库有问题,有重复元素
			List<Area> sendAreaList = areaDao.findByProvinceAndCityAndDistrict(sendArea.getProvince(),sendArea.getCity(),sendArea.getDistrict());
			sendArea=sendAreaList.get(0);
			order.setSendArea(sendArea);
		}
		if(recArea!=null){
			
			List<Area> recAreaAreaList = areaDao.findByProvinceAndCityAndDistrict(recArea.getProvince(),recArea.getCity(),recArea.getDistrict());
			recArea=recAreaAreaList.get(0);
			order.setRecArea(recArea);
		}
		
		orderDao.save(order);
		
		String sendAddress = order.getSendAddress();
//策略一:根据已有客户地址找定区
		String fixedAreaId=customerService.findByAddress(sendAddress);
		
		autoOrder(fixedAreaId, order);

//策略二:根据用户地址关键字找定区	
		
		if(order.getSendArea()!=null){
			Area sendAre = order.getSendArea();
			Set<SubArea> subareas = sendAre.getSubareas();
			for (SubArea subArea : subareas) {
				if(sendAddress.contains(subArea.getAssistKeyWords())||
				sendAddress.contains(subArea.getKeyWords())){
					
					FixedArea fixedArea = subArea.getFixedArea();
					autoOrder(fixedArea.getId(),order);
				}
			}
		}
//无法自动分单---转人工		
		order.setOrderType("人工分单");
	}
	@Autowired
	@Qualifier("jmsQueueTemplate")
	private JmsTemplate jmsTemplate;
	
	public  void autoOrder(String fixedAreaId,final Order order){
		if(StringUtils.isNotBlank(fixedAreaId)){
			//按工单从少到多的顺序查询到快递员id
			List<Object> courierIds = courierDao.findFreeCourierWithFixedId(fixedAreaId);
			if(courierIds!=null&&courierIds.size()>0){
				for (Object courierId : courierIds) {
					System.out.println(courierId.toString());
					final Courier courier = courierDao.findOne(Integer.parseInt(courierId.toString()));
					//TODO 可以判断是否在工作时间
					WorkBill workBill = new WorkBill();
					workBill.setType("新");
					workBill.setCourier(courier);
					workBill.setOrder(order);
					workBill.setAttachbilltimes(0);
					workBill.setBuildtime(new Date());
					workBill.setPickstate("待取件");
					workBill.setRemark(order.getRemark());
					
					//给快递员发送短信
					jmsTemplate.send("sms_msg", new MessageCreator() {
						@Override
						public Message createMessage(Session session) throws JMSException {
							MapMessage mapMessage = session.createMapMessage();
							mapMessage.setString("tel",courier.getTelephone());
							mapMessage.setString("templateCode","SMS_130835423");
							
							Map<String, String> templateParams = new HashMap<>();
							templateParams.put("customerAddress", order.getSendAddress());
							templateParams.put("customerName", order.getSendName());
							templateParams.put("customerTelephone", order.getSendMobile());
							templateParams.put("customerRemark", order.getSendMobileMsg());
							
							mapMessage.setObject("map", templateParams);
							return mapMessage;
						}
					});
					//Boolean flag = SMSUtil.sendMsg(courier.getTelephone(), "SMS_130835423", templateParams);
					//workBill.setSmsNumber(flag.toString());
					
					workBillDao.save(workBill);
					
					order.setCourier(courier);
					order.setStatus("待取件");
					order.setOrderType("自动分单");
					
					return ;
				}
			}
		}
	}
}
