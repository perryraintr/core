package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class OrderDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<OrderBean> By(int page){
		String key = "order.by" + page;
		if(Cache.orders.containsKey(key))
	    	return Cache.orders.get(key);
		
		List<OrderBean> orders = (List<OrderBean>)getSqlMapClientTemplate().queryForList("order.by", page);
		Cache.orders.put(key, orders);
		return orders;
	}
	
	public OrderBean ById(int id){
		String key = "order.byId" + id;
		if(Cache.order.containsKey(key))
			return Cache.order.get(key);
		
		OrderBean order = (OrderBean)getSqlMapClientTemplate().queryForObject("order.byId", id);
		Cache.order.put(key, order);
		return order;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderBean> ByMemberId(int memberId, int page){
		String key = "order.byMemberId" + memberId + page;
		if(Cache.orders.containsKey(key))
	    	return Cache.orders.get(key);
		
		OrderBean order = new OrderBean();
		order.setId(page);
		order.setMember_id(memberId);
		List<OrderBean> orders = (List<OrderBean>)getSqlMapClientTemplate().queryForList("order.byMemberId", order);
		Cache.orders.put(key, orders);
		return orders;
	}
	
	public OrderBean ByOrderNo(String orderNo){
		String key = "order.byOrderNo" + orderNo;
		if(Cache.order.containsKey(key))
			return Cache.order.get(key);
		
		OrderBean order = (OrderBean)getSqlMapClientTemplate().queryForObject("order.byOrderNo", orderNo);
		Cache.order.put(key, order);
		return order;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderBean> ByStoreId(int storeId, int page){
		String key = "order.byStoreId" + storeId + page;
		if(Cache.orders.containsKey(key))
	    	return Cache.orders.get(key);
		
		OrderDetailBean orderDetail = new OrderDetailBean();
		orderDetail.setId(page);
		orderDetail.setStore_id(storeId);
		List<OrderBean> orders = (List<OrderBean>)getSqlMapClientTemplate().queryForList("order.byStoreId", orderDetail);
		Cache.orders.put(key, orders);
		return orders;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderBean> ByStore(int page){
		String key = "order.byStore" + page;
		if(Cache.orders.containsKey(key))
	    	return Cache.orders.get(key);
		
		List<OrderBean> orders = (List<OrderBean>)getSqlMapClientTemplate().queryForList("order.byStore", page);
		Cache.orders.put(key, orders);
		return orders;
	}
	
	@SuppressWarnings("unchecked")
	public List<OrderBean> ByStatus(int stauts){
		String key = "order.byStatus" + stauts;
		if(Cache.orders.containsKey(key))
	    	return Cache.orders.get(key);
		
		List<OrderBean> orders = (List<OrderBean>)getSqlMapClientTemplate().queryForList("order.byStatus", stauts);
		Cache.orders.put(key, orders);
		return orders;
	}
	
	public int Add(OrderBean order){
		int id = (Integer)getSqlMapClientTemplate().insert("order.add", order);
		Cache.order.clear();
		Cache.orders.clear();
		return id;
	}
	
	public int Modify(OrderBean order){
		int id = (Integer)getSqlMapClientTemplate().update("order.modify", order);
		Cache.order.clear();
		Cache.orders.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("order.remove", id);
		Cache.order.clear();
		Cache.orders.clear();
		return id;
	}
}