package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class CommodityDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<CommodityBean> By(int page){
		String key = "commodity.by" + page;
		if(Cache.commoditys.containsKey(key))
	    	return Cache.commoditys.get(key);
		
		List<CommodityBean> commoditys = (List<CommodityBean>)getSqlMapClientTemplate().queryForList("commodity.by", page);
		Cache.commoditys.put(key, commoditys);
		return commoditys;
	}
	
	public CommodityBean ById(int id){
		String key = "commodity.byId" + id;
		
		if(Cache.commodity.containsKey(key)){
			synchronized (Cache.commodityObject){
				return Cache.commodity.get(key);
			}
		}
		
		CommodityBean commodity = (CommodityBean)getSqlMapClientTemplate().queryForObject("commodity.byId", id);
		Cache.commodity.put(key, commodity);
		return commodity;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommodityBean> ByT0(int t0){
		String key = "commodity.byT0" + t0;
		if(Cache.commoditys.containsKey(key))
	    	return Cache.commoditys.get(key);
		
		List<CommodityBean> commoditys = (List<CommodityBean>)getSqlMapClientTemplate().queryForList("commodity.byT0", t0);
		Cache.commoditys.put(key, commoditys);
		return commoditys;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommodityBean> ByT1(int t1){
		String key = "commodity.byT1" + t1;
		if(Cache.commoditys.containsKey(key))
	    	return Cache.commoditys.get(key);
		
		List<CommodityBean> commoditys = (List<CommodityBean>)getSqlMapClientTemplate().queryForList("commodity.byT1", t1);
		Cache.commoditys.put(key, commoditys);
		return commoditys;
	}
	
	public CommodityBean ByYZ(int num_iid, int outer_id){
		String key = "commodity.byYZ" + num_iid + outer_id;
		if(Cache.commodity.containsKey(key))
	    	return Cache.commodity.get(key);
		
		CommodityBean commodity = new CommodityBean();
		commodity.setNum_iid(num_iid);
		commodity.setOuter_id(outer_id);
		
		commodity = (CommodityBean)getSqlMapClientTemplate().queryForObject("commodity.byYZ", commodity);
		Cache.commodity.put(key, commodity);
		return commodity;
	}
	
	public int Add(CommodityBean commodity){
		int id = (Integer)getSqlMapClientTemplate().insert("commodity.add", commodity);
		synchronized (Cache.commodityObject){
			Cache.commodity.clear();
			Cache.commoditys.clear();
		}
		return id;
	}
	
	public int Modify(CommodityBean commodity){
		int id = (Integer)getSqlMapClientTemplate().update("commodity.modify", commodity);
		synchronized (Cache.commodityObject){
			Cache.commodity.clear();
			Cache.commoditys.clear();
		}
		return id;
	}
	
	public int Modify(int status){
		int id = (Integer)getSqlMapClientTemplate().update("commodity.modifyStatus", status);
		synchronized (Cache.commodityObject){
			Cache.commodity.clear();
			Cache.commoditys.clear();
		}
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("commodity.remove", id);
		synchronized (Cache.commodityObject){
			Cache.commodity.clear();
			Cache.commoditys.clear();
		}
		return id;
	}
}