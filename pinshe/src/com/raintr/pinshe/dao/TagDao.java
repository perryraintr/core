package com.raintr.pinshe.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class TagDao extends SqlMapClientDaoSupport {
	public TagBean ById(int id){
		String key = "tag.byId" + id;
		if(Cache.tag.containsKey(key))
	    	return Cache.tag.get(key);
		
		TagBean tag = (TagBean)getSqlMapClientTemplate().queryForObject("tag.byId", id);
		Cache.tag.put(key, tag);
		return tag;
	}
	
	@SuppressWarnings("unchecked")
	public List<TagBean> ByPostId(int postId){
		String key = "tag.byPostId" + postId;
		if(Cache.tags.containsKey(key))
	    	return Cache.tags.get(key);
		
		TagBean tag = new TagBean();
		tag.setPost_id(postId);
		List<TagBean> tags = (List<TagBean>)getSqlMapClientTemplate().queryForList("tag.byPostId", tag);
		Cache.tags.put(key, tags);
		return tags;
	}
	
	@SuppressWarnings("unchecked")
	public List<TagBean> ByProductId(int productId){
		String key = "tag.byProductId" + productId;
		if(Cache.tags.containsKey(key))
	    	return Cache.tags.get(key);
		
		TagBean tag = new TagBean();
		tag.setProduct_id(productId);
		List<TagBean> tags = (List<TagBean>)getSqlMapClientTemplate().queryForList("tag.byProductId", tag);
		Cache.tags.put(key, tags);
		return tags;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<TagBean> ByT1T2(int t1, int t2){
		String key = "tag.byT1T2" + t1 + t2;
		if(Cache.tags.containsKey(key))
	    	return Cache.tags.get(key);
		
		TagBean tag = new TagBean();
		tag.setT1(t1);
		tag.setT2(t2);
		List<TagBean> tags = (List<TagBean>)getSqlMapClientTemplate().queryForList("tag.byT1T2", tag);
		Cache.tags.put(key, tags);
		return tags;
	}
	
	public int Add(TagBean tag){
		int id = (Integer)getSqlMapClientTemplate().insert("tag.add", tag);
		Cache.tag.clear();
		Cache.tags.clear();
		return id;
	}
	
	public int Modify(TagBean tag){
		int id = (Integer)getSqlMapClientTemplate().update("tag.modify", tag);
		Cache.tag.clear();
		Cache.tags.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("tag.remove", id);
		Cache.tag.clear();
		Cache.tags.clear();
		return id;
	}
}