package com.raintr.pinshe.bean;

import java.util.Date;
import com.raintr.pinshe.utils.DateGlobal;

public class AdBean {
	private int id;
	private int type;
	private String name;
	private String image;
	private String url;
	private Date create_time;
	private Date modify_time;
	
	public AdBean(){
		name = "";
		image = "";
		url = "";
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToName(String table){return String.format("\"%sname\":\"%s\"", 				table, name);}
	public String ToImage(String table){return String.format("\"%simage\":\"%s\"", 				table, image);}
	public String ToUrl(String table){return String.format("\"%surl\":\"%s\"", 					table, url);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, DateGlobal.GetDistanceTime(modify_time));}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
