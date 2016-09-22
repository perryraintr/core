package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.ActivityDetailBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class ActivityDetailDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<ActivityDetailBean> ByActivityId(int activityId){
		String key = "activityDetail.byActivityId" + activityId;
		if(Cache.activityDetails.containsKey(key))
	    	return Cache.activityDetails.get(key);
		
		List<ActivityDetailBean> activityDetails = (List<ActivityDetailBean>)getSqlMapClientTemplate().queryForList("activityDetail.byActivityId", activityId);
		Cache.activityDetails.put(key, activityDetails);
		return activityDetails;
	}
	
	public ActivityDetailBean ByActivityIdStoreId(int activityId, int storeId){
		String key = "activityDetail.byActivityIdStoreId" + activityId + storeId;
		if(Cache.activityDetail.containsKey(key))
	    	return Cache.activityDetail.get(key);
		
		ActivityDetailBean activityDetail = new ActivityDetailBean();
		activityDetail.setActivity_id(activityId);
		activityDetail.setStore_id(storeId);
		ActivityDetailBean activity = (ActivityDetailBean)getSqlMapClientTemplate().queryForObject("activityDetail.byActivityIdStoreId", activityDetail);
		Cache.activityDetail.put(key, activity);
		return activity;
	}
	
	public int Add(ActivityDetailBean activityDetail){
		int id = (Integer)getSqlMapClientTemplate().insert("activityDetail.add", activityDetail);
		Cache.activityDetail.clear();
		return id;
	}
	
	public int Modify(ActivityDetailBean activityDetail){
		int id = (Integer)getSqlMapClientTemplate().update("activityDetail.modify", activityDetail);
		Cache.activityDetail.clear();
		return id;
	}
}