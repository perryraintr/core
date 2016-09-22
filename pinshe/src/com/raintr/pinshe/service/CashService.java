package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.CashBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.dao.CashDao;
import com.raintr.pinshe.dao.MemberDao;
import com.raintr.pinshe.dao.OrderDao;
import com.raintr.pinshe.dao.StoreDao;

public class CashService {
	private CashDao cashDao;
	private StoreDao storeDao;
	private OrderDao orderDao;
	private MemberDao memberDao;
	
	public List<CashBean> By(int page){
		CashBean cash;
		StoreBean store;
		OrderBean order;
		List<CashBean> cashs = cashDao.By(page);
		if(cashs != null && cashs.size() > 0){
			for(int i = 0; i < cashs.size(); i++){
				cash = cashs.get(i);
				
				if(cash != null){
					cash.setMember(memberDao.ById(cash.getMember_id()));
					
					cash.setStore(storeDao.ById(cash.getStore_id()));
					store = cash.getStore();
					if(store != null)
						store.setMember(memberDao.ById(store.getMember_id()));
					
					cash.setOrder(orderDao.ById(cash.getOrder_id()));
					order = cash.getOrder();
					if(order != null)
						order.setMember(memberDao.ById(order.getMember_id()));
				}
			}
		}
		return cashs;
	}
	
	public CashBean ById(int id){
		CashBean cash = cashDao.ById(id);
		if(cash != null){
			cash.setMember(memberDao.ById(cash.getMember_id()));
			
			cash.setStore(storeDao.ById(cash.getStore_id()));
			StoreBean store = cash.getStore();
			if(store != null)
				store.setMember(memberDao.ById(store.getMember_id()));
			
			cash.setOrder(orderDao.ById(cash.getOrder_id()));
			OrderBean order = cash.getOrder();
			if(order != null)
				order.setMember(memberDao.ById(order.getMember_id()));
			
			return cash;
		}
		
		return null;
	}
	
	public CashBean ByOrderId(int orderId){
		CashBean cash = cashDao.ByOrderId(orderId);
		if(cash != null){
			cash.setMember(memberDao.ById(cash.getMember_id()));
			
			cash.setStore(storeDao.ById(cash.getStore_id()));
			StoreBean store = cash.getStore();
			if(store != null)
				store.setMember(memberDao.ById(store.getMember_id()));
			
			cash.setOrder(orderDao.ById(cash.getOrder_id()));
			OrderBean order = cash.getOrder();
			if(order != null)
				order.setMember(memberDao.ById(order.getMember_id()));
			
			return cash;
		}
		
		return null;
	}
	
	public List<CashBean> ByStoreId(int storeId, int page){
		CashBean cash;
		StoreBean store;
		OrderBean order;
		List<CashBean> cashs = cashDao.ByStoreId(storeId, page);
		if(cashs != null && cashs.size() > 0){
			for(int i = 0; i < cashs.size(); i++){
				cash = cashs.get(i);
				if(cash != null){
					cash.setMember(memberDao.ById(cash.getMember_id()));
					
					cash.setStore(storeDao.ById(cash.getStore_id()));
					store = cash.getStore();
					if(store != null)
						store.setMember(memberDao.ById(store.getMember_id()));
					
					cash.setOrder(orderDao.ById(cash.getOrder_id()));
					order = cash.getOrder();
					if(order != null)
						order.setMember(memberDao.ById(order.getMember_id()));
				}
			}
		}
		return cashs;
	}
	
	public int Add(CashBean cash){
		return cashDao.Add(cash);
	}
	
	public int Modify(CashBean cash){
		return cashDao.Modify(cash);
	}
	
	public int Remove(int id){
		return cashDao.Remove(id);
	}

	
	
	
	public CashDao getCashDao() {
		return cashDao;
	}

	public void setCashDao(CashDao cashDao) {
		this.cashDao = cashDao;
	}

	public StoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
}
