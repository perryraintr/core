package com.raintr.pinshe.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.HistoryBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class HistoryDao extends SqlMapClientDaoSupport {
	public HistoryBean ById(int id){
		String key = "history.byId" + id;
		if(Cache.history.containsKey(key))
	    	return Cache.history.get(key);
		
		HistoryBean history = (HistoryBean)getSqlMapClientTemplate().queryForObject("history.byId", id);
		Cache.history.put(key, history);
		return history;
	}
	
	public HistoryBean ByUserIdVoteId(int userId, int voteId){
		String key = "history.byUserIdVoteId" + userId + voteId;
		if(Cache.history.containsKey(key))
	    	return Cache.history.get(key);
		
		HistoryBean history = new HistoryBean();
		history.setVote_id(voteId);
		history.setUser_id(userId);
		history = (HistoryBean)getSqlMapClientTemplate().queryForObject("history.byUserIdVoteId", history);
		Cache.history.put(key, history);
		return history;
	}
	
	public int Add(HistoryBean history){
		int id = (Integer)getSqlMapClientTemplate().insert("history.add", history);
		Cache.history.clear();
		return id;
	}
}