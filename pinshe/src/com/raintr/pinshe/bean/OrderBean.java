package com.raintr.pinshe.bean;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderBean {
	private int id;
	private int member_id;
	private int consignee_id;
	private int coupon_id;
	private String order_no;
	private int count;
	private double current;
	private double amount;
	private int type; // 1 member pay 2 wechat pay
	private int status;// 0 not pay 1 paid 2 send 3 finish 4 user cancel
	private int grind;
	private double cost;
	private String memo;
	private Date create_time;
	private Date modify_time;
	
	private MemberBean member;
	private ConsigneeBean consignee;
	private List<OrderDetailBean> orderDetails;
	private CouponBean coupon;
	private Random random = new Random();
	
	public OrderBean(){
		order_no = "";
		memo = "";
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 									table, id);}
	public String ToOrder_no(String table){return String.format("\"%sorder_no\":\"%s\"", 					table, order_no);}
	public String ToCount(String table){return String.format("\"%scount\":%d", 								table, count);}
	public String ToCurrent(String table){return String.format("\"%scurrent\":%.2f", 						table, current);}
	public String ToAmount(String table){return String.format("\"%samount\":%.2f", 							table, amount);}
	public String ToType(String table){return String.format("\"%stype\":%d", 								table, type);}
	public String ToStatus(String table){return String.format("\"%sstatus\":%d", 							table, status);}
	public String ToGrind(String table){return String.format("\"%sgrind\":%d", 								table, grind);}
	public String ToCost(String table){return String.format("\"%scost\":%.2f", 								table, cost);}
	public String ToMemo(String table){return String.format("\"%smemo\":\"%s\"", 							table, memo);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 				table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 				table, modify_time);}

	public String MadeOrderNo(){
		return String.valueOf(new Date().getTime() + random.nextInt(1000000) + member_id);
	}
	
	
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

	public int getConsignee_id() {
		return consignee_id;
	}

	public void setConsignee_id(int consignee_id) {
		this.consignee_id = consignee_id;
	}

	public int getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
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

	public int getGrind() {
		return grind;
	}

	public void setGrind(int grind) {
		this.grind = grind;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public MemberBean getMember() {
		return member;
	}

	public void setMember(MemberBean member) {
		this.member = member;
	}

	public ConsigneeBean getConsignee() {
		return consignee;
	}

	public void setConsignee(ConsigneeBean consignee) {
		this.consignee = consignee;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public List<OrderDetailBean> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetailBean> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public CouponBean getCoupon() {
		return coupon;
	}

	public void setCoupon(CouponBean coupon) {
		this.coupon = coupon;
	}
}
