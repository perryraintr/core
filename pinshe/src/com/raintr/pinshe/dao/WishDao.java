package com.raintr.pinshe.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.WishBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class WishDao extends SqlMapClientDaoSupport {
	public WishBean ById(int id){
		String key = "wish.byId" + id;
		if(Cache.wish.containsKey(key))
	    	return Cache.wish.get(key);
		
		WishBean wish = (WishBean)getSqlMapClientTemplate().queryForObject("wish.byId", id);
		Cache.wish.put(key, wish);
		return wish;
	}
	
	@SuppressWarnings("unchecked")
	public List<WishBean> ByUserId(int userId, int page){
		String key = "wish.byUserId" + userId + page;
		if(Cache.wishs.containsKey(key))
	    	return Cache.wishs.get(key);
		
		WishBean wish = new WishBean();
		wish.setUser_id(userId);
		wish.setId(page);
		List<WishBean> wishs = (List<WishBean>)getSqlMapClientTemplate().queryForList("wish.byUserId", wish);
		Cache.wishs.put(key, wishs);
		return wishs;
	}
	
	public WishBean ByUserIdPostId(int userId, int postId){
		String key = "wish.byUserIdPostId" + userId + postId;
		if(Cache.wish.containsKey(key))
	    	return Cache.wish.get(key);
		
		WishBean wish = new WishBean();
		wish.setUser_id(userId);
		wish.setPost_id(postId);
		wish = (WishBean)getSqlMapClientTemplate().queryForObject("wish.byUserIdPostId", wish);
		Cache.wish.put(key, wish);
		return wish;
	}
	
	public WishBean ByUserIdVoteId(int userId, int voteId){
		String key = "wish.byUserIdVoteId" + userId + voteId;
		if(Cache.wish.containsKey(key))
	    	return Cache.wish.get(key);
		
		WishBean wish = new WishBean();
		wish.setUser_id(userId);
		wish.setVote_id(voteId);
		wish = (WishBean)getSqlMapClientTemplate().queryForObject("wish.byUserIdVoteId", wish);
		Cache.wish.put(key, wish);
		return wish;
	}
	
	public int Add(WishBean wish){
		int id = (Integer)getSqlMapClientTemplate().insert("wish.add", wish);
		Cache.wish.clear();
		Cache.wishs.clear();
		return id;
	}
	
	public int Modify(WishBean wish){
		int id = (Integer)getSqlMapClientTemplate().update("wish.modify", wish);
		Cache.wish.clear();
		Cache.wishs.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("wish.remove", id);
		Cache.wish.clear();
		Cache.wishs.clear();
		return id;
	}
	
	public int RemoveByUserIdPostId(int userId, int postId){
		WishBean wish = new WishBean();
		wish.setUser_id(userId);
		wish.setPost_id(postId);
		int id = (Integer)getSqlMapClientTemplate().delete("wish.removeByUserIdPostId", wish);
		Cache.wish.clear();
		Cache.wishs.clear();
		return id;
	}
	
	public int RemoveByUserIdVoteId(int userId, int voteId){
		WishBean wish = new WishBean();
		wish.setUser_id(userId);
		wish.setVote_id(voteId);
		int id = (Integer)getSqlMapClientTemplate().delete("wish.removeByUserIdVoteId", wish);
		Cache.wish.clear();
		Cache.wishs.clear();
		return id;
	}
}