package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.StoreFeature1ImageBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StoreFeature1ImageDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<StoreFeature1ImageBean> By(int page){
		String key = "storeFeature1Image.by" + page;
		if(Cache.storeFeature1Images.containsKey(key))
	    	return Cache.storeFeature1Images.get(key);
		
		List<StoreFeature1ImageBean> storeFeature1Images = (List<StoreFeature1ImageBean>)getSqlMapClientTemplate().queryForList("storeFeature1Image.by", page);
		Cache.storeFeature1Images.put(key, storeFeature1Images);
		return storeFeature1Images;
	}

	public StoreFeature1ImageBean ById(int id){
		String key = "storeFeature1Image.byId" + id;
		if(Cache.storeFeature1Image.containsKey(key))
			return Cache.storeFeature1Image.get(key);
		
		StoreFeature1ImageBean storeFeature1Image = (StoreFeature1ImageBean)getSqlMapClientTemplate().queryForObject("storeFeature1Image.byId", id);
		Cache.storeFeature1Image.put(key, storeFeature1Image);
		return storeFeature1Image;
	}
	
	public int Add(StoreFeature1ImageBean storeFeature1Image){
		int id = (Integer)getSqlMapClientTemplate().insert("storeFeature1Image.add", storeFeature1Image);
		Cache.storeFeature1Image.clear();
		Cache.storeFeature1Images.clear();
		return id;
	}
	
	public int Modify(StoreFeature1ImageBean storeFeature1Image){
		int id = (Integer)getSqlMapClientTemplate().update("storeFeature1Image.modify", storeFeature1Image);
		Cache.storeFeature1Image.clear();
		Cache.storeFeature1Images.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("storeFeature1Image.remove", id);
		Cache.storeFeature1Image.clear();
		Cache.storeFeature1Images.clear();
		return id;
	}
}