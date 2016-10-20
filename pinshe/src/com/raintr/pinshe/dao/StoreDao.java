package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StoreDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<StoreBean> By(int page){
		String key = "store.by" + page;
		if(Cache.stores.containsKey(key))
	    	return Cache.stores.get(key);
		
		List<StoreBean> stores = (List<StoreBean>)getSqlMapClientTemplate().queryForList("store.by", page);
		Cache.stores.put(key, stores);
		return stores;
	}
	
	@SuppressWarnings("unchecked")
	public List<StoreBean> ByIsDelete(int page){
		String key = "store.byIsDelete" + page;
		if(Cache.stores.containsKey(key))
	    	return Cache.stores.get(key);
		
		List<StoreBean> stores = (List<StoreBean>)getSqlMapClientTemplate().queryForList("store.byIsDelete", page);
		Cache.stores.put(key, stores);
		return stores;
	}
	
	@SuppressWarnings("unchecked")
	public List<StoreBean> ByInvaild(int page){
		String key = "store.byInvaild" + page;
		if(Cache.stores.containsKey(key))
	    	return Cache.stores.get(key);
		
		List<StoreBean> stores = (List<StoreBean>)getSqlMapClientTemplate().queryForList("store.byInvaild", page);
		Cache.stores.put(key, stores);
		return stores;
	}
	
	
	public StoreBean ById(int id){
		String key = "store.byId" + id;
		if(Cache.store.containsKey(key)){
			synchronized (Cache.storeObject){
				return Cache.store.get(key);
			}
		}
		
		StoreBean store = (StoreBean)getSqlMapClientTemplate().queryForObject("store.byId", id);
		Cache.store.put(key, store);
		return store;
	}
	
	@SuppressWarnings("unchecked")
	public List<StoreBean> ByMerchantId(int merchantId){
		String key = "store.byMerchantId" + merchantId;
		if(Cache.stores.containsKey(key))
	    	return Cache.stores.get(key);
		
		List<StoreBean> stores = (List<StoreBean>)getSqlMapClientTemplate().queryForList("store.byMerchantId", merchantId);
		Cache.stores.put(key, stores);
		return stores;
	}
	
	
	
	public int Add(StoreBean store){
		int id = (Integer)getSqlMapClientTemplate().insert("store.add", store);
		synchronized (Cache.storeObject){
			Cache.store.clear();
			Cache.stores.clear();
		}
		return id;
	}
	
	public int Modify(StoreBean store){
		int id = (Integer)getSqlMapClientTemplate().update("store.modify", store);
		synchronized (Cache.storeObject){
			Cache.store.clear();
			Cache.stores.clear();
		}
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("store.remove", id);
		synchronized (Cache.storeObject){
			Cache.store.clear();
			Cache.stores.clear();
		}
		return id;
	}
}