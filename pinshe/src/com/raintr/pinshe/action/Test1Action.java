package com.raintr.pinshe.action;

	import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

	public class Test1Action {
	    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
	    private static String appId = "wKinuFmVEwAlkJyp7Fx1b2";
	    private static String appKey = "Om41aQHFCKAo6BTvhroyI5";
	    private static String masterSecret = "dxHQnZXwF99SY3FNAqDda8";

	    static String CID = "cbd07c9d3b792dc6715f75b5a3afcd32";
//	    static String CID ="a7911cd0ac46428f8609c80911c45a52";
	  //别名推送方式
	   // static String Alias = "";
	    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	    public static void main(String[] args) throws Exception {
	        IGtPush push = new IGtPush(host, appKey, masterSecret);
	        NotificationTemplate template = notificationTemplateDemo();

	        SingleMessage message = new SingleMessage();
	        message.setOffline(true);
	        // 离线有效时间，单位为毫秒，可选
	        message.setOfflineExpireTime(24 * 3600 * 1000);
	        message.setData(template);
	        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
	        message.setPushNetWorkType(0); 
	        Target target = new Target();
	        target.setAppId(appId);
	        target.setClientId(CID);
	        //target.setAlias(Alias);
	        IPushResult ret = null;
	        try {
	            ret = push.pushMessageToSingle(message, target);
	        } catch (RequestException e) {
	            e.printStackTrace();
	            ret = push.pushMessageToSingle(message, target, e.getRequestId());
	        }
	        if (ret != null) {
	            System.out.println(ret.getResponse().toString());
	        } else {
	            System.out.println("服务器响应异常");
	        }
	        
	        
	        
	    }
	    public static LinkTemplate linkTemplateDemo() {
	        LinkTemplate template = new LinkTemplate();
	        // 设置APPID与APPKEY
	        template.setAppId(appId);
	        template.setAppkey(appKey);
	        // 设置通知栏标题与内容
	        template.setTitle("请输入通知栏标题");
	        template.setText("请输入通知栏内容");
	        // 配置通知栏图标
	        template.setLogo("icon.png");
	        // 配置通知栏网络图标，填写图标URL地址
	        template.setLogoUrl("");
	        // 设置通知是否响铃，震动，或者可清除
	        template.setIsRing(true);
	        template.setIsVibrate(true);
	        template.setIsClearable(true);
	        // 设置打开的网址地址
	        template.setUrl("http://www.baidu.com");
	        return template;
	    }
	    
	    
	    public static NotificationTemplate notificationTemplateDemo() {
		    NotificationTemplate template = new NotificationTemplate();
		    // 设置APPID与APPKEY
		    template.setAppId(appId);
		    template.setAppkey(appKey);
		    // 设置通知栏标题与内容
		    template.setTitle("请输入通知栏标题ddd");
		    template.setText("请输入通知栏内容kkkk");
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
		    template.setTransmissionContent("请输入您要透传的内容");
		    // 设置定时展示时间
		    // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		    return template;
		}
	    
		private static TransmissionTemplate getTemplate() {
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
			payload.setAlertMsg(getDictionaryAlertMsg("大幅", "大幅答复"));
			template.setAPNInfo(payload);
			return template;
		}

		private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String body) {
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
}