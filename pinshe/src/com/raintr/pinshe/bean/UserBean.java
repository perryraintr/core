package com.raintr.pinshe.bean;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.raintr.pinshe.utils.DateGlobal;

public class UserBean {
	private int id;
	private String name;
	private String wechat_guid;
	private String wechat;
	private String phone;
	private String address;
	private String avatar;
	private String password;
	private Date create_time;
	private Date modify_time;
	
	public UserBean(){
		name = "";
		wechat_guid = "";
		wechat = "";
		phone = "";
		address = "";
		avatar = "";
		password = "";
		create_time = new Date();
		modify_time = new Date();
	}
	
	public UserBean(JSONObject json){
		wechat_guid = json.getString("username");
		name = json.getString("nickname");
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToName(String table){return String.format("\"%sname\":\"%s\"", 				table, name);}
	public String ToWechat_guid(String table){return String.format("\"%swechat_guid\":\"%s\"", 	table, wechat_guid);}
	public String ToWechat(String table){return String.format("\"%swechat\":\"%s\"", 			table, wechat);}
	public String ToPhone(String table){return String.format("\"%sphone\":\"%s\"", 				table, phone);}
	public String ToAddress(String table){return String.format("\"%saddress\":\"%s\"", 			table, address);}
	public String ToAvatar(String table){return String.format("\"%savatar\":\"%s\"", 			table, avatar);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, DateGlobal.GetDistanceTime(modify_time));}
	
	public UserBean Clone(){
		UserBean user = new UserBean();
		
		user.id = this.id;
		user.name = this.name;
		user.wechat_guid = this.wechat_guid;
		user.wechat = this.wechat;
		user.phone = this.phone;
		user.address = this.address;
		user.avatar = this.avatar;
		user.password = this.password;
		user.create_time = this.create_time;
		user.modify_time = this.modify_time;
		
		return user;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWechat_guid() {
		return wechat_guid;
	}

	public void setWechat_guid(String wechat_guid) {
		this.wechat_guid = wechat_guid;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
