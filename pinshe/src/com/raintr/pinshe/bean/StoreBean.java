package com.raintr.pinshe.bean;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class StoreBean implements Comparator<StoreBean> {
	private int id;
	private int merchant_id;
	private double current;
	private double longitude;
	private double latitude;
	private String name;
	private int star;
	private String address;
	private String phone;
	private String date;
	private String slogan;
	private String owner;
	private String avatar;
	private String recommend;
	private String feature1;
	private String feature2;
	private String feature3;
	private String image;
	private String video;
	private String activity;
	private int comment;
	private String payment;
	private int invaild; // 0 use 1 not use
	private String description;
	private Date create_time;
	private Date modify_time;
	
	private double distance;
	
	private MerchantBean merchant;
	private List<StoreImageBean> images;
	
	private int by;
	
	public StoreBean(){
		name = "";
		address = "";
		phone = "";
		date = "";
		slogan = "";
		owner = "";
		avatar = "";
		recommend = "";
		feature1 = "";
		feature2 = "";
		feature3 = "";
		image = "";
		video = "";
		activity = "";
		payment = "";
		description = "";
		
		create_time = new Date();
		modify_time = new Date();
	}

	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}
	public String ToCurrent(String table){return String.format("\"%scurrent\":%f", 				table, current);}
	public String ToLongitude(String table){return String.format("\"%slongitude\":%f", 			table, longitude);}
	public String ToLatitude(String table){return String.format("\"%slatitude\":%f", 			table, latitude);}
	public String ToName(String table){return String.format("\"%sname\":\"%s\"", 				table, name);}
	public String ToStar(String table){return String.format("\"%sstar\":%d", 					table, star);}
	public String ToAddress(String table){return String.format("\"%saddress\":\"%s\"", 			table, address);}
	public String ToPhone(String table){return String.format("\"%sphone\":\"%s\"", 				table, phone);}
	public String ToDate(String table){return String.format("\"%sdate\":\"%s\"", 				table, date);}
	public String ToSlogan(String table){return String.format("\"%sslogan\":\"%s\"", 			table, slogan);}
	public String ToOwner(String table){return String.format("\"%sowner\":\"%s\"", 				table, owner);}
	public String ToAvatar(String table){return String.format("\"%savatar\":\"%s\"", 			table, avatar);}
	public String ToRecommend(String table){return String.format("\"%srecommend\":\"%s\"", 		table, recommend);}
	public String ToFeature1(String table){return String.format("\"%sfeature1\":\"%s\"", 		table, feature1);}
	public String ToFeature2(String table){return String.format("\"%sfeature2\":\"%s\"", 		table, feature2);}
	public String ToFeature3(String table){return String.format("\"%sfeature3\":\"%s\"", 		table, feature3);}
	public String ToImage(String table){return String.format("\"%simage\":\"%s\"", 				table, image);}
	public String ToVideo(String table){return String.format("\"%svideo\":\"%s\"", 				table, video);}
	public String ToActivity(String table){return String.format("\"%sactivity\":\"%s\"", 		table, activity);}
	public String ToComment(String table){return String.format("\"%scomment\":%d", 				table, comment);}
	public String ToPayment(String table){return String.format("\"%spayment\":\"%s\"", 			table, payment);}
	public String ToInvaild(String table){return String.format("\"%sinvaild\":%d", 				table, invaild);}
	public String ToDescription(String table){return String.format("\"%sdescription\":\"%s\"", 	table, description);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 	table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 	table, modify_time);}

	public String ToDistance(String table){return String.format("\"%sdistance\":%f", 			table, distance);}

	public int compare(StoreBean store1, StoreBean store2) {
		if(by == 1){
			return (int)(store1.getDistance() - store2.getDistance());
		}

		
		int id = store1.getInvaild() - store2.getInvaild();
        if(id == 0)
        	return (int)(store1.getDistance() - store2.getDistance());
        
        return id;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(int merchant_id) {
		this.merchant_id = merchant_id;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getRecommend() {
		return recommend;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getFeature1() {
		return feature1;
	}

	public void setFeature1(String feature1) {
		this.feature1 = feature1;
	}

	public String getFeature2() {
		return feature2;
	}

	public void setFeature2(String feature2) {
		this.feature2 = feature2;
	}

	public String getFeature3() {
		return feature3;
	}

	public void setFeature3(String feature3) {
		this.feature3 = feature3;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public int getInvaild() {
		return invaild;
	}

	public void setInvaild(int invaild) {
		this.invaild = invaild;
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

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public MerchantBean getMerchant() {
		return merchant;
	}

	public void setMerchant(MerchantBean merchant) {
		this.merchant = merchant;
	}

	public List<StoreImageBean> getImages() {
		return images;
	}

	public void setImages(List<StoreImageBean> images) {
		this.images = images;
	}

	public int getBy() {
		return by;
	}

	public void setBy(int by) {
		this.by = by;
	}
}
