package com.raintr.pinshe.bean;

import java.util.Date;

import com.raintr.pinshe.utils.DateGlobal;

public class PublishBean {
	private int id;
	private int user_id;
	private int vote_id;
	private int post_id;
	private Date create_time;
	private Date modify_time;
	
	private UserBean user;
	private VoteBean vote;
	private PostBean post;
	
	public PublishBean(){
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, DateGlobal.GetDistanceTime(modify_time));}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int userId) {
		user_id = userId;
	}

	public int getVote_id() {
		return vote_id;
	}

	public void setVote_id(int voteId) {
		vote_id = voteId;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int postId) {
		post_id = postId;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date createTime) {
		create_time = createTime;
	}

	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modifyTime) {
		modify_time = modifyTime;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public VoteBean getVote() {
		return vote;
	}

	public void setVote(VoteBean vote) {
		this.vote = vote;
	}

	public PostBean getPost() {
		return post;
	}

	public void setPost(PostBean post) {
		this.post = post;
	}
}
