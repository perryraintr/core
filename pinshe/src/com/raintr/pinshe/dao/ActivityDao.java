package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.ActivityBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class ActivityDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<ActivityBean> By(int page){
		String key = "activity.by" + page;
		if(Cache.activitys.containsKey(key))
	    	return Cache.activitys.get(key);
		
		List<ActivityBean> activitys = (List<ActivityBean>)getSqlMapClientTemplate().queryForList("activity.by", page);
		Cache.activitys.put(key, activitys);
		return activitys;
	}
	
	public ActivityBean ById(int id){
		String key = "activity.byId" + id;
		if(Cache.activity.containsKey(key))
	    	return Cache.activity.get(key);
		
		ActivityBean activity = (ActivityBean)getSqlMapClientTemplate().queryForObject("activity.byId", id);
		Cache.activity.put(key, activity);
		return activity;
	}
	
	public ActivityBean ByMemberId(int memberId){
		String key = "activity.byMemberId" + memberId;
		if(Cache.activity.containsKey(key))
	    	return Cache.activity.get(key);
		
		ActivityBean activity = (ActivityBean)getSqlMapClientTemplate().queryForObject("activity.byMemberId", memberId);
		Cache.activity.put(key, activity);
		return activity;
	}
	
	public int Add(ActivityBean activity){
		int id = (Integer)getSqlMapClientTemplate().insert("activity.add", activity);
		Cache.activity.clear();
		Cache.activitys.clear();
		return id;
	}
	
	public int Modify(ActivityBean activity){
		int id = (Integer)getSqlMapClientTemplate().update("activity.modify", activity);
		Cache.activity.clear();
		Cache.activitys.clear();
		return id;
	}
}