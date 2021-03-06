package com.raintr.pinshe.service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.CommodityImageBean;
import com.raintr.pinshe.bean.RecommendBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreImageBean;
import com.raintr.pinshe.bean.StorePushBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.bean.WechatPayBean;
import com.raintr.pinshe.utils.Cache;
import com.raintr.pinshe.utils.Md5Global;
import com.raintr.pinshe.utils.NetGlobal;
import com.raintr.pinshe.utils.StringGlobal;

public class WechatService {
	private String appid;
	private String appsecret;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private RecommendService recommendService;
	
	public WechatService(){}
	
	// https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQF68ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL3N6c0lTNkRsOGs2TFpKMjNIQk1RAAIE48rXVwMEAAAAAA==
	public String GetQrcode(String code) throws Exception{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s", GetToken());

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		
		String json = String.format("{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"%s\"}}}", code);

		return NetGlobal.HttpPost(url, headers, json, "utf-8");
	}
	
	public String ReplyText(String toUser, String fromUser) throws Exception{
		String content = null;
		
		String json = GetAutoreply();
		if(!StringGlobal.IsNull(json)){
			JSONObject row = JSON.parseObject(json);
			if(row != null){
				JSONObject add_friend_autoreply_info = row.getJSONObject("add_friend_autoreply_info");
				if(add_friend_autoreply_info != null)
					content = add_friend_autoreply_info.getString("content");
			}
		}
		
		if(content == null)
			content = "";
		
		StringBuffer xml = new StringBuffer();
		xml.append("<xml>");
		xml.append(String.format("<ToUserName><![CDATA[%s]]></ToUserName>", toUser));
		xml.append(String.format("<FromUserName><![CDATA[%s]]></FromUserName>", fromUser));
		xml.append(String.format("<CreateTime>%d</CreateTime>", new Date().getTime()));
		xml.append(String.format("<MsgType><![CDATA[%s]]></MsgType>", "text"));
		xml.append(String.format("<Content><![CDATA[%s]]></Content>", content));
		xml.append("</xml>");
		return xml.toString();
	}
	
	public String ReplyRichText(String toUser, String fromUser, String name, String description, String image, String url) throws Exception{
		StringBuffer xml = new StringBuffer();
		xml.append("<xml>");
		xml.append(String.format("<ToUserName><![CDATA[%s]]></ToUserName>", toUser));
		xml.append(String.format("<FromUserName><![CDATA[%s]]></FromUserName>", fromUser));
		xml.append(String.format("<CreateTime>%d</CreateTime>", new Date().getTime()));
		xml.append(String.format("<MsgType><![CDATA[%s]]></MsgType>", "news"));
		xml.append(String.format("<ArticleCount>%s</ArticleCount>", "1"));
		xml.append(String.format("<Articles>"));
		xml.append(String.format("<item>"));
		xml.append(String.format("<Title><![CDATA[%s]]></Title>", name));
		xml.append(String.format("<Description><![CDATA[%s]]></Description>", description));
		xml.append(String.format("<PicUrl><![CDATA[%s]]></PicUrl>", image));
		xml.append(String.format("<Url><![CDATA[%s]]></Url>", url));
		xml.append(String.format("</item>"));
		xml.append(String.format("</Articles>"));
		xml.append("</xml>");
		return xml.toString();
	}	
	
	public String ReplyCommodity(String toUser, String fromUser, String commodityId) throws Exception{
		if(!StringGlobal.IsNull(commodityId)){
			CommodityBean commodity = commodityService.ById(Integer.parseInt(commodityId));
			if(commodity != null){
				CommodityImageBean image = null;
				List<CommodityImageBean> images = commodity.getImages();
				if(images != null && images.size() > 0)
					image = images.get(0);
				
				if(image == null)
					image = new CommodityImageBean();
				
				StringBuffer xml = new StringBuffer();
				xml.append("<xml>");
				xml.append(String.format("<ToUserName><![CDATA[%s]]></ToUserName>", toUser));
				xml.append(String.format("<FromUserName><![CDATA[%s]]></FromUserName>", fromUser));
				xml.append(String.format("<CreateTime>%d</CreateTime>", new Date().getTime()));
				xml.append(String.format("<MsgType><![CDATA[%s]]></MsgType>", "news"));
				xml.append(String.format("<ArticleCount>%s</ArticleCount>", "1"));
				xml.append(String.format("<Articles>"));
				xml.append(String.format("<item>"));
				xml.append(String.format("<Title><![CDATA[%s]]></Title>", commodity.getName()));
				xml.append(String.format("<Description><![CDATA[%s]]></Description>", commodity.getDescription()));
				xml.append(String.format("<PicUrl><![CDATA[%s]]></PicUrl>", image.getUrl()));
				xml.append(String.format("<Url><![CDATA[%s]]></Url>", "http://www.pinshe.org/html/v1/coffee/product_detail.html?id=" + commodity.getId()));
				xml.append(String.format("</item>"));
				
//				xml.append(String.format("<item>"));
//				xml.append(String.format("<Title><![CDATA[%s]]></Title>", "【寻咖行动】- 重金悬赏：寻找帝都的好咖啡！"));
//				xml.append(String.format("<Description><![CDATA[%s]]></Description>", "为了让这些美好的咖啡馆能够为更多人知道与喜爱，品社特别推出金秋特别企划： X Coffee | 寻咖行动。这是国内独立咖啡史上最激动人心的优惠活动，只为助你喝到好咖啡，享受好时光！"));
//				xml.append(String.format("<PicUrl><![CDATA[%s]]></PicUrl>", "http://mmbiz.qpic.cn/mmbiz_jpg/icjGTQas0nm1ff0964JzdCYzicBkYUwA93iaQQyGClbjWibceuUE4dicMu9jlKp2gdPzar3bYXRyWCsmSCCwsSgK5tg/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1"));
//				xml.append(String.format("<Url><![CDATA[%s]]></Url>", "http://mp.weixin.qq.com/s?__biz=MzIzNDQxNDU0OA==&mid=2247483675&idx=1&sn=3e58cf4eec91c5be5ccd3e47efbf4406&chksm=e8f789a0df8000b60e51ba4fdae3ea15c3982cc1b0428b9debe4889b0f029015c894f07e6bae&scene=2&srcid=0924ui2NFYPNlIu7qv7U6PyW&from=timeline&isappinstalled=0#wechat_redirect"));
//				xml.append(String.format("</item>"));
				
				xml.append(String.format("</Articles>"));
				xml.append("</xml>");
				return xml.toString();
			}
		}
		
		return null;
	}	
	
	public String ReplyStore(String toUser, String fromUser, String storeId) throws Exception{
		if(!StringGlobal.IsNull(storeId)){
			StoreBean store = storeService.ById(Integer.parseInt(storeId));
			if(store != null){
				StoreImageBean image = null;
				List<StoreImageBean> images = store.getImages();
				if(images != null && images.size() > 0)
					image = images.get(0);
				
				if(image == null)
					image = new StoreImageBean();
				
				StringBuffer xml = new StringBuffer();
				xml.append("<xml>");
				xml.append(String.format("<ToUserName><![CDATA[%s]]></ToUserName>", toUser));
				xml.append(String.format("<FromUserName><![CDATA[%s]]></FromUserName>", fromUser));
				xml.append(String.format("<CreateTime>%d</CreateTime>", new Date().getTime()));
				xml.append(String.format("<MsgType><![CDATA[%s]]></MsgType>", "news"));
				xml.append(String.format("<ArticleCount>%s</ArticleCount>", "2"));
				xml.append(String.format("<Articles>"));
				xml.append(String.format("<item>"));
				//xml.append(String.format("<Title><![CDATA[%s]]></Title>", store.getName()));
				xml.append(String.format("<Title><![CDATA[%s]]></Title>", store.getSlogan()));
				xml.append(String.format("<Description><![CDATA[%s]]></Description>", store.getSlogan()));
				xml.append(String.format("<PicUrl><![CDATA[%s]]></PicUrl>", image.getUrl()));
				xml.append(String.format("<Url><![CDATA[%s]]></Url>", "http://www.pinshe.org/html/v1/coffee/nearby_cafedetail.html?id=" + store.getId()));
				xml.append(String.format("</item>"));
				
				xml.append(String.format("<item>"));
				xml.append(String.format("<Title><![CDATA[%s]]></Title>", "😇别忘了评价本次体验"));
				xml.append(String.format("<Description><![CDATA[%s]]></Description>", ""));
				xml.append(String.format("<PicUrl><![CDATA[%s]]></PicUrl>", ""));
				xml.append(String.format("<Url><![CDATA[%s]]></Url>", "http://www.pinshe.org/html/v1/coffee/qrcode_cafecomment.html?id=" + store.getId()));
				xml.append(String.format("</item>"));
				
				xml.append(String.format("</Articles>"));
				xml.append("</xml>");
				return xml.toString();
			}
		}
		
		return null;
	}	
	
	public String GetAutoreply() throws Exception{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=%s", GetToken());
		return NetGlobal.HttpGet(url, null, "utf-8");
	}
	
	public String GetMenu() throws Exception{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=%s", GetToken());
		return NetGlobal.HttpGet(url, null, "utf-8");
	}
	
	public String SetMenu() throws Exception{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=%s", GetToken());
		String json = NetGlobal.HttpGet(url, null, "utf-8");
		
		if(json != null && json.length() > 0){
			JSONObject row = JSON.parseObject(json);	
			url = String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s", GetToken());
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			
			json = row.getJSONObject("selfmenu_info").toJSONString();
			json = json.replaceAll("\\{\"list\"\\:\\[", "[");
			json = json.replaceAll("\\}\\]\\}\\}","}]}");

			return NetGlobal.HttpPost(url, headers, json, "utf-8");
		}
		
		return null;
	}
	
	public UserBean By(String code) throws Exception{
		String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appid, appsecret, code);
		String json = NetGlobal.HttpGet(url, null);
		
		if(json != null && json.length() > 0){
			JSONObject row = JSON.parseObject(json);
			url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", row.getString("access_token"), row.getString("openid"));
			json = NetGlobal.HttpGet(url, null);
			
			row = JSON.parseObject(json);
			
			if(!StringGlobal.IsNull(row.getString("openid"))){
				UserBean user = new UserBean();
				user.setName(new String(row.getString("nickname").getBytes("ISO-8859-1"), "utf-8"));				
				user.setWechat_guid(row.getString("openid"));
				user.setAvatar(row.getString("headimgurl"));
				return user;
			}
		}
		
		return null;
	}
	
	public String Send(String wechat_id, String message) throws Exception{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s", GetToken());
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String body = String.format("{\"touser\":\"%s\",\"msgtype\":\"text\", \"text\":{\"content\":\"%s\"}}", wechat_id, message);
		return NetGlobal.HttpPost(url, headers, body, null);
	}
	
	public String SendTemplate(String wechat_id, String express, String title, String deliver, String order, String content) throws Exception{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", GetToken());
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String body = String.format("{\"touser\":\"%s\",\"template_id\":\"CYUGEFz4V5Wr_dbp5vmWQ3jnS0zCla6VfzHjd3wqoOY\",\"url\":\"%s\",\"data\":{\"first\": {\"value\":\"%s\",\"color\":\"#173177\"},\"delivername\":{\"value\":\"%s\",\"color\":\"#173177\"},\"ordername\": {\"value\":\"%s\",\"color\":\"#173177\"},\"remark\": {\"value\":\"%s\",\"color\":\"#173177\"}}}", wechat_id, express, title, deliver, order, content);
		return NetGlobal.HttpPost(url, headers, body, null);
	}
	
	public String SendTemplatePaid(String wechat_id, String redirect, String title, String amount, String order, String content) throws Exception{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", GetToken());
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String body = String.format("{\"touser\":\"%s\",\"template_id\":\"Y0z82NkCh5jo2HfOQlDfGfVyHk2vAxBIth8HvchCNdo\",\"url\":\"%s\",\"data\":{\"first\": {\"value\":\"%s\",\"color\":\"#000000\"},\"keyword1\":{\"value\":\"%s\",\"color\":\"#000000\"},\"keyword2\": {\"value\":\"%s\",\"color\":\"#000000\"},\"remark\": {\"value\":\"%s\",\"color\":\"#000000\"}}}", wechat_id, redirect, title, amount, order, content);
		return NetGlobal.HttpPost(url, headers, body, null);
	}
	
	public String SendTemplateWithdraw(String wechat_id, String redirect, String title, String name, String time, String amount, String way) throws Exception{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s", GetToken());
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String body = String.format("{\"touser\":\"%s\",\"template_id\":\"rT__CRb85PSeiGJZve8O2__3cmjDVzvyrl7PD-OIKWs\",\"url\":\"%s\",\"data\":{\"first\": {\"value\":\"%s\",\"color\":\"#000000\"},\"keyword1\":{\"value\":\"%s\",\"color\":\"#000000\"},\"keyword2\": {\"value\":\"%s\",\"color\":\"#000000\"},\"keyword3\": {\"value\":\"%s\",\"color\":\"#000000\"},\"keyword4\": {\"value\":\"%s\",\"color\":\"#000000\"}}}", wechat_id, redirect, title, name, time, amount, way);
		return NetGlobal.HttpPost(url, headers, body, null);
	}
	
	
	public String SendStore(String wechatId, String storeId, String orderId) throws Exception{
		if(!StringGlobal.IsNull(storeId)){
			StoreBean store = storeService.ById(Integer.parseInt(storeId));
			if(store != null){
				StoreImageBean image = null;
				List<StoreImageBean> images = store.getImages();
				if(images != null && images.size() > 0)
					image = images.get(0);
				
				if(image == null)
					image = new StoreImageBean();
				
				RecommendBean recommend = null;
				CommodityBean commodity = null;
				CommodityImageBean commodityImage = null;
				List<RecommendBean> recommends = recommendService.ByStoreId(store.getId(), 0);
				if(recommends != null && recommends.size() > 0){
					recommend = recommends.get(0);
					if(recommend != null){
						commodity = recommend.getCommodity();
						if(commodity != null){
							List<CommodityImageBean> commodityImages = commodity.getImages();
							if(commodityImages != null && commodityImages.size() > 0)
								commodityImage = commodityImages.get(0);
						}
					}
				}
				
				if(recommend == null)
					recommend = new RecommendBean();
				if(commodity == null)
					commodity = new CommodityBean();
				if(commodityImage == null)
					commodityImage = new CommodityImageBean();
				
				StorePushBean storePush;
				List<StorePushBean> storePushs = store.getStorePushs();
				
				StringBuffer json = new StringBuffer();

				if(store.getId() > 0)
					json.append(String.format("{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"},", store.getSlogan(), "", "http://www.pinshe.org/html/v1/coffee/nearby_cafedetail.html?id=" + store.getId(), image.getUrl()));
				
				if(recommend.getId() > 0)
					json.append(String.format("{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"},", "店长暖心推荐：" + recommend.getMessage(), "", "http://www.pinshe.org/html/v1/coffee/product_detail.html?id=" + recommend.getCommodity_id(), commodityImage.getUrl()));
				
				if(storePushs != null && storePushs.size() > 0){
					for(int i = 0; i < storePushs.size(); i++){
						storePush = storePushs.get(i);
						json.append(String.format("{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"},", storePush.getName(), "", storePush.getUrl(), storePush.getImage()));
					}
				}
				
				if(!StringGlobal.IsNull(store.getWifi()))
					json.append(String.format("{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"},", "查看" + store.getName() + "的WIFI密码", "", "http://www.pinshe.org/html/v1/coffee/qrcode_wifi.html?id=" + store.getId(), "http://www.pinshe.org/v1/image/2016/10/19/wifi.jpg"));
				
				json.append(String.format("{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"},", "别忘了评价本次体验, 获得品社心意馈赠", "", "http://www.pinshe.org/html/v1/coffee/qrcode_cafecomment.html?id=" + store.getId() + "&oid=" + orderId, "http://www.pinshe.org/v1/image/2016/10/19/logo.jpg"));

				if(json.length() > 0){
					json.setLength(json.length() - 1);
					String url = String.format("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s", GetToken());
					String body = String.format("{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\": [%s]}}", wechatId, json.toString());
					Map<String, String> headers = new HashMap<String, String>();  
					headers.put("Content-Type", "application/json");
					return NetGlobal.HttpPost(url, headers, body, "utf-8");
				}
				
//				if(recommend.getId() > 0){
//					String url = String.format("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s", GetToken());
//					Map<String, String> headers = new HashMap<String, String>();  
//					headers.put("Content-Type", "application/json");
//					String body = String.format("{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\": [{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"},{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"},{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"}]}}", 
//												wechatId,
//												store.getSlogan(), 
//												"", 
//												"http://www.pinshe.org/html/v1/coffee/nearby_cafedetail.html?id=" + store.getId(),
//												image.getUrl(),
//												"😇别忘了评价本次体验, 获得品社心意馈赠", 
//												"", 
//												"http://www.pinshe.org/html/v1/coffee/qrcode_cafecomment.html?id=" + store.getId() + "&oid=" + orderId, 
//												"",
//												"店长暖心推荐：" + recommend.getMessage(),
//												"",
//												"http://www.pinshe.org/html/v1/coffee/product_detail.html?id=" + recommend.getCommodity_id(),
//												commodityImage.getUrl());
//					return NetGlobal.HttpPost(url, headers, body, "utf-8");
//				}else{
//					String url = String.format("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s", GetToken());
//					Map<String, String> headers = new HashMap<String, String>();  
//					headers.put("Content-Type", "application/json");
//					String body = String.format("{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\": [{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"},{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"}]}}", 
//												wechatId,
//												store.getSlogan(), 
//												"", 
//												"http://www.pinshe.org/html/v1/coffee/nearby_cafedetail.html?id=" + store.getId(),
//												image.getUrl(),
//												"😇别忘了评价本次体验, 获得品社心意馈赠", 
//												"", 
//												"http://www.pinshe.org/html/v1/coffee/qrcode_cafecomment.html?id=" + store.getId() + "&oid=" + orderId, 
//												"");
//					return NetGlobal.HttpPost(url, headers, body, "utf-8");
//				}
				
			}
		}
		
		return null;
	}
	
	
	public String SendRichText(String wechat_id, String title1, String description1, String url1, String picurl1, String title2, String description2, String url2, String picurl2) throws Exception{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s", GetToken());
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		String body = String.format("{\"touser\":\"OPENID\",\"msgtype\":\"news\",\"news\":{\"articles\": [{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"},{\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\",\"picurl\":\"%s\"}]}}", wechat_id, title1, description1, url1, picurl1, title2, description2, url2, picurl2);
		return NetGlobal.HttpPost(url, headers, body, null);
	}
	
	
	
	
	
	public String GetJsapiTicket() throws Exception{
		String url = String.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi", GetToken());
		String json = NetGlobal.HttpGet(url, null);
		if(json != null && json.length() > 0){
			return JSON.parseObject(json).getString("ticket");
		}
		
		return null;
	}
	

	public String GetPrepay(String wechatId, String orderNo, String amount) throws Exception{
		WechatPayBean pay = new WechatPayBean();
		pay.setAppid(appid);
		pay.setMch_id("1366650902");
		pay.setNonce_str(String.valueOf(new java.util.Random().nextInt(100000000)));
		pay.setBody("品社");
		pay.setOut_trade_no(orderNo);
		pay.setTotal_fee(amount);
		pay.setSpbill_create_ip("127.0.0.1");
		pay.setNotify_url("http://interface.pinshe.org/v1/wechat_notify.a");
		pay.setTrade_type("JSAPI");
		pay.setOpenid(wechatId);
		pay.BuildSign();
		String xml = NetGlobal.HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder", null, pay.ToXml());
		
		Element element;
		if(xml != null){
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(new ByteArrayInputStream(xml.getBytes("utf-8")));
			Element root = doc.getRootElement();
			List<Element> elements = root.getChildren();
			if (elements != null && elements.size() != 0) {
				for(int i = 0; i < elements.size(); i++){
					element = elements.get(i);
					if("prepay_id".equals(element.getName()))
						return GetPay(element.getText());
				}
			}
		}

		return null;
	}
	
	private String GetPay(String prepayId) throws Exception{
		String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
		String nonceStr = String.valueOf(new java.util.Random().nextInt(100000000));

		StringBuffer sb = new StringBuffer();
		sb.append(String.format("appId=%s&", 				appid));
		sb.append(String.format("nonceStr=%s&", 			nonceStr));
		sb.append(String.format("package=prepay_id=%s&", 	prepayId));
		sb.append("signType=MD5&");
		sb.append(String.format("timeStamp=%s&", 			timeStamp));
		sb.append("key=pinshewechat00010001000100010001");
		String sign = Md5Global.Encode(sb.toString()).toUpperCase();

		StringBuffer json = new StringBuffer();
		json.append(String.format("\"appId\":\"%s\",", 				appid));
		json.append(String.format("\"timeStamp\":\"%s\",", 			timeStamp));
		json.append(String.format("\"nonceStr\":\"%s\",", 			nonceStr));
		json.append(String.format("\"package\":\"prepay_id=%s\",", 	prepayId));
		json.append("\"signType\":\"MD5\",");
		json.append(String.format("\"paySign\":\"%s\"", 			sign));
		
		return json.toString();
	}
	
	
	private String GetToken() throws Exception{
		Date now = new Date();
		if(Cache.date == null || now.getTime() > Cache.date.getTime() + 3600000){
			synchronized (Cache.objectToken){
				if(Cache.date == null || now.getTime() > Cache.date.getTime() + 3600000){
					String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appid, appsecret);
					String json = NetGlobal.HttpGet(url, null);
					if(json != null && json.length() > 0){
						Cache.token = JSON.parseObject(json).getString("access_token");
						Cache.date = now;
					}
				}
	        }
		}
		
		return Cache.token;
	}
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
}
