package com.raintr.pinshe.bean;

import java.util.Date;

public class StoreFeature1Bean {
	private int id;
	private int store_id;
	private int store_feature1_image_id;
	private Date create_time;
	private Date modify_time;
	
	private StoreFeature1ImageBean storeFeature1Image;
	
	public StoreFeature1Bean(){
		create_time = new Date();
		modify_time = new Date();
	}

	public String ToId(String table){return String.format("\"%sguid\":%d", 						table, id);}

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

	public int getStore_feature1_image_id() {
		return store_feature1_image_id;
	}

	public void setStore_feature1_image_id(int store_feature1_image_id) {
		this.store_feature1_image_id = store_feature1_image_id;
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

	public StoreFeature1ImageBean getStoreFeature1Image() {
		return storeFeature1Image;
	}

	public void setStoreFeature1Image(StoreFeature1ImageBean storeFeature1Image) {
		this.storeFeature1Image = storeFeature1Image;
	}
}
