package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.StoreFeature2ImageBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StoreFeature2ImageDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<StoreFeature2ImageBean> By(int page){
		String key = "storeFeature2Image.by" + page;
		if(Cache.storeFeature2Images.containsKey(key))
	    	return Cache.storeFeature2Images.get(key);
		
		List<StoreFeature2ImageBean> storeFeature2Images = (List<StoreFeature2ImageBean>)getSqlMapClientTemplate().queryForList("storeFeature2Image.by", page);
		Cache.storeFeature2Images.put(key, storeFeature2Images);
		return storeFeature2Images;
	}
	
	public StoreFeature2ImageBean ById(int id){
		String key = "storeFeature2Image.byId" + id;
		if(Cache.storeFeature2Image.containsKey(key))
			return Cache.storeFeature2Image.get(key);
		
		StoreFeature2ImageBean storeFeature2Image = (StoreFeature2ImageBean)getSqlMapClientTemplate().queryForObject("storeFeature2Image.byId", id);
		Cache.storeFeature2Image.put(key, storeFeature2Image);
		return storeFeature2Image;
	}
	
	public int Add(StoreFeature2ImageBean storeFeature2Image){
		int id = (Integer)getSqlMapClientTemplate().insert("storeFeature2Image.add", storeFeature2Image);
		Cache.storeFeature2Image.clear();
		Cache.storeFeature2Images.clear();
		return id;
	}
	
	public int Modify(StoreFeature2ImageBean storeFeature2Image){
		int id = (Integer)getSqlMapClientTemplate().update("storeFeature2Image.modify", storeFeature2Image);
		Cache.storeFeature2Image.clear();
		Cache.storeFeature2Images.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("storeFeature2Image.remove", id);
		Cache.storeFeature2Image.clear();
		Cache.storeFeature2Images.clear();
		return id;
	}
}