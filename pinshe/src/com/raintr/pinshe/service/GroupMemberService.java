package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.GroupMemberBean;
import com.raintr.pinshe.dao.GroupMemberDao;
import com.raintr.pinshe.dao.UserDao;

public class GroupMemberService {
	private GroupMemberDao groupMemberDao;
	private UserDao userDao;
	
	public GroupMemberService(){}
	
	public List<GroupMemberBean> ByGroupId(int groupId, int page){
		GroupMemberBean groupMember;
		List<GroupMemberBean> groupMembers = groupMemberDao.ByGroupId(groupId, page);
		for(int i = 0; i < groupMembers.size(); i++){
			groupMember = groupMembers.get(i);
			groupMember.setUser(userDao.ById(groupMember.getUser_id()));
		}
		return groupMembers;
	}
	
	public GroupMemberBean ById(int id){	
		return groupMemberDao.ById(id);
	}
	
	public GroupMemberBean ByGroupIdUserId(GroupMemberBean groupMember){	
		return groupMemberDao.ByGroupIdUserId(groupMember);
	}
	
	public int Add(GroupMemberBean groupMember){
		return groupMemberDao.Add(groupMember);
	}
	
	public int Modify(GroupMemberBean groupMember){
		return groupMemberDao.Modify(groupMember);
	}
	
	public int Remove(int id){
		return groupMemberDao.Remove(id);
	}

	
	
	public GroupMemberDao getGroupMemberDao() {
		return groupMemberDao;
	}

	public void setGroupMemberDao(GroupMemberDao groupMemberDao) {
		this.groupMemberDao = groupMemberDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
