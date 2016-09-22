package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.CommentBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.bean.WishBean;
import com.raintr.pinshe.dao.CommentDao;
import com.raintr.pinshe.dao.PostDao;
import com.raintr.pinshe.dao.ProductDao;
import com.raintr.pinshe.dao.TagDao;
import com.raintr.pinshe.dao.UserDao;
import com.raintr.pinshe.dao.VoteDao;
import com.raintr.pinshe.dao.WishDao;

public class WishService {
	private WishDao wishDao;
	private VoteDao voteDao;
	private PostDao postDao;
	private UserDao userDao;
	private TagDao tagDao;
	private CommentDao commentDao;
	private ProductDao productDao;
	
	public WishBean ById(int id){
		return wishDao.ById(id);
	}
	
	public List<WishBean> ByUserId(int userId, int page) throws Exception{		
		WishBean wish;
		VoteBean vote;
		PostBean post;
		ProductBean product;
		CommentBean comment;
		List<CommentBean> comments;
		List<WishBean> wishs = wishDao.ByUserId(userId, page);

		if(wishs != null && wishs.size() > 0){
			for(int i = 0; i < wishs.size(); i++){
				wish = wishs.get(i);
				
				if(wish.getPost_id() > 0){
					post = postDao.ById(wish.getPost_id());
					post.setUser(userDao.ById(post.getUser_id()));
					post.setTags(tagDao.ByPostId(post.getId()));
					
					comments = commentDao.ByPostId(post.getId(), 1);
					if(comments != null && comments.size() > 0){
						comment = comments.get(0);
						comment.setUser_a(userDao.ById(comment.getUser_id_a()));
						post.setLastComment(comment);
					}
					
					wish.setPost(post);
				}else{
					vote = voteDao.ById(wish.getVote_id());
					
					vote.setUser(userDao.ById(vote.getUser_id()));
					
					if(vote.getPost_id_a() > 0){
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
					}

					if(vote.getProduct_id_a() > 0){
						product = productDao.ById(vote.getProduct_id_a());
						vote.setProduct_a(product);
						
						product = productDao.ById(vote.getProduct_id_b());
						vote.setProduct_b(product);
					}
					
					wish.setVote(vote);
				}
			}
			
			return wishs;
		}

		return null;
	}
	
	public WishBean ByUserIdPostId(int userId, int postId){
		return wishDao.ByUserIdPostId(userId, postId);
	}
	
	public WishBean ByUserIdVoteId(int userId, int voteId){
		return wishDao.ByUserIdVoteId(userId, voteId);
	}
	
	public int Add(WishBean wish){
		return wishDao.Add(wish);
	}
	
	public int Modify(WishBean wish){
		return wishDao.Modify(wish);
	}
	
	public int Remove(int id){
		return wishDao.Remove(id);
	}
	
	public int RemoveByUserIdPostId(int userId, int postId){
		return wishDao.RemoveByUserIdPostId(userId, postId);
	}
	
	public int RemoveByUserIdVoteId(int userId, int voteId){
		return wishDao.RemoveByUserIdVoteId(userId, voteId);
	}

	public WishDao getWishDao() {
		return wishDao;
	}

	public void setWishDao(WishDao wishDao) {
		this.wishDao = wishDao;
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

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
}
