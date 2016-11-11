package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.StoreFeature1Bean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StoreFeature1Dao extends SqlMapClientDaoSupport {
	public StoreFeature1Bean byStoreFeature1(int storeId, int storeFeature1ImageId){
		String key = "storeFeature1.byStoreFeature1" + storeId + storeFeature1ImageId;
		if(Cache.storeFeature1.containsKey(key))
			return Cache.storeFeature1.get(key);
		
		StoreFeature1Bean storeFeature1 = new StoreFeature1Bean();
		storeFeature1.setStore_id(storeId);
		storeFeature1.setStore_feature1_image_id(storeFeature1ImageId);
		
		storeFeature1 = (StoreFeature1Bean)getSqlMapClientTemplate().queryForObject("storeFeature1.byStoreFeature1", storeFeature1);
		Cache.storeFeature1.put(key, storeFeature1);
		return storeFeature1;
	}
	
	@SuppressWarnings("unchecked")
	public List<StoreFeature1Bean> ByStoreId(int storeId){
		String key = "storeFeature1.byStoreId" + storeId;
		if(Cache.storeFeature1s.containsKey(key))
	    	return Cache.storeFeature1s.get(key);
		
		List<StoreFeature1Bean> storeFeature1s = (List<StoreFeature1Bean>)getSqlMapClientTemplate().queryForList("storeFeature1.byStoreId", storeId);
		Cache.storeFeature1s.put(key, storeFeature1s);
		return storeFeature1s;
	}
	
	public int Add(StoreFeature1Bean storeFeature1){
		int id = (Integer)getSqlMapClientTemplate().insert("storeFeature1.add", storeFeature1);
		Cache.storeFeature1.clear();
		Cache.storeFeature1s.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("storeFeature1.remove", id);
		Cache.storeFeature1.clear();
		Cache.storeFeature1s.clear();
		return id;
	}
	
	public int RemoveStoreId(int storeId){
		int id = (Integer)getSqlMapClientTemplate().delete("storeFeature1.removeStoreId", storeId);
		Cache.storeFeature1.clear();
		Cache.storeFeature1s.clear();
		return id;
	}
}