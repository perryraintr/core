package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class PostDao extends SqlMapClientDaoSupport {
	public PostBean ById(int id){
		String key = "post.byId" + id;
		if(Cache.post.containsKey(key))
			return Cache.post.get(key);
		
		PostBean post = (PostBean)getSqlMapClientTemplate().queryForObject("post.byId", id);
		Cache.post.put(key, post);
		return post;
	}
	
	@SuppressWarnings("unchecked")
	public List<PostBean> By(int page){
		String key = "post.by" + page;
		if(Cache.posts.containsKey(key))
	    	return Cache.posts.get(key);
		
		List<PostBean> posts = (List<PostBean>)getSqlMapClientTemplate().queryForList("post.by", page);
		Cache.posts.put(key, posts);
		return posts;
	}
	
	@SuppressWarnings("unchecked")
	public List<PostBean> ByProductId(int productId){
		String key = "post.byProductId" + productId;
		if(Cache.posts.containsKey(key))
	    	return Cache.posts.get(key);
		
		List<PostBean> posts = (List<PostBean>)getSqlMapClientTemplate().queryForList("post.byProductId", productId);
		Cache.posts.put(key, posts);
		return posts;
	}
	
	@SuppressWarnings("unchecked")
	public List<PostBean> ByTagT1(int t1, int page){
		String key = "post.byTagT1" + t1 + page;
		if(Cache.posts.containsKey(key))
	    	return Cache.posts.get(key);
		
		TagBean tag = new TagBean();
		tag.setT1(t1);
		tag.setId(page);
		List<PostBean> posts = (List<PostBean>)getSqlMapClientTemplate().queryForList("post.byTagT1", tag);
		Cache.posts.put(key, posts);
		return posts;
	}
	
	@SuppressWarnings("unchecked")
	public List<PostBean> ByTagT1T2(int t1, int t2, int page){
		String key = "post.byTagT1T2" + t1 + t2 + page;
		if(Cache.posts.containsKey(key))
	    	return Cache.posts.get(key);
		
		TagBean tag = new TagBean();
		tag.setT1(t1);
		tag.setT2(t2);
		tag.setId(page);
		List<PostBean> posts = (List<PostBean>)getSqlMapClientTemplate().queryForList("post.byTagT1T2", tag);
		Cache.posts.put(key, posts);
		return posts;
	}
	
	@SuppressWarnings("unchecked")
	public List<PostBean> ByTagT1T2T3(int t1, int t2, int t3, int page){
		String key = "post.byTagT1T2T3" + t1 + t2 + page;
		if(Cache.posts.containsKey(key))
	    	return Cache.posts.get(key);
		
		TagBean tag = new TagBean();
		tag.setT1(t1);
		tag.setT2(t2);
		tag.setT3(t3);
		tag.setId(page);
		List<PostBean> posts = (List<PostBean>)getSqlMapClientTemplate().queryForList("post.byTagT1T2T3", tag);
		Cache.posts.put(key, posts);
		return posts;
	}
	
	public int Add(PostBean post){
		int id = (Integer)getSqlMapClientTemplate().insert("post.add", post);
		Cache.post.clear();
		Cache.posts.clear();
		return id;
	}
	
	public int Modify(PostBean post){
		int id = (Integer)getSqlMapClientTemplate().update("post.modify", post);
		Cache.post.clear();
		Cache.posts.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("post.remove", id);
		Cache.post.clear();
		Cache.posts.clear();
		return id;
	}
}