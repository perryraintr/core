package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.SubscriptionBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class SubscriptionDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<SubscriptionBean> By(int page){
		String key = "subscription.by" + page;
		if(Cache.subscriptions.containsKey(key))
	    	return Cache.subscriptions.get(key);
		
		List<SubscriptionBean> subscriptions = (List<SubscriptionBean>)getSqlMapClientTemplate().queryForList("subscription.by", page);
		Cache.subscriptions.put(key, subscriptions);
		return subscriptions;
	}
	
	public SubscriptionBean ById(int id){
		String key = "subscription.byId" + id;
		if(Cache.subscription.containsKey(key))
	    	return Cache.subscription.get(key);
		
		SubscriptionBean subscription = (SubscriptionBean)getSqlMapClientTemplate().queryForObject("subscription.byId", id);
		Cache.subscription.put(key, subscription);
		return subscription;
	}
	
	public SubscriptionBean ByMemberId(int id){
		String key = "subscription.byMemberId" + id;
		if(Cache.subscription.containsKey(key))
	    	return Cache.subscription.get(key);
		
		SubscriptionBean subscription = (SubscriptionBean)getSqlMapClientTemplate().queryForObject("subscription.byMemberId", id);
		Cache.subscription.put(key, subscription);
		return subscription;
	}
	
	public int Add(SubscriptionBean subscription){
		int id = (Integer)getSqlMapClientTemplate().insert("subscription.add", subscription);
		Cache.subscription.clear();
		Cache.subscriptions.clear();
		return id;
	}
	
	public int Modify(SubscriptionBean subscription){
		int id = (Integer)getSqlMapClientTemplate().update("subscription.modify", subscription);
		Cache.subscription.clear();
		Cache.subscriptions.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("subscription.remove", id);
		Cache.subscription.clear();
		Cache.subscriptions.clear();
		return id;
	}
}