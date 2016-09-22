package com.raintr.pinshe.bean;

import java.util.Date;

import com.raintr.pinshe.utils.DateGlobal;

public class TagBean {
	private int id;
	private int product_id;
	private int post_id;
	private int t1;
	private int t2;
	private int t3;
	private Date create_time;
	private Date modify_time;
	
	private GroupBean group;
	private ProductBean product;
	private PostBean post;
	
	
	public TagBean(){
		group = new GroupBean();
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToT1Id(String table){return String.format("\"%st1_id\":%d", 					table, t1);}
	public String ToT2Id(String table){return String.format("\"%st2_id\":%d", 					table, t2);}
	public String ToT1(String table){return String.format("\"%st1\":\"%s\"", 					table, T1());}
//	public String ToT2(String table){return String.format("\"%st2\":\"%s\"", 					table, T2());}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, DateGlobal.GetDistanceTime(modify_time));}

	public String T1(){
		if(t1 == 1)
			return "推荐";
		else if(t1 == 2)
			return "吐槽";
		
		return "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int productId) {
		product_id = productId;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int postId) {
		post_id = postId;
	}

	public int getT1() {
		return t1;
	}

	public void setT1(int t1) {
		this.t1 = t1;
	}

	public int getT2() {
		return t2;
	}

	public void setT2(int t2) {
		this.t2 = t2;
	}

	public int getT3() {
		return t3;
	}

	public void setT3(int t3) {
		this.t3 = t3;
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

	public GroupBean getGroup() {
		return group;
	}

	public void setGroup(GroupBean group) {
		this.group = group;
	}

	public ProductBean getProduct() {
		return product;
	}

	public void setProduct(ProductBean product) {
		this.product = product;
	}

	public PostBean getPost() {
		return post;
	}

	public void setPost(PostBean post) {
		this.post = post;
	}
}
