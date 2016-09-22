package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.dao.OrderDetailDao;

public class OrderDetailService {
	private OrderDetailDao orderDetailDao;
//	private MemberDao memberDao;

	public List<OrderDetailBean> By(int page){	
//		OrderDetailDaoBean order;
		List<OrderDetailBean> orders = orderDetailDao.By(page);
//		for(int i = 0; i < orders.size(); i++){
//			order = orders.get(i);
//			if(order != null){
//				if(order.getMember_id() > 0)
//					order.setMemeber(memberDao.ById(order.getMember_id()));
//			}
//		}
		return orders;
	}
	
	public OrderDetailBean ById(int id){
		return orderDetailDao.ById(id);
	}
	
	public List<OrderDetailBean> ByOrderId(int orderId){	
		return orderDetailDao.ByOrderId(orderId);
	}
	
	public int Add(OrderDetailBean order){
		return orderDetailDao.Add(order);
	}
	
	public int Modify(OrderDetailBean order){
		return orderDetailDao.Modify(order);
	}
	
	public int Remove(int id){
		return orderDetailDao.Remove(id);
	}
	

	public OrderDetailDao getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}
}
