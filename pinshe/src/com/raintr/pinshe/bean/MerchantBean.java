package com.raintr.pinshe.bean;

import java.util.Date;

public class MerchantBean {
	private int id;
	private String wechat_id;
	private String name;
	private String password;
	private String phone;
	private int device; // 0 ios 1 android 2 html5
	private String getui_id;
	private String avatar;
	private double current;
	private double amount;
	private Date create_time;
	private Date modify_time;
	
	public MerchantBean(){
		wechat_id = "";
		name = "";
		password = "";
		phone = "";
		getui_id = "";
		avatar = "";
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToWechat_id(String table){return String.format("\"%swechat_id\":\"%s\"", 		table, wechat_id);}
	public String ToName(String table){return String.format("\"%sname\":\"%s\"", 				table, name);}
	public String ToPhone(String table){return String.format("\"%sphone\":\"%s\"", 				table, phone);}
	public String ToDevice(String table){return String.format("\"%sdevice\":%d", 				table, device);}
	public String ToGetui_id(String table){return String.format("\"%sgetui_id\":\"%s\"", 		table, getui_id);}
	public String ToAvatar(String table){return String.format("\"%savatar\":\"%s\"", 			table, avatar);}
	public String ToCurrent(String table){return String.format("\"%scurrent\":%.2f", 			table, current);}
	public String ToAmount(String table){return String.format("\"%samount\":%.2f", 				table, amount);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 	table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, modify_time);}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWechat_id() {
		return wechat_id;
	}

	public void setWechat_id(String wechat_id) {
		this.wechat_id = wechat_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getDevice() {
		return device;
	}

	public void setDevice(int device) {
		this.device = device;
	}

	public String getGetui_id() {
		return getui_id;
	}

	public void setGetui_id(String getui_id) {
		this.getui_id = getui_id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
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
}
