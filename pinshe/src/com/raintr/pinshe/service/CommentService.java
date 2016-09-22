package com.raintr.pinshe.service;

import java.util.List;
import com.raintr.pinshe.bean.CommentBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.dao.CommentDao;
import com.raintr.pinshe.dao.PostDao;
import com.raintr.pinshe.dao.UserDao;

public class CommentService {
	private UserDao userDao;
	private PostDao postDao;
	private CommentDao commentDao;
	
	public CommentBean ById(int id){
		return commentDao.ById(id); 
	}
	
	public List<CommentBean> ByPostId(int postId, int page) throws Exception{
		CommentBean comment;
		List<CommentBean> comments = commentDao.ByPostId(postId, page);

		if(comments != null && comments.size() > 0){
			if(comments != null && comments.size() > 0){
				for(int i = 0; i < comments.size(); i++){
					comment = comments.get(i);
					comment.setUser_a(userDao.ById(comment.getUser_id_a()));
					comment.setUser_b(userDao.ById(comment.getUser_id_b()));
				}
			}
			
			return comments;
		}
		
		return null;
	}
	
	public List<CommentBean> ByVoteId(int voteId, int page) throws Exception{		
		CommentBean comment;
		List<CommentBean> comments = commentDao.ByVoteId(voteId, page);

		if(comments != null && comments.size() > 0){
			if(comments != null && comments.size() > 0){
				for(int i = 0; i < comments.size(); i++){
					comment = comments.get(i);
					comment.setUser_a(userDao.ById(comment.getUser_id_a()));
					comment.setUser_b(userDao.ById(comment.getUser_id_b()));
				}
			}
			
			return comments;
		}
		
		return null;
	}
	
	public List<CommentBean> ByModifyTime(int t1, int page){
		PostBean post;
		CommentBean comment;
		List<CommentBean> comments = commentDao.ByModifyTime(t1, page);
		if(comments != null && comments.size() > 0){
			if(comments != null && comments.size() > 0){
				for(int i = 0; i < comments.size(); i++){
					comment = comments.get(i);
					post = postDao.ById(comment.getPost_id());
					post.setUser(userDao.ById(post.getUser_id()));
					comment.setPost(post);
					comment.setUser_a(userDao.ById(comment.getUser_id_a()));
					comment.setUser_b(userDao.ById(comment.getUser_id_b()));
				}
			}
			
			return comments;
		}
		
		return null;
	}
	
	public int Add(CommentBean comment){
		return commentDao.Add(comment); 
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

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
}
