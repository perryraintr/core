package com.raintr.pinshe.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.raintr.pinshe.utils.DateGlobal;

public class MessageBean {
	private int id;
	private int vote_id;
	private int post_id;
	private int user_id_a;
	private int user_id_b;
	private String message;
	private Date create_time;
	private Date modify_time;
	
	private VoteBean vote;
	private PostBean post;
	private UserBean user_a;
	private UserBean user_b;
	
	private SimpleDateFormat sdf;
	
	public MessageBean(){
		message = "";
		create_time = new Date();
		modify_time = new Date();
		
		sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						 table, id);}
	public String ToMessage(String table){return String.format("\"%smessage\":\"%s\"", 			 table, message);}
	public String ToModify_time1(String table){return String.format("\"%smodify_time1\":\"%s\"", table, sdf.format(modify_time));}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	 table, DateGlobal.GetDistanceTime(modify_time));}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getUser_id_a() {
		return user_id_a;
	}

	public void setUser_id_a(int userIdA) {
		user_id_a = userIdA;
	}

	public int getUser_id_b() {
		return user_id_b;
	}

	public void setUser_id_b(int userIdB) {
		user_id_b = userIdB;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public UserBean getUser_a() {
		return user_a;
	}

	public void setUser_a(UserBean userA) {
		user_a = userA;
	}

	public UserBean getUser_b() {
		return user_b;
	}

	public void setUser_b(UserBean userB) {
		user_b = userB;
	}
}
