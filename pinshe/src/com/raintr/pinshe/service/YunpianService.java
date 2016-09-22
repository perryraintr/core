package com.raintr.pinshe.service;

import java.util.HashMap;
import java.util.Map;

import com.raintr.pinshe.utils.Cache;
import com.raintr.pinshe.utils.NetGlobal;

// 同1个手机发相同内容，30秒内最多发送1次，5分钟内最多发送3次。
// 验证码1小时内同一个手机号发送不超过3次，
// 同一个手机号同一个验证模板每30秒只能发一条
public class YunpianService {
	private String apikey;
	
	public YunpianService(){}

	public String Send(String phone) throws Exception{
		int code = (int)((Math.random() * 9 + 1) * 100000);
		Cache.phones.put(phone, String.valueOf(code));
		
		String url = "https://sms.yunpian.com/v2/sms/single_send.json";
		
		Map<String, String> bodys = new HashMap<String, String>();
		bodys.put("apikey", apikey);
		bodys.put("mobile", phone);
		bodys.put("text", "【品社咖啡】您的验证码是" + code);
	
		return NetGlobal.HttpPost(url, null, bodys, "utf-8");
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
}
