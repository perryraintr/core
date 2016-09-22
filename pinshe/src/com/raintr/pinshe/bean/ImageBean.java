package com.raintr.pinshe.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageBean {
	private int id;
	private int post_id;
	private int product_id;
	private String url;
	private Date create_time;
	private Date modify_time;
	
	private SimpleDateFormat sdf;
	
	public ImageBean(){
		url = "";
		create_time = new Date();
		modify_time = new Date();
		sdf = new SimpleDateFormat("MM月dd日 HH:mm");
	}

	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToUrl(String table){return String.format("\"%surl\":%d", 						table, url);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"",	table, sdf.format(modify_time));}

	public ImageBean Clone(){
		ImageBean image = new ImageBean();
		
		image.id = this.id;
		image.post_id = this.post_id;
		image.product_id = this.product_id;
		image.url = this.url;
		image.create_time = this.create_time;
		image.modify_time = this.modify_time;
		
		return image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
