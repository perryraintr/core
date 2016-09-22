package com.raintr.pinshe.service;

import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import com.kdt.api.KdtApiClient;

public class YouzanService {
	private String appkey;
	private String secret;
	
	public YouzanService(){}

	public String ByProduct() throws Exception{
		String method = "kdt.items.onsale.get";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("created", "desc");
		params.put("page_size",  "99999999");
		params.put("fields", "title,item_imgs,item_tags,price,num_iid,outer_id,desc");
		
		KdtApiClient kdtApiClient = new KdtApiClient(appkey, secret);
		HttpResponse response = kdtApiClient.get(method, params);
			String json = EntityUtils.toString(response.getEntity());
			
		if(json != null && json.length() > 0)
			return json;
		
		return null;
	}
	
	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
