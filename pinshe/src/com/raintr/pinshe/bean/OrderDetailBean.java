package com.raintr.pinshe.bean;

import java.util.Date;

public class OrderDetailBean {
	private int id;
	private int order_id;
	private int commodity_id;
	private int store_id;
	private int count;
	private double amount;
	private Date create_time;
	private Date modify_time;
	
	private OrderBean order;
	private CommodityBean commodity;
	private StoreBean store;
	
	public OrderDetailBean(){
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 									table, id);}
	public String ToCount(String table){return String.format("\"%scount\":%d", 								table, count);}
	public String ToAmount(String table){return String.format("\"%samount\":%.2f", 							table, amount);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 				table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 				table, modify_time);}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getCommodity_id() {
		return commodity_id;
	}

	public void setCommodity_id(int commodity_id) {
		this.commodity_id = commodity_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
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

	public OrderBean getOrder() {
		return order;
	}

	public void setOrder(OrderBean order) {
		this.order = order;
	}

	public CommodityBean getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityBean commodity) {
		this.commodity = commodity;
	}

	public StoreBean getStore() {
		return store;
	}

	public void setStore(StoreBean store) {
		this.store = store;
	}
}
