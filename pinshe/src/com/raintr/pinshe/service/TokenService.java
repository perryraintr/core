package com.raintr.pinshe.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.raintr.pinshe.bean.TokenBean;
import com.raintr.pinshe.utils.NetGlobal;

public class TokenService {
	private String org_name;
	private String app_name;
	private String client_id;
	private String client_secret;
	
	private int once;
	private static TokenBean token;
	
	private TokenService(){
		token = new TokenBean();
	}

	public TokenBean Instance() throws Exception{
//		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//		if(once != day){
//			synchronized (TokenService.class){
//				if(once != day){
//					token = By();
//					once = day;
//				}
//	        }
//		}
		
		return token;
	}
	
	private TokenBean By() throws Exception{
		String url = String.format("https://a1.easemob.com/%s/%s/token", org_name, app_name);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		
		String body = String.format("{\"grant_type\":\"client_credentials\",\"client_id\":\"%s\",\"client_secret\":\"%s\"}", client_id, client_secret);

		String json = NetGlobal.HttpPost(url, headers, body);
		
		if(json != null && json.length() > 0)
			return JSON.parseObject(json, TokenBean.class);
		
		return null;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}

	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
}
