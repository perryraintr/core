package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.GroupBean;
import com.raintr.pinshe.utils.Cache;


@SuppressWarnings("deprecation")
public class GroupDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<GroupBean> By(int page){
		String key = "group.by" + page;
		if(Cache.groups.containsKey(key))
	    	return Cache.groups.get(key);

		List<GroupBean> groups = (List<GroupBean>)getSqlMapClientTemplate().queryForList("group.by", page);
		Cache.groups.put(key, groups);
		return groups;
	}
	
	@SuppressWarnings("unchecked")
	public List<GroupBean> ByType(int type, int page){
		String key = "group.byType" + type + page;
		if(Cache.groups.containsKey(key))
	    	return Cache.groups.get(key);

		GroupBean group = new GroupBean();
		group.setId(page);
		group.setType(type);
		List<GroupBean> groups = (List<GroupBean>)getSqlMapClientTemplate().queryForList("group.byType", group);
		Cache.groups.put(key, groups);
		return groups;
	}
	
	public GroupBean ById(int id){
		String key = "group.byId" + id;
		if(Cache.group.containsKey(key))
	    	return Cache.group.get(key);
		
		GroupBean group = (GroupBean)getSqlMapClientTemplate().queryForObject("group.byId", id);
		Cache.group.put(key, group);
		return group;
	}
	
	public GroupBean ByEasemobId(String id){
		String key = "group.byEasemobId" + id;
		if(Cache.group.containsKey(key))
	    	return Cache.group.get(key);
		
		GroupBean group = (GroupBean)getSqlMapClientTemplate().queryForObject("group.byEasemobId", id);
		Cache.group.put(key, group);
		return group;
	}
	
	public int Add(GroupBean group){
		int id = (Integer)getSqlMapClientTemplate().insert("group.add", group);
		Cache.group.clear();
		Cache.groups.clear();
		return id;
	}
	
	public int Modify(GroupBean group){
		int id = (Integer)getSqlMapClientTemplate().update("group.modify", group);
		Cache.group.clear();
		Cache.groups.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("group.remove", id);
		Cache.group.clear();
		Cache.groups.clear();
		return id;
	}
}