package com.raintr.pinshe.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CouponBean {
	private int id;
	private int member_id;
	private int current;
	private int count;
	private double amount;
	private Date expire_time;
	private int status;// 1 normal 2 member
	private Date create_time;
	private Date modify_time;
	
	private MemberBean member;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public CouponBean(){
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToCurrent(String table){return String.format("\"%scurrent\":%d", 				table, current);}
	public String ToCount(String table){return String.format("\"%scount\":%d", 					table, count);}
	public String ToAmount(String table){return String.format("\"%samount\":%.2f", 				table, amount);}
	public String ToExpire_time(String table){return String.format("\"%sexpire_time\":\"%s\"", 	table, format.format(expire_time));}
	public String ToStatus(String table){return String.format("\"%sstatus\":%d", 				table, status);}
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

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(Date expire_time) {
		this.expire_time = expire_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
