package com.raintr.pinshe.service;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class GetuiService {
    private String appId;
    private String appKey;
    private String masterSecret;
	
	public GetuiService(){}
	
	// cid=a7911cd0ac46428f8609c80911c45a52
	public String Set(String cid, String title, String body) {
		IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", appKey, masterSecret);
		TransmissionTemplate template = getTemplate(title, body);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(cid);
		// target.setAlias(Alias);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}

		if (ret != null)
			return ret.getResponse().toString();

		return "服务器响应异常";
	}

	@SuppressWarnings("deprecation")
	private TransmissionTemplate getTemplate(String title, String body) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionContent("");
		template.setTransmissionType(2);
		APNPayload payload = new APNPayload();
		payload.setBadge(1);
		payload.setContentAvailable(1);
		payload.setSound("default");
		payload.setCategory("");
		// 简单模式APNPayload.SimpleMsg
		// payload.setAlertMsg(new APNPayload.SimpleAlertMsg("测试"));
		// 字典模式使用下者
		payload.setAlertMsg(getDictionaryAlertMsg(title, body));
		template.setAPNInfo(payload);
		return template;
	}

	private APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String body) {
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		alertMsg.setBody(body);
		alertMsg.setActionLocKey("");
		alertMsg.setLocKey("");
		alertMsg.addLocArg("");
		alertMsg.setLaunchImage("");
		// IOS8.2以上版本支持
		alertMsg.setTitle(title);
		alertMsg.setTitleLocKey("");
		alertMsg.addTitleLocArg("");
		return alertMsg;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getMasterSecret() {
		return masterSecret;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}
}




