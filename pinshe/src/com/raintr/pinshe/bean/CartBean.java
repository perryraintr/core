package com.raintr.pinshe.bean;

import java.util.Date;

public class CartBean {
	private int id;
	private int member_id;
	private int commodity_id;
	private int count;
	private Date create_time;
	private Date modify_time;
	
	private MemberBean memeber;
	private CommodityBean commodity;
	
	public CartBean(){
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 									table, id);}
	public String ToCount(String table){return String.format("\"%scount\":%d", 								table, count);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 				table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 				table, modify_time);}

	
	
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

	public int getCommodity_id() {
		return commodity_id;
	}

	public void setCommodity_id(int commodity_id) {
		this.commodity_id = commodity_id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public MemberBean getMemeber() {
		return memeber;
	}

	public void setMemeber(MemberBean memeber) {
		this.memeber = memeber;
	}

	public CommodityBean getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityBean commodity) {
		this.commodity = commodity;
	}
}
