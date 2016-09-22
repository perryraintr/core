package com.raintr.pinshe.bean;

import java.util.Date;

public class HistoryBean {	  
	private int id;
	private int user_id;
	private int vote_id;
	private Date create_time;
	private Date modify_time;
	
	public HistoryBean(){
		create_time = new Date();
		modify_time = new Date();
	}

	public String ToId(String table){return String.format("\"%sguid\":%d", table, id);}

	
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
