package com.raintr.pinshe.service;

import java.util.Date;
import java.util.List;

import com.raintr.pinshe.bean.GroupMessageBean;
import com.raintr.pinshe.dao.GroupMessageDao;
import com.raintr.pinshe.dao.PostDao;
import com.raintr.pinshe.dao.UserDao;

public class GroupMessageService {	
	private GroupMessageDao groupMessageDao;
	private UserDao userDao;
	private PostDao postDao;
	
	public GroupMessageService(){}

	public List<GroupMessageBean> ByCreateTime(int groupId, Date createTime){
		GroupMessageBean groupMessage;
		List<GroupMessageBean> groupMessages = groupMessageDao.ByCreateTime(groupId, createTime);
		if(groupMessages != null && groupMessages.size() > 0){
			for(int i = 0; i < groupMessages.size(); i++){
				groupMessage = groupMessages.get(i);
				groupMessage.setUser(userDao.ById(groupMessage.getUser_id()));
				groupMessage.setPost(postDao.ById(groupMessage.getPost_id()));
			}
		}
		
		return groupMessages;
	}
	
	public int Add(GroupMessageBean groupMessage){
		return groupMessageDao.Add(groupMessage);
	}
	
	public int Remove(int id){
		return groupMessageDao.Remove(id);
	}

	
	public GroupMessageDao getGroupMessageDao() {
		return groupMessageDao;
	}

	public void setGroupMessageDao(GroupMessageDao groupMessageDao) {
		this.groupMessageDao = groupMessageDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public PostDao getPostDao() {
		return postDao;
	}

	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}
}
