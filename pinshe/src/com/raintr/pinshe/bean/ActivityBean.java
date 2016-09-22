package com.raintr.pinshe.bean;

import java.util.Date;

public class ActivityBean {
	private int id;
	private int member_id;
	private int sharer_id;
	private int count;
	private int is3; // 0 false 1 true
	private double amount;
	private Date create_time;
	private Date modify_time;
	
	private MemberBean member;
	private MemberBean sharer;
	
	public ActivityBean(){
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToCount(String table){return String.format("\"%scount\":%d", 					table, count);}
	public String ToIs3(String table){return String.format("\"%sis3\":%d", 						table, is3);}
	public String ToAmount(String table){return String.format("\"%samount\":%.2f", 				table, amount);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 	table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, modify_time);}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getSharer_id() {
		return sharer_id;
	}

	public void setSharer_id(int sharer_id) {
		this.sharer_id = sharer_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getIs3() {
		return is3;
	}

	public void setIs3(int is3) {
		this.is3 = is3;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public MemberBean getSharer() {
		return sharer;
	}

	public void setSharer(MemberBean sharer) {
		this.sharer = sharer;
	}
}
