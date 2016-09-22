package com.raintr.pinshe.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.AdBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class AdDao extends SqlMapClientDaoSupport {
	public AdBean ById(int id){
		String key = "ad.byId" + id;
		if(Cache.ad.containsKey(key))
	    	return Cache.ad.get(key);
		
		AdBean ad = (AdBean)getSqlMapClientTemplate().queryForObject("ad.byId", id);
		Cache.ad.put(key, ad);
		return ad;
	}
	
	@SuppressWarnings("unchecked")
	public List<AdBean> ByType(int type, int page){
		String key = "ad.by" + page;
		if(Cache.ad.containsKey(key))
	    	return Cache.ads.get(key);
		
		AdBean ad = new AdBean();
		ad.setType(type);
		ad.setId(page);
		List<AdBean> ads = (List<AdBean>)getSqlMapClientTemplate().queryForList("ad.byType", ad);
		Cache.ads.put(key, ads);
		return ads;
	}
	
	public int Add(AdBean ad){
		int id = (Integer)getSqlMapClientTemplate().insert("ad.add", ad);
		Cache.ad.clear();
		Cache.ads.clear();
		return id;
	}
	
	public int Modify(AdBean ad){
		int id = (Integer)getSqlMapClientTemplate().update("ad.modify", ad);
		Cache.ad.clear();
		Cache.ads.clear();
		return id;
	}
}