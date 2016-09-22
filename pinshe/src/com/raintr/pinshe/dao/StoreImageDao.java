package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.StoreImageBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StoreImageDao extends SqlMapClientDaoSupport {
	public StoreImageBean ById(int id){
		String key = "storeImage.byId" + id;
		if(Cache.storeImage.containsKey(key))
	    	return Cache.storeImage.get(key);
		
		StoreImageBean storeImage = (StoreImageBean)getSqlMapClientTemplate().queryForObject("storeImage.byId", id);
		Cache.storeImage.put(key, storeImage);
		return storeImage;
	}
	
	@SuppressWarnings("unchecked")
	public List<StoreImageBean> ByStoreId(int storeId){
		String key = "storeImage.byStoreId" + storeId;
		if(Cache.storeImages.containsKey(key))
	    	return Cache.storeImages.get(key);
		
		List<StoreImageBean> storeImages = (List<StoreImageBean>)getSqlMapClientTemplate().queryForList("storeImage.byStoreId", storeId);
		Cache.storeImages.put(key, storeImages);
		return storeImages;
	}
	
	public int Add(StoreImageBean storeImage){
		int id = (Integer)getSqlMapClientTemplate().insert("storeImage.add", storeImage);
		Cache.storeImage.clear();
		Cache.storeImages.clear();
		return id;
	}
	
	public int Modify(StoreImageBean storeImage){
		int id = (Integer)getSqlMapClientTemplate().update("storeImage.modify", storeImage);
		Cache.storeImage.clear();
		Cache.storeImages.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("storeImage.remove", id);
		Cache.storeImage.clear();
		Cache.storeImages.clear();
		return id;
	}
}