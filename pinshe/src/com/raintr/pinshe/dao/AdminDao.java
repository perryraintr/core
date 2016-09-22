package com.raintr.pinshe.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.AdminBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class AdminDao extends SqlMapClientDaoSupport {
	public AdminBean ById(int id){
		String key = "admin.byId" + id;
		if(Cache.admin.containsKey(key))
			return Cache.admin.get(key);
		
		AdminBean admin = (AdminBean)getSqlMapClientTemplate().queryForObject("admin.byId", id);
		Cache.admin.put(key, admin);
		return admin;
	}
	
	public AdminBean ByPhone(String phone){
		String key = "admin.byPhone" + phone;
		if(Cache.admin.containsKey(key))
			return Cache.admin.get(key);
		
		AdminBean admin = (AdminBean)getSqlMapClientTemplate().queryForObject("admin.byPhone", phone);
		Cache.admin.put(key, admin);
		return admin;
	}
	
	
	public AdminBean ByPhonePassword(String phone, String password){
		String key = "admin.byPhonePassword" + phone + password;
		if(Cache.admin.containsKey(key))
	    	return Cache.admin.get(key);
		
		AdminBean admin = new AdminBean();
		admin.setPhone(phone);
		admin.setPassword(password);
		
		admin = (AdminBean)getSqlMapClientTemplate().queryForObject("admin.byPhonePassword", admin);
		Cache.admin.put(key, admin);
		return admin;
	}
	
	
	public int Add(AdminBean admin){
		int id = (Integer)getSqlMapClientTemplate().insert("admin.add", admin);
		Cache.admin.clear();
		return id;
	}
	
	public int Modify(AdminBean admin){
		int id = (Integer)getSqlMapClientTemplate().update("admin.modify", admin);
		Cache.admin.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("admin.remove", id);
		Cache.admin.clear();
		return id;
	}
}