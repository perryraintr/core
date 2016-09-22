package com.raintr.pinshe.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.CommentBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class CommentDao extends SqlMapClientDaoSupport {
	public CommentBean ById(int id){
		String key = "comment.byId" + id;
		if(Cache.comment.containsKey(key))
	    	return Cache.comment.get(key);
		
		CommentBean comment = (CommentBean)getSqlMapClientTemplate().queryForObject("comment.byId", id);
		Cache.comment.put(key, comment);
		return comment;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommentBean> ByPostId(int postId, int page){
		String key = "comment.byPostId" + postId + page;
		if(Cache.comments.containsKey(key))
	    	return Cache.comments.get(key);

		CommentBean comment = new CommentBean();
		comment.setPost_id(postId);
		comment.setId(page);
		List<CommentBean> comments = (List<CommentBean>)getSqlMapClientTemplate().queryForList("comment.byPostId", comment);
		Cache.comments.put(key, comments);
		return comments;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommentBean> ByVoteId(int voteId, int page){
		String key = "comment.byVoteId" + voteId + page;
		if(Cache.comments.containsKey(key))
	    	return Cache.comments.get(key);
		
		CommentBean comment = new CommentBean();
		comment.setVote_id(voteId);
		comment.setId(page);
		List<CommentBean> comments = (List<CommentBean>)getSqlMapClientTemplate().queryForList("comment.byVoteId", comment);
		Cache.comments.put(key, comments);
		return comments;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommentBean> ByModifyTime(int t1, int page){
		String key = "comment.byModifyTime" + t1 + page;
		if(Cache.comments.containsKey(key))
	    	return Cache.comments.get(key);
		
		TagBean tag = new TagBean();
		tag.setT1(t1);
		tag.setId(page);
		List<CommentBean> comments = (List<CommentBean>)getSqlMapClientTemplate().queryForList("comment.byModifyTime", tag);
		Cache.comments.put(key, comments);
		return comments;
	}
	
	public int Add(CommentBean comment){
		int id = (Integer)getSqlMapClientTemplate().insert("comment.add", comment);
		Cache.comment.clear();
		Cache.comments.clear();
		return id;
	}
}