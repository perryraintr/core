package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.CashBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class CashDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<CashBean> By(int page){
		String key = "cash.by" + page;
		if(Cache.cashs.containsKey(key))
	    	return Cache.cashs.get(key);
		
		List<CashBean> cashs = (List<CashBean>)getSqlMapClientTemplate().queryForList("cash.by", page);
		Cache.cashs.put(key, cashs);
		return cashs;
	}
	
	public CashBean ById(int id){
		String key = "cash.byId" + id;
		if(Cache.cash.containsKey(key))
			return Cache.cash.get(key);
		
		CashBean cash = (CashBean)getSqlMapClientTemplate().queryForObject("cash.byId", id);
		Cache.cash.put(key, cash);
		return cash;
	}
	
	public CashBean ByOrderId(int orderId){
		String key = "cash.byOrderId" + orderId;
		if(Cache.cash.containsKey(key))
			return Cache.cash.get(key);
		
		CashBean cash = (CashBean)getSqlMapClientTemplate().queryForObject("cash.byOrderId", orderId);
		Cache.cash.put(key, cash);
		return cash;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CashBean> ByStoreId(int storeId, int page){
		String key = "cash.byStoreId" + storeId + page;
		if(Cache.cashs.containsKey(key))
	    	return Cache.cashs.get(key);
		
		CashBean cash = new CashBean();
		cash.setId(page);
		cash.setStore_id(storeId);
		List<CashBean> cashs = (List<CashBean>)getSqlMapClientTemplate().queryForList("cash.byStoreId", cash);
		Cache.cashs.put(key, cashs);
		return cashs;
	}
	
	public int Add(CashBean cash){
		int id = (Integer)getSqlMapClientTemplate().insert("cash.add", cash);
		Cache.cash.clear();
		Cache.cashs.clear();
		return id;
	}
	
	public int Modify(CashBean cash){
		int id = (Integer)getSqlMapClientTemplate().update("cash.modify", cash);
		Cache.cash.clear();
		Cache.cashs.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("cash.remove", id);
		Cache.cash.clear();
		Cache.cashs.clear();
		return id;
	}
}