package com.raintr.pinshe.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.raintr.pinshe.service.WechatService;
import com.raintr.pinshe.utils.FileGlobal;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Wechat_CallBackAction{
	@Autowired
	private WechatService wechatService;

    @RequestMapping(value = "/wechat_callback", method = {RequestMethod.POST})
    protected String ActionPost(HttpServletRequest request, HttpServletResponse response, ModelMap model){
    	try{
	    	Element element;
	    	String toUserName = null;
	    	String fromUserName = null;
	    	String event = null;
	    	String eventKey = null;
	    	
	    	String xml = IOUtils.toString(request.getInputStream(), "utf-8");
	    	FileGlobal.AddFile(xml, "", "/opt/log/callback");
	    	
// 0
//	    	<xml><ToUserName><![CDATA[gh_df90c2626736]]></ToUserName>
//	    	<FromUserName><![CDATA[o1D_JwGKMNWZmBYLxghYYw0GIlUg]]></FromUserName>
//	    	<CreateTime>1474948418</CreateTime>
//	    	<MsgType><![CDATA[event]]></MsgType>
//	    	<Event><![CDATA[subscribe]]></Event>
//	    	<EventKey><![CDATA[]]></EventKey>
//	    	</xml>
	    	
// 1
//	    	<xml><ToUserName><![CDATA[gh_df90c2626736]]></ToUserName>
//	    	<FromUserName><![CDATA[o1D_JwGKMNWZmBYLxghYYw0GIlUg]]></FromUserName>
//	    	<CreateTime>1473739229</CreateTime>
//	    	<MsgType><![CDATA[event]]></MsgType>
//	    	<Event><![CDATA[subscribe]]></Event>
//	    	<EventKey><![CDATA[last_trade_no_4007112001201609113708466169]]></EventKey>
//	    	</xml>

// 2	    	
//	    	<xml><ToUserName><![CDATA[gh_df91c2626736]]></ToUserName>
//	    	<FromUserName><![CDATA[o1D_JwGKMNWZmBYLxghYYw0GIlUg]]></FromUserName>
//	    	<CreateTime>1473759987</CreateTime>
//	    	<MsgType><![CDATA[event]]></MsgType>
//	    	<Event><![CDATA[SCAN]]></Event>
//	    	<EventKey><![CDATA[0_1]]></EventKey>
//	    	<Ticket><![CDATA[gQF68ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL3N6c0lTNkRsOGs2TFpKMjNIQk1RAAIE48rXVwMEAAAAAA==]]></Ticket>
//	    	</xml>

// 3
//	    	<xml><ToUserName><![CDATA[gh_df91c2626736]]></ToUserName>
//	    	<FromUserName><![CDATA[o1D_JwGKMNWZmBYLxghYYw0GIlUg]]></FromUserName>
//	    	<CreateTime>1473760322</CreateTime>
//	    	<MsgType><![CDATA[event]]></MsgType>
//	    	<Event><![CDATA[subscribe]]></Event>
//	    	<EventKey><![CDATA[qrscene_0_1]]></EventKey>
//	    	<Ticket><![CDATA[gQF68ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL3N6c0lTNkRsOGs2TFpKMjNIQk1RAAIE48rXVwMEAAAAAA==]]></Ticket>
//	    	</xml>
	    	
			if(xml != null){
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(new ByteArrayInputStream(xml.getBytes("utf-8")));
				Element root = doc.getRootElement();
				List<Element> elements = root.getChildren();
				if (elements != null && elements.size() != 0) {
					for(int i = 0; i < elements.size(); i++){
						element = elements.get(i);
						
						if("ToUserName".equals(element.getName()))
							toUserName = element.getText();
						else if("FromUserName".equals(element.getName()))
							fromUserName = element.getText();
						else if("Event".equals(element.getName()))
							event = element.getText();
						else if("EventKey".equals(element.getName()))
							eventKey = element.getText();
					}
				}
			}
			
			
			if(event != null){
				if("subscribe".equals(event)){
					if(!StringGlobal.IsNull(eventKey)){
						String[] eventKeys = eventKey.split("_");
						if(eventKeys != null && eventKeys.length > 0){
							if("qrscene".equals(eventKeys[0])){
								String type = null;
								if(eventKeys.length > 1)
									type = eventKeys[1];
								
								String id = null;
								if(eventKeys.length > 2)
									id = eventKeys[2];
								
								if(type != null && id != null){
									// commodity
									if("0".equals(type)){
										response.getWriter().print(wechatService.ReplyCommodity(fromUserName, toUserName, id));
										return null;
									}
									
									// store
									if("1".equals(type)){
										response.getWriter().print(wechatService.ReplyStore(fromUserName, toUserName, id));
										return null;
									}
								}
							}
						}
					}
					
					response.getWriter().print(wechatService.ReplyText(fromUserName, toUserName));
					//response.getWriter().print(wechatService.ReplyRichText(fromUserName, toUserName, "【寻咖行动】- 重金悬赏：寻找帝都的好咖啡！", "为了让这些美好的咖啡馆能够为更多人知道与喜爱，品社特别推出金秋特别企划： X Coffee | 寻咖行动。这是国内独立咖啡史上最激动人心的优惠活动，只为助你喝到好咖啡，享受好时光！", "http://mmbiz.qpic.cn/mmbiz_jpg/icjGTQas0nm1ff0964JzdCYzicBkYUwA93iaQQyGClbjWibceuUE4dicMu9jlKp2gdPzar3bYXRyWCsmSCCwsSgK5tg/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1", "http://mp.weixin.qq.com/s?__biz=MzIzNDQxNDU0OA==&mid=2247483675&idx=1&sn=3e58cf4eec91c5be5ccd3e47efbf4406&chksm=e8f789a0df8000b60e51ba4fdae3ea15c3982cc1b0428b9debe4889b0f029015c894f07e6bae&scene=2&srcid=0924ui2NFYPNlIu7qv7U6PyW&from=timeline&isappinstalled=0#wechat_redirect"));
					return null;
				}
				
				if("SCAN".equals(event)){
					String[] eventKeys = eventKey.split("_");
					if(eventKeys != null && eventKeys.length > 0){
						String type = null;
						if(eventKeys.length > 0)
							type = eventKeys[0];
						
						String id = null;
						if(eventKeys.length > 1)
							id = eventKeys[1];
						
						if(type != null && id != null){
							// commodity
							if("0".equals(type)){
								response.getWriter().print(wechatService.ReplyCommodity(fromUserName, toUserName, id));
								return null;
							}
							
							// store
							if("1".equals(type)){
								response.getWriter().print(wechatService.ReplyStore(fromUserName, toUserName, id));
								return null;
							}
						}
					}
				}
			}
			
			response.getWriter().print("");
    	}catch(Exception e){
    		e.printStackTrace();
    		try{response.getWriter().print("");}catch(IOException e1){}
    	}
    	
    	return null;
    }
	
	
	
	@RequestMapping(value = "/wechat_callback", method = {RequestMethod.GET})
	protected String ActionGet(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String echostr = request.getParameter("echostr");
		String signature = request.getParameter("signature");
	    String timestamp = request.getParameter("timestamp");
	    String nonce = request.getParameter("nonce");
	    
		if(!StringGlobal.IsNull(echostr) && !StringGlobal.IsNull(signature) && !StringGlobal.IsNull(timestamp) && !StringGlobal.IsNull(nonce)){
			if(CheckSignature(signature, timestamp, nonce, "TOKEN"))
				response.getWriter().print(echostr);
			return null;
		}
		
		return null;
	}
	
    private boolean CheckSignature(String signature, String timestamp, String nonce, String token) throws NoSuchAlgorithmException{  
	    String[] tmpArr = {token, timestamp, nonce};  
	    Arrays.sort(tmpArr);  
	    String tmpStr = ArrayToString(tmpArr);  
	    tmpStr = SHA1Encode(tmpStr);  
	    if(tmpStr.equalsIgnoreCase(signature))
	        return true;
	    
	    return false;
	}
	 
    private String ArrayToString(String [] arr){ 
    	StringBuffer bf = new StringBuffer();
    	for(int i = 0; i < arr.length; i++){
    		bf.append(arr[i]);
        }
        return bf.toString();
    }  
 
    private String SHA1Encode(String sourceString) throws NoSuchAlgorithmException{  
        String resultString = new String(sourceString);
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return Byte2hexString(md.digest(resultString.getBytes()));
    }
    
    private String Byte2hexString(byte[] bytes){
        StringBuffer buf = new StringBuffer(bytes.length * 2);  
        for(int i = 0; i < bytes.length; i++){  
            if(((int) bytes[i] & 0xff) < 0x10){  
                buf.append("0");  
            }
            buf.append(Long.toString((int)bytes[i] & 0xff, 16));  
        }
        return buf.toString().toUpperCase();  
    }	
}