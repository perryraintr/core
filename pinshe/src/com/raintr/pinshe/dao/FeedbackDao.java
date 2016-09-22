package com.raintr.pinshe.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.FeedbackBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class FeedbackDao extends SqlMapClientDaoSupport {
	public FeedbackBean ById(int id){
		String key = "feedback.byId" + id;
		if(Cache.feedback.containsKey(key))
	    	return Cache.feedback.get(key);
		
		FeedbackBean feedback = (FeedbackBean)getSqlMapClientTemplate().queryForObject("feedback.byId", id);
		Cache.feedback.put(key, feedback);
		return feedback;
	}
	
	public int Add(FeedbackBean feedback){
		int id = (Integer)getSqlMapClientTemplate().insert("feedback.add", feedback);
		Cache.feedback.clear();
		return id;
	}
}