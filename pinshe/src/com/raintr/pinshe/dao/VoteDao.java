package com.raintr.pinshe.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class VoteDao extends SqlMapClientDaoSupport {
	public VoteBean ById(int id){
		String key = "vote.byId" + id;
		if(Cache.vote.containsKey(key))
			return Cache.vote.get(key);
	    	
		VoteBean vote = (VoteBean)getSqlMapClientTemplate().queryForObject("vote.byId", id);
		Cache.vote.put(key, vote);
		return vote;
	}
	
	@SuppressWarnings("unchecked")
	public List<VoteBean> By(int userId, int page){
		String key = "vote.by" + userId + page;
		if(Cache.votes.containsKey(key))
	    	return Cache.votes.get(key);
		
		VoteBean vote = new VoteBean();
		vote.setUser_id(userId);
		vote.setId(page);
		List<VoteBean> votes = (List<VoteBean>)getSqlMapClientTemplate().queryForList("vote.by", vote);
		Cache.votes.put(key, votes);
		return votes;
	}
	
	public int Add(VoteBean vote){
		int id = (Integer)getSqlMapClientTemplate().insert("vote.add", vote);
		Cache.vote.clear();
		Cache.votes.clear();
		return id;
	}
	
	public int Modify(VoteBean vote){
		int id = (Integer)getSqlMapClientTemplate().update("vote.modify", vote);
		Cache.vote.clear();
		Cache.votes.clear();
		return id;
	}
}