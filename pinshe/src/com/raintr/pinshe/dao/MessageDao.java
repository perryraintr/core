package com.raintr.pinshe.dao;

import java.util.Date;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.MessageBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class MessageDao extends SqlMapClientDaoSupport {
	public MessageBean ById(int id){
		String key = "message.byId" + id;
		if(Cache.message.containsKey(key))
	    	return Cache.message.get(key);
		
		MessageBean message = (MessageBean)getSqlMapClientTemplate().queryForObject("message.byId", id);
		Cache.message.put(key, message);
		return message;
	}
	
	@SuppressWarnings("unchecked")
	public List<MessageBean> ByUserId(int userId, int page){
		String key = "message.byUserId" + userId + page;
		if(Cache.messages.containsKey(key))
	    	return Cache.messages.get(key);
		
		MessageBean message = new MessageBean();
		message.setUser_id_b(userId);
		message.setId(page);
		List<MessageBean> messages = (List<MessageBean>)getSqlMapClientTemplate().queryForList("message.byUserId", message);
		Cache.messages.put(key, messages);
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	public List<MessageBean> ByUserIdAndModifyTime(int userId, Date modifyTime){
		String key = "message.byUserIdAndModifyTime" + userId + modifyTime;
		if(Cache.messages.containsKey(key))
	    	return Cache.messages.get(key);
		
		MessageBean message = new MessageBean();
		message.setUser_id_b(userId);
		message.setModify_time(modifyTime);
		List<MessageBean> messages = (List<MessageBean>)getSqlMapClientTemplate().queryForList("message.byUserIdAndModifyTime", message);
		Cache.messages.put(key, messages);
		return messages;
	}
	
	public int Add(MessageBean message){
		int id = (Integer)getSqlMapClientTemplate().insert("message.add", message);
		Cache.message.clear();
		Cache.messages.clear();
		return id;
	}
}