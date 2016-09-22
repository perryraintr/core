package com.raintr.pinshe.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostBean {
	private int id;
	private int user_id;
	private int product_id;
	private String name;
	private int view;
	private int comment;
	private int favorite;
	private String image;
	private double price;
	private String address;
	private String brand;
	private String description;
	private Date create_time;
	private Date modify_time;
	
	private UserBean user;
	private ProductBean product;
	private CommentBean lastComment;
	private List<TagBean> tags;
	private List<ImageBean> images;
	
	private SimpleDateFormat sdf;
	
	public PostBean(){
		name = "";
		image = "";
		address = "";
		brand = "";
		description = "";
		create_time = new Date();
		modify_time = new Date();
		
		sdf = new SimpleDateFormat("MM月dd日 HH:mm");
	}

	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToName(String table){return String.format("\"%sname\":\"%s\"", 				table, name);}
	public String ToView(String table){return String.format("\"%sview\":%d", 					table, view);}
	public String ToComment(String table){return String.format("\"%scomment\":%d", 				table, comment);}
	public String ToFavorite(String table){return String.format("\"%sfavorite\":%d", 			table, favorite);}
	public String ToImage(String table){return String.format("\"%simage\":\"%s\"", 				table, image);}
	public String ToPrice(String table){return String.format("\"%sprice\":%.2f",				table, price);}
	public String ToAddress(String table){return String.format("\"%saddress\":\"%s\"",			table, address);}
	public String ToBrand(String table){return String.format("\"%sbrand\":\"%s\"",				table, brand);}
	public String ToDescription(String table){return String.format("\"%sdescription\":\"%s\"",	table, description);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"",	table, sdf.format(modify_time));}

	public PostBean Clone(){
		PostBean post = new PostBean();
		
		post.id = this.id;
		post.user_id = this.user_id;
		post.product_id = this.product_id;
		post.name = this.name;
		post.view = this.view;
		post.comment = this.comment;
		post.favorite = this.favorite;
		post.image = this.image;
		post.price = this.price;
		post.address = this.address;
		post.brand = this.brand;
		post.description = this.description;
		post.create_time = this.create_time;
		post.modify_time = this.modify_time;
		
		if(user != null)
			post.user = this.user.Clone();
		
		return post;
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

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int productId) {
		product_id = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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

	public ProductBean getProduct() {
		return product;
	}

	public void setProduct(ProductBean product) {
		this.product = product;
	}

	public CommentBean getLastComment() {
		return lastComment;
	}

	public void setLastComment(CommentBean lastComment) {
		this.lastComment = lastComment;
	}

	public List<TagBean> getTags() {
		return tags;
	}

	public void setTags(List<TagBean> tags) {
		this.tags = tags;
	}

	public List<ImageBean> getImages() {
		return images;
	}

	public void setImages(List<ImageBean> images) {
		this.images = images;
	}
}
