package com.raintr.pinshe.bean;

import java.util.Date;

public class RecommendBean {
	private int id;
	private int store_id;
	private int commodity_id;
	private String message;
	private Date create_time;
	private Date modify_time;
	
	private StoreBean store;
	private CommodityBean commodity;
	
	public RecommendBean(){
		message = "";
		create_time = new Date();
		modify_time = new Date();
	}

	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToStore_id(String table){return String.format("\"%sstore_id\":%d", 			table, store_id);}
	public String ToCommodity_id(String table){return String.format("\"%scommodity_id\":%d", 	table, commodity_id);}
	public String ToMessage(String table){return String.format("\"%smessage\":\"%s\"", 			table, message);}
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

	public int getCommodity_id() {
		return commodity_id;
	}

	public void setCommodity_id(int commodity_id) {
		this.commodity_id = commodity_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public StoreBean getStore() {
		return store;
	}

	public void setStore(StoreBean store) {
		this.store = store;
	}

	public CommodityBean getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityBean commodity) {
		this.commodity = commodity;
	}
}
