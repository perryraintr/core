package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.StoreCommentBean;
import com.raintr.pinshe.dao.MemberDao;
import com.raintr.pinshe.dao.StoreCommentDao;

public class StoreCommentService {
	private MemberDao memberDao;
	private StoreCommentDao storeCommentDao;
	
	public StoreCommentBean ById(int id){
		return storeCommentDao.ById(id); 
	}
	
	public StoreCommentBean ByStoreIdOrderIdMemberId(int storeId, int memberId, int orderId){
		return storeCommentDao.ByStoreIdOrderIdMemberId(storeId, memberId, orderId);
	}
	
	public List<StoreCommentBean> ByStoreId(int postId, int page) throws Exception{
		StoreCommentBean storeComment;
		List<StoreCommentBean> storeComments = storeCommentDao.ByStoreId(postId, page);
		if(storeComments != null && storeComments.size() > 0){
			if(storeComments != null && storeComments.size() > 0){
				for(int i = 0; i < storeComments.size(); i++){
					storeComment = storeComments.get(i);
					storeComment.setMember(memberDao.ById(storeComment.getMember_id()));
				}
			}
			
			return storeComments;
		}
		
		return null;
	}
	
	public int Add(StoreCommentBean storeComment){
		return storeCommentDao.Add(storeComment); 
	}

	
	
	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public StoreCommentDao getStoreCommentDao() {
		return storeCommentDao;
	}

	public void setStoreCommentDao(StoreCommentDao storeCommentDao) {
		this.storeCommentDao = storeCommentDao;
	}
}
