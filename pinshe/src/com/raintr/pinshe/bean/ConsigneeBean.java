package com.raintr.pinshe.bean;

import java.util.Date;

public class ConsigneeBean {
	private int id;
	private int member_id;
	private String name;
	private String phone;
	private String address;
	private String zip;
	private int status;
	private Date create_time;
	private Date modify_time;
	
	public ConsigneeBean(){
		name = "";
		phone = "";
		address = "";
		zip = "";
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToMember_id(String table){return String.format("\"%smember_id\":%d", 			table, member_id);}
	public String ToName(String table){return String.format("\"%sname\":\"%s\"", 				table, name);}
	public String ToPhone(String table){return String.format("\"%sphone\":\"%s\"", 				table, phone);}
	public String ToAddress(String table){return String.format("\"%saddress\":\"%s\"", 			table, address);}
	public String ToZip(String table){return String.format("\"%szip\":\"%s\"", 					table, zip);}
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
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
}
