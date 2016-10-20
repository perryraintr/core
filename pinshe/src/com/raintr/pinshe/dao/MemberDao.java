package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class MemberDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<MemberBean> By(int page){
		String key = "member.by" + page;
		if(Cache.members.containsKey(key))
	    	return Cache.members.get(key);
		
		List<MemberBean> members = (List<MemberBean>)getSqlMapClientTemplate().queryForList("member.by", page);
		Cache.members.put(key, members);
		return members;
	}
	
	public MemberBean ById(int id){
		String key = "member.byId" + id;
		if(Cache.member.containsKey(key)){
			synchronized (Cache.memberObject){
				return Cache.member.get(key);
			}
		}
		
		MemberBean member = (MemberBean)getSqlMapClientTemplate().queryForObject("member.byId", id);
		Cache.member.put(key, member);
		return member;
	}
	
	public MemberBean ByWechatId(String wechatId){
		String key = "member.byWechatId" + wechatId;
		if(Cache.member.containsKey(key))
	    	return Cache.member.get(key);
		
		MemberBean member = (MemberBean)getSqlMapClientTemplate().queryForObject("member.byWechatId", wechatId);
		Cache.member.put(key, member);
		return member;
	}
	
	public MemberBean ByPhone(String phone){
		String key = "member.byPhone" + phone;
		if(Cache.member.containsKey(key))
	    	return Cache.member.get(key);
		
		MemberBean member = (MemberBean)getSqlMapClientTemplate().queryForObject("member.byPhone", phone);
		Cache.member.put(key, member);
		return member;
	}
	
	public MemberBean ByPhonePassword(String phone, String password){
		String key = "member.byPhonePassword" + phone + password;
		if(Cache.member.containsKey(key))
	    	return Cache.member.get(key);
		
		MemberBean member = new MemberBean();
		member.setPhone(phone);
		member.setPassword(password);
		
		member = (MemberBean)getSqlMapClientTemplate().queryForObject("member.byPhonePassword", member);
		Cache.member.put(key, member);
		return member;
	}
	
	public MemberBean ByName(String name){
		String key = "member.byName" + name;
		if(Cache.member.containsKey(key))
	    	return Cache.member.get(key);
		
		MemberBean member = (MemberBean)getSqlMapClientTemplate().queryForObject("member.byName", name);
		Cache.member.put(key, member);
		return member;
	}
	
	public int Add(MemberBean member){
		int id = (Integer)getSqlMapClientTemplate().insert("member.add", member);
		synchronized (Cache.memberObject){
			Cache.member.clear();
			Cache.members.clear();
		}
		return id;
	}
	
	public int Modify(MemberBean member){
		int id = (Integer)getSqlMapClientTemplate().update("member.modify", member);
		synchronized (Cache.memberObject){
			Cache.member.clear();
			Cache.members.clear();
		}
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("member.remove", id);
		synchronized (Cache.memberObject){
			Cache.member.clear();
			Cache.members.clear();
		}
		return id;
	}
}