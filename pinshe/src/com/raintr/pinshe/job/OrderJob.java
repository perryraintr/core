package com.raintr.pinshe.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.CachedIntrospectionResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.service.CommodityService;
import com.raintr.pinshe.service.OrderService;

public class OrderJob {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CommodityService commodityService;
	
	private Log log = LogFactory.getLog("order");
	
	public void execute() {
		int page = 0;
		OrderBean order;
		List<OrderBean> orders;
		CommodityBean commodity;
		OrderDetailBean orderDetail;
		List<OrderDetailBean> orderDetails;
		Date now;
		
		try{
			log.info("start--------------------------");

			while(true){
				log.info(" while start");
				orders = orderService.By(page++ * 100);
				if(orders == null || orders.size() == 0){
					log.info("  orders is null");
					break;
				}
				
				log.info("  orders start");
				for(int i = 0; i < orders.size(); i++){
					log.info("   " + i + " start");
					
					order = orders.get(i);
					if(order == null){
						log.info("   " + i + " order is null");
						continue;
					}
					
					log.info("   " + i + " order id=" + order.getId());
					
					if(order.getStatus() > 0){
						log.info("   " + i + " order.getStatus() is " + order.getStatus());
						continue;
					}

					now = new Date();
					if(now.getTime() < order.getCreate_time().getTime() + 300000){
						log.info("   " + i + " now.getTime() < order.getCreate_time().getTime() + 300000 is " + now + " " + order.getCreate_time());
						continue;
					}
					
					
					orderDetails = order.getOrderDetails();
					if(orderDetails == null || orderDetails.size() == 0){
						log.info("  orderDetails is null");
						break;
					}
					
					log.info("    orderDetails start");
					for(int j = 0; j < orderDetails.size(); j++){
						log.info("     " + j + " start");
						orderDetail = orderDetails.get(j);
						if(orderDetail == null){
							log.info("     " + j + " order is null");
							continue;
						}
						
						log.info("     " + j + " orderDetail id=" + orderDetail.getId());
						
						commodity = orderDetail.getCommodity();
						if(commodity == null){
							log.info("     " + j + " commodity is null");
							continue;
						}
						
						commodity.setCount(commodity.getCount() + orderDetail.getCount());
						commodityService.Modify(commodity);
						log.info("     " + j + " ended");
					}
					log.info("    orderDetails ended");

					order.setStatus(4);
					orderService.Modify(order);
					
					log.info("   " + i + " ended");
				}

				log.info("  orders ended");
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
		((OrderJob)context.getBean("orderJob")).execute();
	}
}