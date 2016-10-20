package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.StorePushBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StorePushDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<StorePushBean> By(int page){
		String key = "storePush.by" + page;
		if(Cache.storePushs.containsKey(key))
	    	return Cache.storePushs.get(key);
		
		List<StorePushBean> storePushs = (List<StorePushBean>)getSqlMapClientTemplate().queryForList("storePush.by", page);
		Cache.storePushs.put(key, storePushs);
		return storePushs;
	}
	
	public StorePushBean ById(int id){
		String key = "storePush.byId" + id;
		if(Cache.storePush.containsKey(key))
			return Cache.storePush.get(key);
		
		StorePushBean storePush = (StorePushBean)getSqlMapClientTemplate().queryForObject("storePush.byId", id);
		Cache.storePush.put(key, storePush);
		return storePush;
	}
	
	@SuppressWarnings("unchecked")
	public List<StorePushBean> ByStoreId(int storeId){
		String key = "storePush.byStoreId" + storeId;
		if(Cache.storePushs.containsKey(key))
	    	return Cache.storePushs.get(key);
		
		List<StorePushBean> storePushs = (List<StorePushBean>)getSqlMapClientTemplate().queryForList("storePush.byStoreId", storeId);
		Cache.storePushs.put(key, storePushs);
		return storePushs;
	}
	
	public int Add(StorePushBean storePush){
		int id = (Integer)getSqlMapClientTemplate().insert("storePush.add", storePush);
		Cache.storePush.clear();
		Cache.storePushs.clear();
		return id;
	}
	
	public int Modify(StorePushBean storePush){
		int id = (Integer)getSqlMapClientTemplate().update("storePush.modify", storePush);
		Cache.storePush.clear();
		Cache.storePushs.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("storePush.remove", id);
		Cache.storePush.clear();
		Cache.storePushs.clear();
		return id;
	}
}