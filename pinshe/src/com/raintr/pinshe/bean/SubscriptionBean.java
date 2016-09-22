package com.raintr.pinshe.bean;

import java.util.Date;

public class SubscriptionBean {
	private int id;
	private int member_id;
	private int day;
	private Date next_time;
	private int commodity_id1;
	private int status1;
	private int commodity_id2;
	private int status2;
	private int commodity_id3;
	private int status3;
	private int commodity_id4;
	private int status4;
	private int commodity_id5;
	private int status5;
	private int commodity_id6;
	private int status6;
	private Date create_time;
	private Date modify_time;

	private ConsigneeBean consignee;
	private CommodityBean commodity1;
	private CommodityBean commodity2;
	private CommodityBean commodity3;
	private CommodityBean commodity4;
	private CommodityBean commodity5;
	private CommodityBean commodity6;
	private MemberBean member;
	
	public SubscriptionBean(){
		next_time = new Date();
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
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

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public Date getNext_time() {
		return next_time;
	}

	public void setNext_time(Date next_time) {
		this.next_time = next_time;
	}

	public int getCommodity_id1() {
		return commodity_id1;
	}

	public void setCommodity_id1(int commodity_id1) {
		this.commodity_id1 = commodity_id1;
	}

	public int getStatus1() {
		return status1;
	}

	public void setStatus1(int status1) {
		this.status1 = status1;
	}

	public int getCommodity_id2() {
		return commodity_id2;
	}

	public void setCommodity_id2(int commodity_id2) {
		this.commodity_id2 = commodity_id2;
	}

	public int getStatus2() {
		return status2;
	}

	public void setStatus2(int status2) {
		this.status2 = status2;
	}

	public int getCommodity_id3() {
		return commodity_id3;
	}

	public void setCommodity_id3(int commodity_id3) {
		this.commodity_id3 = commodity_id3;
	}

	public int getStatus3() {
		return status3;
	}

	public void setStatus3(int status3) {
		this.status3 = status3;
	}

	public int getCommodity_id4() {
		return commodity_id4;
	}

	public void setCommodity_id4(int commodity_id4) {
		this.commodity_id4 = commodity_id4;
	}

	public int getStatus4() {
		return status4;
	}

	public void setStatus4(int status4) {
		this.status4 = status4;
	}

	public int getCommodity_id5() {
		return commodity_id5;
	}

	public void setCommodity_id5(int commodity_id5) {
		this.commodity_id5 = commodity_id5;
	}

	public int getStatus5() {
		return status5;
	}

	public void setStatus5(int status5) {
		this.status5 = status5;
	}

	public int getCommodity_id6() {
		return commodity_id6;
	}

	public void setCommodity_id6(int commodity_id6) {
		this.commodity_id6 = commodity_id6;
	}

	public int getStatus6() {
		return status6;
	}

	public void setStatus6(int status6) {
		this.status6 = status6;
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
	

	public ConsigneeBean getConsignee() {
		return consignee;
	}

	public void setConsignee(ConsigneeBean consignee) {
		this.consignee = consignee;
	}

	public CommodityBean getCommodity1() {
		return commodity1;
	}

	public void setCommodity1(CommodityBean commodity1) {
		this.commodity1 = commodity1;
	}

	public CommodityBean getCommodity2() {
		return commodity2;
	}

	public void setCommodity2(CommodityBean commodity2) {
		this.commodity2 = commodity2;
	}

	public CommodityBean getCommodity3() {
		return commodity3;
	}

	public void setCommodity3(CommodityBean commodity3) {
		this.commodity3 = commodity3;
	}

	public CommodityBean getCommodity4() {
		return commodity4;
	}

	public void setCommodity4(CommodityBean commodity4) {
		this.commodity4 = commodity4;
	}

	public CommodityBean getCommodity5() {
		return commodity5;
	}

	public void setCommodity5(CommodityBean commodity5) {
		this.commodity5 = commodity5;
	}

	public CommodityBean getCommodity6() {
		return commodity6;
	}

	public void setCommodity6(CommodityBean commodity6) {
		this.commodity6 = commodity6;
	}

	public MemberBean getMember() {
		return member;
	}

	public void setMember(MemberBean member) {
		this.member = member;
	}
}
