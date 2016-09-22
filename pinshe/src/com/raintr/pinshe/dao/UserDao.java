package com.raintr.pinshe.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class UserDao extends SqlMapClientDaoSupport {
	public UserBean ById(int id){
		String key = "user.byId" + id;
		if(Cache.user.containsKey(key))
	    	return Cache.user.get(key);
		
		UserBean user = (UserBean)getSqlMapClientTemplate().queryForObject("user.byId", id);
		Cache.user.put(key, user);
		return user;
	}
	
	public UserBean ByWechatGuid(String wechat_guid){
		String key = "user.byWechatGuid" + wechat_guid;
		if(Cache.user.containsKey(key))
	    	return Cache.user.get(key);
		
		UserBean user = (UserBean)getSqlMapClientTemplate().queryForObject("user.byWechatGuid", wechat_guid);
		Cache.user.put(key, user);
		return user;
	}
	
	public int Add(UserBean user){
		int id = (Integer)getSqlMapClientTemplate().insert("user.add", user);
		Cache.user.clear();
		return id;
	}
	
	public int Modify(UserBean user){
		int id = (Integer)getSqlMapClientTemplate().update("user.modify", user);
		Cache.user.clear();
		return id;
	}
}