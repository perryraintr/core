package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.GroupMemberBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class GroupMemberDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<GroupMemberBean> ByGroupId(int groupId, int page){
		String key = "groupMember.byGroupId" + groupId + page;
		if(Cache.groupMembers.containsKey(key))
	    	return Cache.groupMembers.get(key);

		GroupMemberBean groupMember = new GroupMemberBean();
		groupMember.setId(page);
		groupMember.setGroup_id(groupId);
		List<GroupMemberBean> groupMembers = (List<GroupMemberBean>)getSqlMapClientTemplate().queryForList("groupMember.byGroupId", groupMember);
		Cache.groupMembers.put(key, groupMembers);
		return groupMembers;
	}
	
	public GroupMemberBean ById(int id){
		String key = "groupMember.byId" + id;
		if(Cache.groupMember.containsKey(key))
	    	return Cache.groupMember.get(key);
		
		GroupMemberBean groupMember = (GroupMemberBean)getSqlMapClientTemplate().queryForObject("groupMember.byId", id);
		Cache.groupMember.put(key, groupMember);
		return groupMember;
	}
	
	public GroupMemberBean ByGroupIdUserId(GroupMemberBean groupMember){
		String key = "groupMember.byGroupIdUserId" + groupMember.getGroup_id() + groupMember.getUser_id();
		if(Cache.groupMember.containsKey(key))
	    	return Cache.groupMember.get(key);
		
		groupMember = (GroupMemberBean)getSqlMapClientTemplate().queryForObject("groupMember.byGroupIdUserId", groupMember);
		Cache.groupMember.put(key, groupMember);
		return groupMember;
	}
	
	public int Add(GroupMemberBean groupMember){
		int id = (Integer)getSqlMapClientTemplate().insert("groupMember.add", groupMember);
		Cache.groupMember.clear();
		Cache.groupMembers.clear();
		return id;
	}
	
	public int Modify(GroupMemberBean groupMember){
		int id = (Integer)getSqlMapClientTemplate().update("groupMember.modify", groupMember);
		Cache.groupMember.clear();
		Cache.groupMembers.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("groupMember.remove", id);
		Cache.groupMember.clear();
		Cache.groupMembers.clear();
		return id;
	}
}