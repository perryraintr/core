package com.raintr.pinshe.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.StoreCommentBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StoreCommentDao extends SqlMapClientDaoSupport {
	public StoreCommentBean ById(int id){
		String key = "storeComment.byId" + id;
		if(Cache.storeComment.containsKey(key))
	    	return Cache.storeComment.get(key);
		
		StoreCommentBean storeComment = (StoreCommentBean)getSqlMapClientTemplate().queryForObject("storeComment.byId", id);
		Cache.storeComment.put(key, storeComment);
		return storeComment;
	}
	
	@SuppressWarnings("unchecked")
	public List<StoreCommentBean> ByStoreId(int storeId, int page){
		String key = "storeComment.byStoreId" + storeId + page;
		if(Cache.storeComments.containsKey(key))
	    	return Cache.storeComments.get(key);

		StoreCommentBean storeComment = new StoreCommentBean();
		storeComment.setStore_id(storeId);
		storeComment.setId(page);
		List<StoreCommentBean> storeComments = (List<StoreCommentBean>)getSqlMapClientTemplate().queryForList("storeComment.byStoreId", storeComment);
		Cache.storeComments.put(key, storeComments);
		return storeComments;
	}
	
	public int Add(StoreCommentBean storeComment){
		int id = (Integer)getSqlMapClientTemplate().insert("storeComment.add", storeComment);
		Cache.storeComment.clear();
		Cache.storeComments.clear();
		return id;
	}
}