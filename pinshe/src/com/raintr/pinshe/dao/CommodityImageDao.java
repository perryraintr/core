package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.CommodityImageBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class CommodityImageDao extends SqlMapClientDaoSupport {
	public CommodityImageBean ById(int id){
		String key = "commodityImage.byId" + id;
		if(Cache.commodityImage.containsKey(key))
	    	return Cache.commodityImage.get(key);
		
		CommodityImageBean commodityImage = (CommodityImageBean)getSqlMapClientTemplate().queryForObject("commodityImage.byId", id);
		Cache.commodityImage.put(key, commodityImage);
		return commodityImage;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommodityImageBean> ByCommodityId(int commodityId){
		String key = "commodityImage.byCommodityId" + commodityId;
		if(Cache.commodityImages.containsKey(key))
	    	return Cache.commodityImages.get(key);
		
		List<CommodityImageBean> commodityImages = (List<CommodityImageBean>)getSqlMapClientTemplate().queryForList("commodityImage.byCommodityId", commodityId);
		Cache.commodityImages.put(key, commodityImages);
		return commodityImages;
	}
	
	public int Add(CommodityImageBean commodityImage){
		int id = (Integer)getSqlMapClientTemplate().insert("commodityImage.add", commodityImage);
		Cache.commodityImage.clear();
		Cache.commodityImages.clear();
		return id;
	}
	
	public int Modify(CommodityImageBean commodityImage){
		int id = (Integer)getSqlMapClientTemplate().update("commodityImage.modify", commodityImage);
		Cache.commodityImage.clear();
		Cache.commodityImages.clear();
		return id;
	}
	
	public int RemoveCommodityId(int commodityId){
		int id = (Integer)getSqlMapClientTemplate().delete("commodityImage.removeCommodityId", commodityId);
		Cache.commodityImage.clear();
		Cache.commodityImages.clear();
		return id;
	}
}