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
	
	public StoreBean ById(int id){
		String key = "store.byId" + id;
		if(Cache.store.containsKey(key))
	    	return Cache.store.get(key);
		
		StoreBean store = (StoreBean)getSqlMapClientTemplate().queryForObject("store.byId", id);
		Cache.store.put(key, store);
		return store;
	}
	
	public StoreBean ByMemberId(int memberId){
		String key = "store.byMemberId" + memberId;
		if(Cache.store.containsKey(key))
	    	return Cache.store.get(key);
		
		StoreBean store = (StoreBean)getSqlMapClientTemplate().queryForObject("store.byMemberId", memberId);
		Cache.store.put(key, store);
		return store;
	}
	
	
	
	public int Add(StoreBean store){
		int id = (Integer)getSqlMapClientTemplate().insert("store.add", store);
		Cache.store.clear();
		Cache.stores.clear();
		return id;
	}
	
	public int Modify(StoreBean store){
		int id = (Integer)getSqlMapClientTemplate().update("store.modify", store);
		Cache.store.clear();
		Cache.stores.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("store.remove", id);
		Cache.store.clear();
		Cache.stores.clear();
		return id;
	}
}