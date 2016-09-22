package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.RecommendBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class RecommendDao extends SqlMapClientDaoSupport {
	public RecommendBean ById(int id){
		String key = "recommend.byId" + id;
		if(Cache.recommend.containsKey(key))
	    	return Cache.recommend.get(key);
		
		RecommendBean recommend = (RecommendBean)getSqlMapClientTemplate().queryForObject("recommend.byId", id);
		Cache.recommend.put(key, recommend);
		return recommend;
	}
	
	public RecommendBean ByStoreIdCommodityId(int storeId, int commodityId){
		String key = "recommend.byStoreIdCommodityId" + storeId + commodityId;
		if(Cache.recommend.containsKey(key))
	    	return Cache.recommend.get(key);
		
		RecommendBean recommend = new RecommendBean();
		recommend.setStore_id(storeId);
		recommend.setCommodity_id(commodityId);
		recommend = (RecommendBean)getSqlMapClientTemplate().queryForObject("recommend.byStoreIdCommodityId", recommend);
		Cache.recommend.put(key, recommend);
		return recommend;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<RecommendBean> ByStoreId(int storeId, int page){
		String key = "recommend.byStoreId" + storeId + page;
		if(Cache.recommends.containsKey(key))
	    	return Cache.recommends.get(key);

		RecommendBean recommend = new RecommendBean();
		recommend.setStore_id(storeId);
		recommend.setId(page);
		List<RecommendBean> recommends = (List<RecommendBean>)getSqlMapClientTemplate().queryForList("recommend.byStoreId", recommend);
		Cache.recommends.put(key, recommends);
		return recommends;
	}
	
	@SuppressWarnings("unchecked")
	public List<RecommendBean> ByCommodityId(int commodityId, int page){
		String key = "recommend.byCommodityId" + commodityId + page;
		if(Cache.recommends.containsKey(key))
	    	return Cache.recommends.get(key);
		
		RecommendBean recommend = new RecommendBean();
		recommend.setCommodity_id(commodityId);
		recommend.setId(page);
		List<RecommendBean> recommends = (List<RecommendBean>)getSqlMapClientTemplate().queryForList("recommend.byCommodityId", recommend);
		Cache.recommends.put(key, recommends);
		return recommends;
	}
	
	public int Add(RecommendBean recommend){
		int id = (Integer)getSqlMapClientTemplate().insert("recommend.add", recommend);
		Cache.recommend.clear();
		Cache.recommends.clear();
		return id;
	}
	
	public int Modify(RecommendBean recommend){
		int id = (Integer)getSqlMapClientTemplate().update("recommend.modify", recommend);
		Cache.recommend.clear();
		Cache.recommends.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("recommend.remove", id);
		Cache.recommend.clear();
		Cache.recommends.clear();
		return id;
	}
}