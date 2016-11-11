package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.StorePaymentBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StorePaymentDao extends SqlMapClientDaoSupport {
	public StorePaymentBean ById(int id){
		String key = "storePayment.byId" + id;
		if(Cache.storePayment.containsKey(key))
			return Cache.storePayment.get(key);
		
		StorePaymentBean storePayment = (StorePaymentBean)getSqlMapClientTemplate().queryForObject("storePayment.byId", id);
		Cache.storePayment.put(key, storePayment);
		return storePayment;
	}
	
	@SuppressWarnings("unchecked")
	public List<StorePaymentBean> ByStoreId(int storeId){
		String key = "storePayment.byStoreId" + storeId;
		if(Cache.storePayments.containsKey(key))
	    	return Cache.storePayments.get(key);
		
		List<StorePaymentBean> storePayments = (List<StorePaymentBean>)getSqlMapClientTemplate().queryForList("storePayment.byStoreId", storeId);
		Cache.storePayments.put(key, storePayments);
		return storePayments;
	}
	
	public int Add(StorePaymentBean storePayment){
		int id = (Integer)getSqlMapClientTemplate().insert("storePayment.add", storePayment);
		Cache.storePayment.clear();
		Cache.storePayments.clear();
		return id;
	}
	
	public int Modify(StorePaymentBean storePayment){
		int id = (Integer)getSqlMapClientTemplate().update("storePayment.modify", storePayment);
		Cache.storePayment.clear();
		Cache.storePayments.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("storePayment.remove", id);
		Cache.storePayment.clear();
		Cache.storePayments.clear();
		return id;
	}
}