package com.raintr.pinshe.bean;

import java.util.Date;

public class StorePaymentBean {
	private int id;
	private int store_id;
	private int type; // 0 alipay 1 bank card
	private String company;
	private String account;
	private String holder;
	private Date create_time;
	private Date modify_time;
	
	public StorePaymentBean(){
		company = "";
		account = "";
		holder = "";
		create_time = new Date();
		modify_time = new Date();
	}

	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToStore_id(String table){return String.format("\"%sstore_id\":%d", 			table, store_id);}
	public String ToType(String table){return String.format("\"%stype\":%d", 					table, type);}
	public String ToCompany(String table){return String.format("\"%scompany\":\"%s\"", 			table, company);}
	public String ToAccount(String table){return String.format("\"%saccount\":\"%s\"", 			table, account);}
	public String ToHolder(String table){return String.format("\"%sholder\":\"%s\"", 			table, holder);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 	table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, modify_time);}

	
	
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
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
