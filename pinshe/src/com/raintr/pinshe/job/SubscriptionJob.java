package com.raintr.pinshe.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.ConsigneeBean;
import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.bean.SubscriptionBean;
import com.raintr.pinshe.service.MemberService;
import com.raintr.pinshe.service.OrderDetailService;
import com.raintr.pinshe.service.OrderService;
import com.raintr.pinshe.service.SubscriptionService;

public class SubscriptionJob {
	@Autowired
	private SubscriptionService subscriptionService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private MemberService memberService;
	
	private Log log = LogFactory.getLog("subscription");
	
	public void execute() {
		int page = 0;
		Calendar now;
		OrderBean order;
		MemberBean member;
		ConsigneeBean consignee;
		OrderDetailBean orderDetail;
		CommodityBean commodity = null;
		SubscriptionBean subscription;
		List<SubscriptionBean> subscriptions;
		
		try{
			log.info("start--------------------------");

			while(true){
				log.info(" while start");
				subscriptions = subscriptionService.By(page++ * 100);
				if(subscriptions == null || subscriptions.size() == 0){
					log.info("  subscriptions is null");
					break;
				}
				
				log.info("  subscriptions start");
				for(int i = 0; i < subscriptions.size(); i++){
					log.info("   " + i + " start");
					subscription = subscriptions.get(i);
					if(subscription == null){
						log.info("   " + i + " subscription is null");
						continue;
					}
					
					log.info("   " + i + " subscription id=" + subscription.getId());
					
					if((new Date()).getTime() < subscription.getNext_time().getTime()){
						log.info("   " + i + " now=" + new Date() + " Next_time=" + subscription.getNext_time());
						continue;
					}
					
					consignee = subscription.getConsignee();
					if(consignee == null){
						log.info("   " + i + " consignee is null");
						continue;
					}
					
					if(subscription.getStatus1() == 0){
						commodity = subscription.getCommodity1();
						subscription.setStatus1(1);
					}else{ 
						if(subscription.getStatus2() == 0){
							commodity = subscription.getCommodity2();
							subscription.setStatus2(1);
						}else{
							if(subscription.getStatus3() == 0){
								commodity = subscription.getCommodity3();
								subscription.setStatus3(1);
							}else{
								if(subscription.getStatus4() == 0){
									commodity = subscription.getCommodity4();
									subscription.setStatus4(1);
								}else{
									if(subscription.getStatus5() == 0){
										commodity = subscription.getCommodity5();
										subscription.setStatus5(1);
									}else{ 
										if(subscription.getStatus6() == 0){
											commodity = subscription.getCommodity6();
											subscription.setStatus6(1);
										}
									}
								}
							}
						}
					}
					
					if(commodity == null){
						log.info("   " + i + " commodity is null");
						continue;
					}
					
					member = subscription.getMember();
					
					if(member == null){
						log.info("   " + i + " member is null");
						continue;
					}
					
					if(commodity.getPrice() > member.getCurrent()){
						log.info("   " + i + " money is not enough");
						continue;
					}
					
					// order
					order = new OrderBean();
					order.setMember_id(subscription.getMember_id());
					order.setConsignee_id(consignee.getId());
					order.setOrder_no(order.MadeOrderNo());
					order.setCount(1);
					order.setCurrent(commodity.getPrice());
					order.setAmount(commodity.getPrice());
					order.setStatus(1);
					order.setCreate_time(new Date());
					order.setModify_time(new Date());
					order.setId(orderService.Add(order));
					
					// order detail
					orderDetail = new OrderDetailBean();
					orderDetail.setOrder_id(order.getId());
					orderDetail.setCommodity_id(commodity.getId());
					orderDetail.setStore_id(0);
					orderDetail.setCount(1);
					orderDetail.setAmount(commodity.getPrice());
					orderDetail.setCreate_time(new Date());
					orderDetail.setModify_time(new Date());
					orderDetailService.Add(orderDetail);

					member.setCurrent(member.getCurrent() - commodity.getPrice());
					memberService.Modify(member);
					
					now = Calendar.getInstance();
					now.add(Calendar.DAY_OF_YEAR, subscription.getDay());
					subscription.setNext_time(now.getTime());
					
					subscriptionService.Modify(subscription);
					
					log.info("   " + i + " ended");
				}
				log.info("  subscriptions ended");
			}
			log.info(" while ended");
		}catch(Exception e){
			log.info("exception start");
			log.info(e.toString());
			e.printStackTrace();
			log.info("exception ended");
		}finally{
			log.info("ended--------------------------");
		}
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		CachedIntrospectionResults.acceptClassLoader(Thread.currentThread().getContextClassLoader());  
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext.xml");
		((SubscriptionJob)context.getBean("subscriptionJob")).execute();
	}
}