package com.raintr.pinshe.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.PublishBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class PublishDao extends SqlMapClientDaoSupport {
	public PublishBean ById(int id){
		String key = "publish.byId" + id;
		if(Cache.publish.containsKey(key))
	    	return Cache.publish.get(key);
		
		PublishBean publish = (PublishBean)getSqlMapClientTemplate().queryForObject("publish.byId", id);
		Cache.publish.put(key, publish);
		return publish;
	}
	
	@SuppressWarnings("unchecked")
	public List<PublishBean> ByUserId(int userId, int page){
		String key = "publish.byUserId" + userId + page;
		if(Cache.publishs.containsKey(key))
	    	return Cache.publishs.get(key);
		
		PublishBean publish = new PublishBean();
		publish.setUser_id(userId);
		publish.setId(page);
		List<PublishBean> publishs = (List<PublishBean>)getSqlMapClientTemplate().queryForList("publish.byUserId", publish);
		Cache.publishs.put(key, publishs);
		return publishs;
	}
	
	public int Add(PublishBean publish){
		int id = (Integer)getSqlMapClientTemplate().insert("publish.add", publish);
		Cache.publish.clear();
		Cache.publishs.clear();
		return id;
	}
}