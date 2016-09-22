package com.raintr.pinshe.bean;

import java.util.Date;
import java.util.List;

public class CommodityBean {
	private int id;
	private int num_iid; // youzan
	private int outer_id; // youzan
	private String name;
	private int t0; // 1:品社X系列  2:至臻精选   3:名庄限量 
	private int t1; // 1:bestseller 2:import 3:new 4:importnew 5:bestnew
	private int t2; // 1:老饕必品  2:顶级庄园限量   3:限量烘焙   4:季节限定   5:全球尖货   6:青春系列   7:纯手工研磨 
	private int t3; // 1:star1 2:star2 3:star3 7:star7
	private int t4; // 1:shallow  2:shallowmiddle 3:middle 4:deepmiddle 5:deep
	private int t5; // 1:80g 2:100g 3:150g 4:170g 5:200g 6:227g 7:115gx2 8:250g  9:450g  10:10包   11:20包   12:300mlx6
	private double price;
	private int count;
	private String description;
	private String detail;
	private String other_name;
	private String other_description;
	private double cost;
	private String url;
	private String memo;
	private int status;// 0 use 1 not use
	private int rank;
	private Date create_time;
	private Date modify_time;

	private List<CommodityImageBean> images;
	
	public CommodityBean(){
		name = "";
		description = "";
		url = "";
		memo = "";
		detail = "";
		other_name = "";
		other_description = "";
		url = "";
		memo = "";
		create_time = new Date();
		modify_time = new Date();
	}
	
	public String ToId(String table){return String.format("\"%sguid\":%d", 									table, id);}
	public String ToNum_iid(String table){return String.format("\"%snum_iid\":%d", 							table, num_iid);}
	public String ToOuter_id(String table){return String.format("\"%souter_id\":%d", 						table, outer_id);}
	public String ToName(String table){return String.format("\"%sname\":\"%s\"", 							table, name);}
	public String ToT0(String table){return String.format("\"%st0\":%d", 									table, t0);}
	public String ToT1(String table){return String.format("\"%st1\":%d", 									table, t1);}
	public String ToT2(String table){return String.format("\"%st2\":%d", 									table, t2);}
	public String ToT3(String table){return String.format("\"%st3\":%d", 									table, t3);}
	public String ToT4(String table){return String.format("\"%st4\":%d", 									table, t4);}
	public String ToT5(String table){return String.format("\"%st5\":%d", 									table, t5);}
	public String ToPrice(String table){return String.format("\"%sprice\":%.2f", 							table, price);}
	public String ToCount(String table){return String.format("\"%scount\":%d", 								table, count);}
	public String ToDescription(String table){return String.format("\"%sdescription\":\"%s\"", 				table, description);}
	public String ToDetail(String table){return String.format("\"%sdetail\":\"%s\"", 						table, detail);}
	public String ToOther_name(String table){return String.format("\"%sother_name\":\"%s\"", 				table, other_name);}
	public String ToOther_description(String table){return String.format("\"%sother_description\":\"%s\"", 	table, other_description);}
	public String ToCost(String table){return String.format("\"%scost\":%.2f", 								table, cost);}
	public String ToUrl(String table){return String.format("\"%surl\":\"%s\"", 								table, url);}
	public String ToMemo(String table){return String.format("\"%smemo\":\"%s\"", 							table, memo);}
	public String ToStatus(String table){return String.format("\"%sstatus\":%d", 							table, status);}
	public String ToRank(String table){return String.format("\"%srank\":%d", 								table, rank);}
	public String ToCreate_time(String table){return String.format("\"%screate_time\":\"%s\"", 				table, create_time);}
	public String ToModify_time(String table){return String.format("\"%smodify_time\":\"%s\"", 				table, modify_time);}

	public String ToT0_str(String table){return String.format("\"%st0_str\":\"%s\"", 						table, T0(t0));}
	public String ToT1_str(String table){return String.format("\"%st1_str\":\"%s\"", 						table, T1(t1));}
	public String ToT2_str(String table){return String.format("\"%st2_str\":\"%s\"", 						table, T2(t2));}
	public String ToT3_str(String table){return String.format("\"%st3_str\":\"%s\"", 						table, T3(t3));}
	public String ToT4_str(String table){return String.format("\"%st4_str\":\"%s\"", 						table, T4(t4));}
	public String ToT5_str(String table){return String.format("\"%st5_str\":\"%s\"", 						table, T5(t5));}
	
	// 1:品社X系列  2:至臻精选   3:名庄限量 
	public int T0(String value){
		if("品社X系列".equals(value))
			return 1;
		if("至臻精选".equals(value))
			return 2;
		if("名庄限量".equals(value))
			return 3;
		return 0;
	}
	
	// 1:品社X系列  2:至臻精选   3:名庄限量 
	public String T0(int value){
		if(value == 1)
			return "品社X系列";
		if(value == 2)
			return "至臻精选";
		if(value == 3)
			return "名庄限量";
		return "";
	}
	 
	// 1:bestseller 2:import 3:new 4:importnew 5:bestnew
	public int T1(String value){
		if("bestseller".equals(value))
			return 1;
		if("import".equals(value))
			return 2;
		if("new".equals(value))
			return 3;
		if("importnew".equals(value))
			return 4;
		if("bestnew".equals(value))
			return 5;
		
		return 0;
	}
	
	// 1:bestseller 2:import 3:new 4:importnew 5:bestnew 
	public String T1(int value){
		if(value == 1)
			return "bestseller";
		if(value == 2)
			return "import";
		if(value == 3)
			return "new";
		if(value == 4)
			return "importnew";
		if(value == 5)
			return "bestnew";
		return "";
	}
	
	// 1:老饕必品  2:顶级庄园限量   3:限量烘焙   4:季节限定   5:全球尖货   6:青春系列   7:纯手工研磨 
	public int T2(String value){
		if("老饕必品".equals(value))
			return 1;
		if("顶级庄园限量".equals(value))
			return 2;
		if("限量烘焙".equals(value))
			return 3;
		if("季节限定".equals(value))
			return 4;
		if("全球尖货".equals(value))
			return 5;
		if("青春系列".equals(value))
			return 6;
		if("纯手工研磨".equals(value))
			return 7;
		return 0;
	}
	
	// 1:老饕必品  2:顶级庄园限量   3:限量烘焙   4:季节限定   5:全球尖货   6:青春系列   7:纯手工研磨 
	public String T2(int value){
		if(value == 1)
			return "老饕必品";
		if(value == 2)
			return "顶级庄园限量";
		if(value == 3)
			return "限量烘焙";
		if(value == 4)
			return "季节限定";
		if(value == 5)
			return "全球尖货";
		if(value == 6)
			return "青春系列";
		if(value == 7)
			return "纯手工研磨";
		return "";
	}
	
	// 1:star1 2:star2 3:star3 7:star7
	public int T3(String value){
		if("star1".equals(value))
			return 1;
		if("star2".equals(value))
			return 2;
		if("star3".equals(value))
			return 3;
		if("star7".equals(value))
			return 7;
		return 0;
	}
	// 1:star1 2:star2 3:star3 7:star7
	public String T3(int value){
		if(value == 1)
			return "star1";
		if(value == 2)
			return "star2";
		if(value == 3)
			return "star3";
		if(value == 7)
			return "star7";
		return "";
	}
	
	// 1:shallow  2:shallowmiddle 3:middle 4:deepmiddle 5:deep
	public int T4(String value){
		if("shallow".equals(value))
			return 1;
		if("shallowmiddle".equals(value))
			return 2;
		if("middle".equals(value))
			return 3;
		if("deepmiddle".equals(value))
			return 4;
		if("deep".equals(value))
			return 5;
		return 0;
	}
	
	// 1:shallow  2:shallowmiddle 3:middle 4:deepmiddle 5:deep
	public String T4(int value){
		if(value == 1)
			return "shallow";
		if(value == 2)
			return "shallowmiddle";
		if(value == 3)
			return "middle";
		if(value == 4)
			return "deepmiddle";
		if(value == 5)
			return "deep";
		return "";
	}
	
	// 1:80g 2:100g 3:150g 4:170g 5:200g 6:227g 7:115gx2 8:250g  9:450g  10:10包   11:20包    12:300mlx6
	public int T5(String value){
		if("80g".equals(value))
			return 1;
		if("100g".equals(value))
			return 2;
		if("150g".equals(value))
			return 3;
		if("170g".equals(value))
			return 4;
		if("200g".equals(value))
			return 5;
		if("227g".equals(value))
			return 6;
		if("115gx2".equals(value))
			return 7;
		if("250g".equals(value))
			return 8;
		if("450g".equals(value))
			return 9;
		if("10包".equals(value))
			return 10;
		if("20包".equals(value))
			return 11;
		if("300mlx6".equals(value))
			return 12;
		return 0;
	}
	
	// 1:80g 2:100g 3:150g 4:170g 5:200g 6:227g 7:115gx2 8:250g  9:450g  10:10包   11:20包    12:300mlx6
	public String T5(int value){
		if(value == 1)
			return "80g";
		if(value == 2)
			return "100g";
		if(value == 3)
			return "150g";
		if(value == 4)
			return "170g";
		if(value == 5)
			return "200g";
		if(value == 6)
			return "227g";
		if(value == 7)
			return "115gx2";
		if(value == 8)
			return "250g";
		if(value == 9)
			return "450g";
		if(value == 10)
			return "10包";
		if(value == 11)
			return "20包";
		if(value == 12)
			return "300mlx6瓶";
		return "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNum_iid() {
		return num_iid;
	}

	public void setNum_iid(int num_iid) {
		this.num_iid = num_iid;
	}

	public int getOuter_id() {
		return outer_id;
	}

	public void setOuter_id(int outer_id) {
		this.outer_id = outer_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getT0() {
		return t0;
	}

	public void setT0(int t0) {
		this.t0 = t0;
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

	public int getT4() {
		return t4;
	}

	public void setT4(int t4) {
		this.t4 = t4;
	}

	public int getT5() {
		return t5;
	}

	public void setT5(int t5) {
		this.t5 = t5;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getOther_name() {
		return other_name;
	}

	public void setOther_name(String other_name) {
		this.other_name = other_name;
	}

	public String getOther_description() {
		return other_description;
	}

	public void setOther_description(String other_description) {
		this.other_description = other_description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
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

	public List<CommodityImageBean> getImages() {
		return images;
	}

	public void setImages(List<CommodityImageBean> images) {
		this.images = images;
	}
}
