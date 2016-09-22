package com.raintr.pinshe.service;

import java.util.List;
import com.raintr.pinshe.bean.CommentBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.PublishBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.dao.CommentDao;
import com.raintr.pinshe.dao.PostDao;
import com.raintr.pinshe.dao.PublishDao;
import com.raintr.pinshe.dao.TagDao;
import com.raintr.pinshe.dao.UserDao;
import com.raintr.pinshe.dao.VoteDao;

public class PublishService {
	private UserDao userDao;
	private PublishDao publishDao;
	private VoteDao voteDao;
	private PostDao postDao;
	private CommentDao commentDao;
	private TagDao tagDao;
	
	public PublishBean ById(int id){
		return publishDao.ById(id);
	}
	
	public List<PublishBean> ByUserId(int userId, int page) throws Exception{
		PublishBean publish;
		CommentBean comment;
		PostBean post;
		VoteBean vote;
		List<CommentBean> comments;
		List<PublishBean> publishs = publishDao.ByUserId(userId, page);

		if(publishs != null && publishs.size() > 0){
			for(int i = 0; i < publishs.size(); i++){
				publish = publishs.get(i);
				
				if(publish.getPost_id() > 0){
					post = postDao.ById(publish.getPost_id());
					post.setTags(tagDao.ByPostId(publish.getPost_id()));
					comments = commentDao.ByPostId(post.getId(), 1);
					if(comments != null && comments.size() > 0){
						comment = comments.get(0);
						comment.setUser_a(userDao.ById(comment.getUser_id_a()));
						post.setLastComment(comment);
					}
					
					publish.setPost(post);
					
				}else{
					vote = voteDao.ById(publish.getVote_id());
					
					post = postDao.ById(vote.getPost_id_a());
					post.setUser(userDao.ById(post.getUser_id()));
					vote.setPost_a(post);
					
					post = postDao.ById(vote.getPost_id_b());
					post.setUser(userDao.ById(post.getUser_id()));
					vote.setPost_b(post);
					
					comments = commentDao.ByVoteId(vote.getId(), 1);
					if(comments != null && comments.size() > 0){
						comment = comments.get(0);
						comment.setUser_a(userDao.ById(comment.getUser_id_a()));
						vote.setLastComment(comment);
					}
					
					publish.setVote(vote);
				}
			}
			
			return publishs;
		}

		return null;
	}

	public int Add(PublishBean publish){
		return publishDao.Add(publish);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public PublishDao getPublishDao() {
		return publishDao;
	}

	public void setPublishDao(PublishDao publishDao) {
		this.publishDao = publishDao;
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

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
}
