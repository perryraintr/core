package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.dao.PostDao;
import com.raintr.pinshe.dao.ProductDao;
import com.raintr.pinshe.dao.UserDao;
import com.raintr.pinshe.dao.VoteDao;

public class VoteService {
	private VoteDao voteDao;
	private UserDao userDao;
	private PostDao postDao;
	private ProductDao productDao;
	
	public VoteBean ById(int id) throws Exception{
		VoteBean vote = voteDao.ById(id);
		if(vote != null){
			vote.setUser(userDao.ById(vote.getUser_id()));
			
			if(vote.getPost_id_a() > 0){
				PostBean post = postDao.ById(vote.getPost_id_a());
				post.setUser(userDao.ById(post.getUser_id()));
				vote.setPost_a(post);
				
				post = postDao.ById(vote.getPost_id_b());
				post.setUser(userDao.ById(post.getUser_id()));
				vote.setPost_b(post);
			}
			
			if(vote.getProduct_id_a() > 0){
				ProductBean product = productDao.ById(vote.getProduct_id_a());
				vote.setProduct_a(product);
				
				product = productDao.ById(vote.getProduct_id_b());
				vote.setProduct_b(product);
			}
			
			return vote;
		}
		
		return null;
	}
	
	public List<VoteBean> By(int userId, int page) throws Exception{
		VoteBean vote;
		PostBean post;
		ProductBean product;
		
		List<VoteBean> votes = voteDao.By(userId, page);
		
		if(votes != null && votes.size() > 0){
			for(int i = 0; i < votes.size(); i++){
				vote = votes.get(i);
				
				vote.setUser(userDao.ById(vote.getUser_id()));
				
				if(vote.getPost_id_a() > 0){
					post = postDao.ById(vote.getPost_id_a());
					post.setUser(userDao.ById(post.getUser_id()));
					vote.setPost_a(post);
					
					post = postDao.ById(vote.getPost_id_b());
					post.setUser(userDao.ById(post.getUser_id()));
					vote.setPost_b(post);
				}
				
				if(vote.getProduct_id_a() > 0){
					product = productDao.ById(vote.getProduct_id_a());
					vote.setProduct_a(product);
					
					product = productDao.ById(vote.getProduct_id_b());
					vote.setProduct_b(product);
				}
			}
			return votes;
		}
		
		return null;
	}
	
	
	public int Add(VoteBean vote){
		return voteDao.Add(vote);
	}
	
	public int Modify(VoteBean vote){
		return voteDao.Modify(vote);
	}
	
	public VoteDao getVoteDao() {
		return voteDao;
	}

	public void setVoteDao(VoteDao voteDao) {
		this.voteDao = voteDao;
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

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
}
