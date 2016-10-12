package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.StoreCashBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StoreCashDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<StoreCashBean> By(int page){
		String key = "storeCash.by" + page;
		if(Cache.storeCashs.containsKey(key))
	    	return Cache.storeCashs.get(key);
		
		List<StoreCashBean> storeCashs = (List<StoreCashBean>)getSqlMapClientTemplate().queryForList("storeCash.by", page);
		Cache.storeCashs.put(key, storeCashs);
		return storeCashs;
	}
	
	public StoreCashBean ById(int id){
		String key = "storeCash.byId" + id;
		if(Cache.storeCash.containsKey(key))
			return Cache.storeCash.get(key);
		
		StoreCashBean storeCash = (StoreCashBean)getSqlMapClientTemplate().queryForObject("storeCash.byId", id);
		Cache.storeCash.put(key, storeCash);
		return storeCash;
	}
	
	public StoreCashBean ByOrderId(int orderId){
		String key = "storeCash.byOrderId" + orderId;
		if(Cache.storeCash.containsKey(key))
			return Cache.storeCash.get(key);
		
		StoreCashBean storeCash = (StoreCashBean)getSqlMapClientTemplate().queryForObject("storeCash.byOrderId", orderId);
		Cache.storeCash.put(key, storeCash);
		return storeCash;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<StoreCashBean> ByStoreId(int storeId, int page){
		String key = "storeCash.byStoreId" + storeId + page;
		if(Cache.storeCashs.containsKey(key))
	    	return Cache.storeCashs.get(key);
		
		StoreCashBean storeCash = new StoreCashBean();
		storeCash.setId(page);
		storeCash.setStore_id(storeId);
		List<StoreCashBean> storeCashs = (List<StoreCashBean>)getSqlMapClientTemplate().queryForList("storeCash.byStoreId", storeCash);
		Cache.storeCashs.put(key, storeCashs);
		return storeCashs;
	}

	@SuppressWarnings("unchecked")
	public List<StoreCashBean> ByType(int type, int page){
		String key = "storeCash.byType" + type + page;
		if(Cache.storeCashs.containsKey(key))
	    	return Cache.storeCashs.get(key);
		
		StoreCashBean storeCash = new StoreCashBean();
		storeCash.setId(page);
		storeCash.setType(type);
		List<StoreCashBean> storeCashs = (List<StoreCashBean>)getSqlMapClientTemplate().queryForList("storeCash.byType", storeCash);
		Cache.storeCashs.put(key, storeCashs);
		return storeCashs;
	}
	
	public int Add(StoreCashBean storeCash){
		int id = (Integer)getSqlMapClientTemplate().insert("storeCash.add", storeCash);
		Cache.storeCash.clear();
		Cache.storeCashs.clear();
		return id;
	}
	
	public int Modify(StoreCashBean storeCash){
		int id = (Integer)getSqlMapClientTemplate().update("storeCash.modify", storeCash);
		Cache.storeCash.clear();
		Cache.storeCashs.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("storeCash.remove", id);
		Cache.storeCash.clear();
		Cache.storeCashs.clear();
		return id;
	}
}