package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.StoreFeature2Bean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StoreFeature2Dao extends SqlMapClientDaoSupport {
	public StoreFeature2Bean byStoreFeature2(int storeId, int storeFeature2ImageId){
		String key = "storeFeature2.byStoreFeature2" + storeId + storeFeature2ImageId;
		if(Cache.storeFeature2.containsKey(key))
			return Cache.storeFeature2.get(key);
		
		StoreFeature2Bean storeFeature2 = new StoreFeature2Bean();
		storeFeature2.setStore_id(storeId);
		storeFeature2.setStore_feature2_image_id(storeFeature2ImageId);
		
		storeFeature2 = (StoreFeature2Bean)getSqlMapClientTemplate().queryForObject("storeFeature2.byStoreFeature2", storeFeature2);
		Cache.storeFeature2.put(key, storeFeature2);
		return storeFeature2;
	}
	
	@SuppressWarnings("unchecked")
	public List<StoreFeature2Bean> ByStoreId(int storeId){
		String key = "storeFeature2.byStoreId" + storeId;
		if(Cache.storeFeature2s.containsKey(key))
	    	return Cache.storeFeature2s.get(key);
		
		List<StoreFeature2Bean> storeFeature2s = (List<StoreFeature2Bean>)getSqlMapClientTemplate().queryForList("storeFeature2.byStoreId", storeId);
		Cache.storeFeature2s.put(key, storeFeature2s);
		return storeFeature2s;
	}
	
	public int Add(StoreFeature2Bean storeFeature2){
		int id = (Integer)getSqlMapClientTemplate().insert("storeFeature2.add", storeFeature2);
		Cache.storeFeature2.clear();
		Cache.storeFeature2s.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("storeFeature2.remove", id);
		Cache.storeFeature2.clear();
		Cache.storeFeature2s.clear();
		return id;
	}
	
	public int RemoveStoreId(int storeId){
		int id = (Integer)getSqlMapClientTemplate().delete("storeFeature2.removeStoreId", storeId);
		Cache.storeFeature2.clear();
		Cache.storeFeature2s.clear();
		return id;
	}
}