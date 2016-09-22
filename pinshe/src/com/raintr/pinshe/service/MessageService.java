package com.raintr.pinshe.service;

import java.util.Date;
import java.util.List;

import com.raintr.pinshe.bean.MessageBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.dao.MessageDao;
import com.raintr.pinshe.dao.PostDao;
import com.raintr.pinshe.dao.TagDao;
import com.raintr.pinshe.dao.UserDao;
import com.raintr.pinshe.dao.VoteDao;

public class MessageService {
	private UserDao userDao;
	private VoteDao voteDao;
	private PostDao postDao;
	private TagDao tagDao;
	private MessageDao messageDao;
	
	public MessageBean ById(int id){
		return messageDao.ById(id);
	}
	
	public List<MessageBean> ByUserId(int userId, int page) throws Exception{
		VoteBean vote;
		PostBean post;
		MessageBean message;
		List<MessageBean> messages = messageDao.ByUserId(userId, page);

		if(messages != null && messages.size() > 0){
			if(messages != null && messages.size() > 0){
				for(int i = 0; i < messages.size(); i++){
					message = messages.get(i);
					
					if(message.getVote_id() > 0){
						vote = voteDao.ById(message.getVote_id());
						vote.setPost_a(postDao.ById(vote.getPost_id_a()));
						message.setVote(vote);
					}else{
						post = postDao.ById(message.getPost_id());
						post.setTags(tagDao.ByPostId(post.getId()));
						message.setPost(post);
					}
					
					message.setUser_a(userDao.ById(message.getUser_id_a()));
				}
			}
			
			return messages;
		}
		
		return null;
	}

	public List<MessageBean> ByUserIdAndModifyTime(int userId, Date modifyTime){
		return messageDao.ByUserIdAndModifyTime(userId, modifyTime);
	}
	
	public int Add(MessageBean message){
		return messageDao.Add(message); 
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public VoteDao getVoteDao() {
		return voteDao;
	}

	public void setVoteDao(VoteDao voteDao) {
		this.voteDao = voteDao;
	}

	public PostDao getPostDao() {
		return postDao;
	}

	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}

	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
}
