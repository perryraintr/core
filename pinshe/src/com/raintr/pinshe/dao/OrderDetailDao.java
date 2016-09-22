package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class OrderDetailDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<OrderDetailBean> By(int page){
		String key = "orderDetail.by" + page;
		if(Cache.orderDetails.containsKey(key))
	    	return Cache.orderDetails.get(key);
		
		List<OrderDetailBean> orderDetails = (List<OrderDetailBean>)getSqlMapClientTemplate().queryForList("orderDetail.by", page);
		Cache.orderDetails.put(key, orderDetails);
		return orderDetails;
	}
	
	public OrderDetailBean ById(int id){
		String key = "orderDetail.byId" + id;
		if(Cache.orderDetail.containsKey(key))
			return Cache.orderDetail.get(key);
		
		OrderDetailBean orderDetail = (OrderDetailBean)getSqlMapClientTemplate().queryForObject("orderDetail.byId", id);
		Cache.orderDetail.put(key, orderDetail);
		return orderDetail;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderDetailBean> ByOrderId(int orderId){
		String key = "orderDetail.byOrderId" + orderId;
		if(Cache.orderDetails.containsKey(key))
	    	return Cache.orderDetails.get(key);

		List<OrderDetailBean> orderDetails = (List<OrderDetailBean>)getSqlMapClientTemplate().queryForList("orderDetail.byOrderId", orderId);
		Cache.orderDetails.put(key, orderDetails);
		return orderDetails;
	}
	
	public int Add(OrderDetailBean orderDetail){
		int id = (Integer)getSqlMapClientTemplate().insert("orderDetail.add", orderDetail);
		Cache.orderDetail.clear();
		Cache.orderDetails.clear();
		return id;
	}
	
	public int Modify(OrderDetailBean orderDetail){
		int id = (Integer)getSqlMapClientTemplate().update("orderDetail.modify", orderDetail);
		Cache.orderDetail.clear();
		Cache.orderDetails.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("orderDetail.remove", id);
		Cache.orderDetail.clear();
		Cache.orderDetails.clear();
		return id;
	}
}