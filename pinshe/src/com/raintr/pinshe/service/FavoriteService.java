package com.raintr.pinshe.service;

import com.raintr.pinshe.bean.FavoriteBean;
import com.raintr.pinshe.dao.FavoriteDao;

public class FavoriteService {
	private FavoriteDao favoriteDao;

	public FavoriteBean ByUserIdPostId(int userId, int postId){
		return favoriteDao.ByUserIdPostId(userId, postId);
	}
	
	public FavoriteBean ByUserIdVoteId(int userId, int voteId){
		return favoriteDao.ByUserIdVoteId(userId, voteId);
	}
	
	public FavoriteBean ByUserIdProductId(int userId, int productId){
		return favoriteDao.ByUserIdProductId(userId, productId);
	}
	
	public int Add(FavoriteBean favorite){
		return favoriteDao.Add(favorite);
	}
	
	public int RemoveUserIdPostId(int userId, int postId){
		return favoriteDao.RemoveUserIdPostId(userId, postId);
	}
	
	public int RemoveUserIdVoteId(int userId, int voteId){
		return favoriteDao.RemoveUserIdVoteId(userId, voteId);
	}
	
	public int RemoveUserIdProductId(int userId, int productId){
		return favoriteDao.RemoveUserIdProductId(userId, productId);
	}

	public FavoriteDao getFavoriteDao() {
		return favoriteDao;
	}

	public void setFavoriteDao(FavoriteDao favoriteDao) {
		this.favoriteDao = favoriteDao;
	}
}
