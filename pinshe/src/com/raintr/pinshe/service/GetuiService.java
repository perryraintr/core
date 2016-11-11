package com.raintr.pinshe.service;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class GetuiService {
    private String appId;
    private String appKey;
    private String masterSecret;
	
	public GetuiService(){}
	
	// cid=a7911cd0ac46428f8609c80911c45a52
	public String SendIOS(String cid, String title, String body) {
		IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", appKey, masterSecret);
		TransmissionTemplate template = GetTemplateIOS(title, body);
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
	
	public String SendAndroid(String cid, String title, String body) {
		IGtPush push = new IGtPush("http://sdk.open.api.igexin.com/apiex.htm", appKey, masterSecret);
		NotificationTemplate template = GetTemplateAndroid(title, body);
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
	private TransmissionTemplate GetTemplateIOS(String title, String body) {
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
		payload.setAlertMsg(GetDictionaryAlertMsg(title, body));
		template.setAPNInfo(payload);
		return template;
	}

	private APNPayload.DictionaryAlertMsg GetDictionaryAlertMsg(String title, String body) {
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
	
	@SuppressWarnings("deprecation")
	private NotificationTemplate GetTemplateAndroid(String title, String body) {
	    NotificationTemplate template = new NotificationTemplate();
	    // 设置APPID与APPKEY
	    template.setAppId(appId);
	    template.setAppkey(appKey);
	    // 设置通知栏标题与内容
	    template.setTitle(title);
	    template.setText(body);
	    // 配置通知栏图标
	    template.setLogo("icon.png");
	    // 配置通知栏网络图标
	    template.setLogoUrl("");
	    // 设置通知是否响铃，震动，或者可清除
	    template.setIsRing(true);
	    template.setIsVibrate(true);
	    template.setIsClearable(true);
	    // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
	    template.setTransmissionType(1);
	    template.setTransmissionContent("通知");
	    // 设置定时展示时间
	    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
	    return template;
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




