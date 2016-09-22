package com.raintr.pinshe.bean;

import java.util.Date;

import com.raintr.pinshe.utils.DateGlobal;

public class VoteBean {
	private int id;
	private int user_id;
	private String name;
	private int post_id_a;
	private int post_id_b;
	private int product_id_a;
	private int product_id_b;
	private int count_a;
	private int count_b;
	private int view;
	private int comment;
	private int favorite;
	private String description;
	private Date create_time;
	private Date modify_time;
	
	private UserBean user;
	private PostBean post_a;
	private PostBean post_b;
	private ProductBean product_a;
	private ProductBean product_b;
	private CommentBean lastComment;
	
	public VoteBean(){
		name = "";
		description = "";
		create_time = new Date();
		modify_time = new Date();
	}	

	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToUser_id(String table){return String.format("\"%suser_id\":%d", 				table, user_id);}
	public String ToName(String table){return String.format("\"%sname\":\"%s\"", 				table, name);}
	public String ToCount_a(String table){return String.format("\"%scount_a\":%d", 				table, count_a);}
	public String ToCount_b(String table){return String.format("\"%scount_b\":%d", 				table, count_b);}
	public String ToView(String table){return String.format("\"%sview\":%d", 					table, view);}
	public String ToComment(String table){return String.format("\"%scomment\":%d", 				table, comment);}
	public String ToFavorite(String table){return String.format("\"%sfavorite\":%d", 			table, favorite);}
	public String ToDescription(String table){return String.format("\"%sdescription\":\"%s\"", 	table, description);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, DateGlobal.GetDistanceTime(modify_time));}

//	public String ToRate_a(String table){return String.format("\"%srate_a\":%.2f", 				table, count_a + count_b == 0 ? 0 :     (int)((double)count_a / (count_a + count_b) * 100) * 0.01);}
//	public String ToRate_b(String table){return String.format("\"%srate_b\":%.2f", 				table, count_a + count_b == 0 ? 0 : 1 - (int)((double)count_a / (count_a + count_b) * 100) * 0.01);}

	public VoteBean Clone(){
		VoteBean vote = new VoteBean();
		vote.id = this.id;
		vote.user_id = this.user_id;
		vote.name = this.name;
		vote.post_id_a = this.post_id_a;
		vote.post_id_b = this.post_id_b;
		vote.product_id_a = this.product_id_a;
		vote.product_id_b = this.product_id_b;
		vote.count_a = this.count_a;
		vote.count_b = this.count_b;
		vote.view = this.view;
		vote.comment = this.comment;
		vote.favorite = this.favorite;
		vote.description = this.description;
		vote.create_time = this.create_time;
		vote.modify_time = this.modify_time;
		
		if(user != null)
			vote.user = this.user.Clone();
		
		if(post_a != null)
			vote.post_a = this.post_a.Clone();
		
		if(post_b != null)
			vote.post_b = this.post_b.Clone();
		
		if(product_a != null)
			vote.product_a = this.product_a.Clone();
		
		if(product_b != null)
			vote.product_b = this.product_b.Clone();
		
		return vote;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int userId) {
		user_id = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPost_id_a() {
		return post_id_a;
	}

	public void setPost_id_a(int postIdA) {
		post_id_a = postIdA;
	}

	public int getPost_id_b() {
		return post_id_b;
	}

	public void setPost_id_b(int postIdB) {
		post_id_b = postIdB;
	}

	public int getProduct_id_a() {
		return product_id_a;
	}

	public void setProduct_id_a(int product_id_a) {
		this.product_id_a = product_id_a;
	}

	public int getProduct_id_b() {
		return product_id_b;
	}

	public void setProduct_id_b(int product_id_b) {
		this.product_id_b = product_id_b;
	}

	public int getCount_a() {
		return count_a;
	}

	public void setCount_a(int countA) {
		count_a = countA;
	}

	public int getCount_b() {
		return count_b;
	}

	public void setCount_b(int countB) {
		count_b = countB;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public PostBean getPost_a() {
		return post_a;
	}

	public void setPost_a(PostBean postA) {
		post_a = postA;
	}

	public PostBean getPost_b() {
		return post_b;
	}

	public void setPost_b(PostBean postB) {
		post_b = postB;
	}

	public ProductBean getProduct_a() {
		return product_a;
	}

	public void setProduct_a(ProductBean product_a) {
		this.product_a = product_a;
	}

	public ProductBean getProduct_b() {
		return product_b;
	}

	public void setProduct_b(ProductBean product_b) {
		this.product_b = product_b;
	}

	public CommentBean getLastComment() {
		return lastComment;
	}

	public void setLastComment(CommentBean lastComment) {
		this.lastComment = lastComment;
	}
}
