package com.raintr.pinshe.bean;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class GroupBean {
	private int id;
	private int type;
	private String easemob_id;
	private String name;
	private String description;
	private int user_id;
	private int member_count;
	private int post_count;
	private String image;
	private Date start_time;
	private Date finish_time;
	private Date create_time;
	private Date modify_time;
	
	public GroupBean(){
		easemob_id = "";
		name = "";
		description = "";
		image = "";
		create_time = new Date();
		modify_time = new Date();
	}
	
	public GroupBean(JSONObject json){
		easemob_id = json.getString("groupid");;
		name = "";
		description = "";
		image = "";
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToType(String table){return String.format("\"%stype\":%d", 					table, type);}
	public String ToName(String table){return String.format("\"%sname\":\"%s\"", 				table, name);}
	public String ToDescription(String table){return String.format("\"%sdescription\":\"%s\"",	table, description);}
	public String ToMember_count(String table){return String.format("\"%smember_count\":%d", 	table, member_count);}
	public String ToPost_count(String table){return String.format("\"%spost_count\":%d", 		table, post_count);}
	public String ToImage(String table){return String.format("\"%simage\":\"%s\"",				table, image);}
	public String ToStart_time(String table){return String.format("\"%sstart_time\":\"%s\"", 	table, start_time);}
	public String ToFinish_time(String table){return String.format("\"%sfinish_time\":\"%s\"", 	table, finish_time);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 	table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, modify_time);}

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

	public String getEasemob_id() {
		return easemob_id;
	}

	public void setEasemob_id(String easemob_id) {
		this.easemob_id = easemob_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getMember_count() {
		return member_count;
	}

	public void setMember_count(int member_count) {
		this.member_count = member_count;
	}

	public int getPost_count() {
		return post_count;
	}

	public void setPost_count(int post_count) {
		this.post_count = post_count;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
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
