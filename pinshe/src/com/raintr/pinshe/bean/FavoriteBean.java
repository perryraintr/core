package com.raintr.pinshe.bean;

import java.util.Date;

import com.raintr.pinshe.utils.DateGlobal;

public class FavoriteBean {
	private int id;
	private int user_id;
	private int vote_id;
	private int post_id;
	private int product_id;
	private Date create_time;
	private Date modify_time;
		
	public FavoriteBean(){
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

	public void setVote_id(int vote_id) {
		this.vote_id = vote_id;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int postId) {
		post_id = postId;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
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
}
