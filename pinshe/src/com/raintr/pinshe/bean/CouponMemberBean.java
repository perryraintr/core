package com.raintr.pinshe.bean;

import java.util.Date;

public class CouponMemberBean {
	private int id;
	private int coupon_id;
	private int member_id;
	private int status; // 0 not use 1 used
	private Date create_time;
	private Date modify_time;
	
	private CouponBean coupon;
	private MemberBean member;
	
	public CouponMemberBean(){
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToStatus(String table){return String.format("\"%sstatus\":%d", 				table, status);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 	table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, modify_time);}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
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

	public CouponBean getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponBean coupon) {
		this.coupon = coupon;
	}

	public MemberBean getMember() {
		return member;
	}

	public void setMember(MemberBean member) {
		this.member = member;
	}
}
