package com.raintr.pinshe.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CashBean {
	private int id;
	private int store_id;
	private int member_id;
	private int order_id;
	private double amount;
	private int type; // -1 out 1 in
	private int status;// 0 not pay 1 paid 2 send 3 finish 4 user cancel
	private Date create_time;
	private Date modify_time;
	
	private StoreBean store;
	private MemberBean member;
	private OrderBean order;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public CashBean(){
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 									table, id);}
	public String ToAmount(String table){return String.format("\"%samount\":%.2f", 							table, amount);}
	public String ToType(String table){return String.format("\"%stype\":%d", 								table, type);}
	public String ToStatus(String table){return String.format("\"%sstatus\":%d", 							table, status);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 				table, sdf.format(create_time));}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 				table, sdf.format(modify_time));}

	
	
	
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

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public StoreBean getStore() {
		return store;
	}

	public void setStore(StoreBean store) {
		this.store = store;
	}

	public MemberBean getMember() {
		return member;
	}

	public void setMember(MemberBean member) {
		this.member = member;
	}

	public OrderBean getOrder() {
		return order;
	}

	public void setOrder(OrderBean order) {
		this.order = order;
	}
}
