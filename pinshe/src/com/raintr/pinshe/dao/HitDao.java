package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.HitBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class HitDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<HitBean> By(int page){
		String key = "hit.by" + page;
		if(Cache.hits.containsKey(key))
	    	return Cache.hits.get(key);
		
		List<HitBean> hits = (List<HitBean>)getSqlMapClientTemplate().queryForList("hit.by", page);
		Cache.hits.put(key, hits);
		return hits;
	}
	
	public HitBean ByName(String name){
		String key = "hit.byName" + name;
		if(Cache.hit.containsKey(key))
	    	return Cache.hit.get(key);
		
		HitBean hit = (HitBean)getSqlMapClientTemplate().queryForObject("hit.byName", name);
		Cache.hit.put(key, hit);
		return hit;
	}
	
	public int Add(HitBean hit){
		int id = (Integer)getSqlMapClientTemplate().insert("hit.add", hit);
		Cache.hit.clear();
		Cache.hits.clear();
		return id;
	}
	
	public int Modify(HitBean hit){
		int id = (Integer)getSqlMapClientTemplate().update("hit.modify", hit);
		Cache.hit.clear();
		Cache.hits.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("hit.remove", id);
		Cache.hit.clear();
		Cache.hits.clear();
		return id;
	}
}