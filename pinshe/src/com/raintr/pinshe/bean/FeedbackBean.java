package com.raintr.pinshe.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedbackBean {	  
	private int id;
	private String name;
	private String e_mail;
	private Date create_time;
	private Date modify_time;
	
	private SimpleDateFormat sdf;
	
	public FeedbackBean(){
		name = "";
		e_mail = "";
		create_time = new Date();
		modify_time = new Date();
		
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToName(String table){return String.format("\"%sname\":\"%s\"", 				table, name);}
	public String ToE_mail(String table){return String.format("\"%se_mail\":\"%s\"", 			table, e_mail);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, sdf.format(modify_time));}
	
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

	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String eMail) {
		e_mail = eMail;
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
