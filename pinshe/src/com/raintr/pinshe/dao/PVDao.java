package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.PVBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class PVDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<PVBean> By(int page){
		String key = "pv.by" + page;
		if(Cache.pvs.containsKey(key))
	    	return Cache.pvs.get(key);
		
		List<PVBean> pvs = (List<PVBean>)getSqlMapClientTemplate().queryForList("pv.by", page);
		Cache.pvs.put(key, pvs);
		return pvs;
	}
	
	public PVBean ById(int id){
		String key = "pv.byId" + id;
		if(Cache.pv.containsKey(key))
	    	return Cache.pv.get(key);
		
		PVBean pv = (PVBean)getSqlMapClientTemplate().queryForObject("pv.byId", id);
		Cache.pv.put(key, pv);
		return pv;
	}
	
	public PVBean ByProductId(int productId){
		String key = "pv.byProductId" + productId;
		if(Cache.pv.containsKey(key))
	    	return Cache.pv.get(key);
		
		PVBean pv = (PVBean)getSqlMapClientTemplate().queryForObject("pv.byProductId", productId);
		Cache.pv.put(key, pv);
		return pv;
	}
	
	public int Add(PVBean pv){
		int id = (Integer)getSqlMapClientTemplate().insert("pv.add", pv);
		Cache.pv.clear();
		Cache.pvs.clear();
		return id;
	}
	
	public int Modify(PVBean pv){
		int id = (Integer)getSqlMapClientTemplate().update("pv.modify", pv);
		Cache.pv.clear();
		Cache.pvs.clear();
		return id;
	}
}