package com.raintr.pinshe.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StoreCommentBean {
	private int id;
	private int store_id;
	private int member_id;
	private int star;
	private String message;
	private Date create_time;
	private Date modify_time;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
	
	private MemberBean member;
	
	public StoreCommentBean(){
		message = "";
		create_time = new Date();
		modify_time = new Date();
	}

	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToStar(String table){return String.format("\"%sstar\":%d", 					table, star);}
	public String ToMessage(String table){return String.format("\"%smessage\":\"%s\"", 			table, message);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 	table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, modify_time);}

	public String ToCreate_time1(String table){return String.format("\"%screate_time1\":\"%s\"", table, dateFormat.format(create_time));}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
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

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

	public MemberBean getMember() {
		return member;
	}

	public void setMember(MemberBean member) {
		this.member = member;
	}
}
