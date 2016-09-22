package com.raintr.pinshe.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.FavoriteBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class FavoriteDao extends SqlMapClientDaoSupport {
	public FavoriteBean ByUserIdPostId(int userId, int postId){
		String key = "favorite.byUserIdPostId" + userId + postId;
		if(Cache.favorite.containsKey(key))
	    	return Cache.favorite.get(key);
		
		FavoriteBean favorite = new FavoriteBean();
		favorite.setUser_id(userId);
		favorite.setPost_id(postId);
		favorite = (FavoriteBean)getSqlMapClientTemplate().queryForObject("favorite.byUserIdPostId", favorite);
		Cache.favorite.put(key, favorite);
		return favorite;
	}
	
	public FavoriteBean ByUserIdVoteId(int userId, int voteId){
		String key = "favorite.byUserIdVoteId" + userId + voteId;
		if(Cache.favorite.containsKey(key))
	    	return Cache.favorite.get(key);
		
		FavoriteBean favorite = new FavoriteBean();
		favorite.setUser_id(userId);
		favorite.setVote_id(voteId);
		favorite = (FavoriteBean)getSqlMapClientTemplate().queryForObject("favorite.byUserIdVoteId", favorite);
		Cache.favorite.put(key, favorite);
		return favorite;
	}
	
	public FavoriteBean ByUserIdProductId(int userId, int productId){
		String key = "favorite.byUserIdProductId" + userId + productId;
		if(Cache.favorite.containsKey(key))
	    	return Cache.favorite.get(key);
		
		FavoriteBean favorite = new FavoriteBean();
		favorite.setUser_id(userId);
		favorite.setProduct_id(productId);
		favorite = (FavoriteBean)getSqlMapClientTemplate().queryForObject("favorite.byUserIdProductId", favorite);
		Cache.favorite.put(key, favorite);
		return favorite;
	}
	
	public int Add(FavoriteBean favorite){
		int id = (Integer)getSqlMapClientTemplate().insert("favorite.add", favorite);
		Cache.favorite.clear();
		return id;
	}
	
	public int RemoveUserIdPostId(int userId, int postId){
		FavoriteBean favorite = new FavoriteBean();
		favorite.setUser_id(userId);
		favorite.setPost_id(postId);
		int id = (Integer)getSqlMapClientTemplate().delete("favorite.removeUserIdPostId", favorite);
		Cache.favorite.clear();
		return id;
	}
	
	public int RemoveUserIdVoteId(int userId, int voteId){
		FavoriteBean favorite = new FavoriteBean();
		favorite.setUser_id(userId);
		favorite.setVote_id(voteId);
		int id = (Integer)getSqlMapClientTemplate().delete("favorite.removeUserIdVoteId", favorite);
		Cache.favorite.clear();
		return id;
	}
	
	public int RemoveUserIdProductId(int userId, int productId){
		FavoriteBean favorite = new FavoriteBean();
		favorite.setUser_id(userId);
		favorite.setProduct_id(productId);
		int id = (Integer)getSqlMapClientTemplate().delete("favorite.removeUserIdProductId", favorite);
		Cache.favorite.clear();
		return id;
	}
}