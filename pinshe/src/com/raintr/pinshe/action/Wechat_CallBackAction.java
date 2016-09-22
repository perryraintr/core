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
	    	FileGlobal.AddFile(xml, "", "/opt/log/");
	    	
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
							}else{
								response.getWriter().print(wechatService.ReplyText(fromUserName, toUserName));
								return null;
							}
						}
					}
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
	
	
//	
//	public class WeiXinHandler { 
//	    final String TOKEN="pinshe";  
//	    final HttpServletRequest final_request=request;   
//	    final HttpServletResponse final_response=response; 
//	    
//	    public void valid(){  
//	        String echostr=final_request.getParameter("echostr");  
//	        if(null==echostr||echostr.isEmpty()){  
//	            responseMsg();  
//	        }else{  
//	            if(this.checkSignature()){  
//	                this.print(echostr);  
//	            }else{  
//	                this.print("error");                                                                                                                                                                                                                                                                                                                                           
//	            }  
//	        }  
//	    }  
//	    //自动回复内容  
//	    public void responseMsg(){  
//	        String postStr=null;  
//	        try{  
//	            postStr=this.readStreamParameter(final_request.getInputStream());  
//	        }catch(Exception e){  
//	            e.printStackTrace();  
//	        }  
//	        //System.out.println(postStr);  
//	        if (null!=postStr&&!postStr.isEmpty()){  
//	            Document document=null;  
//	            try{  
//	                document = DocumentHelper.parseText(postStr);  
//	            }catch(Exception e){  
//	                e.printStackTrace();  
//	            }  
//	            if(null==document){  
//	                this.print("");  
//	                return;  
//	            }  
//	            Element root=document.getRootElement();  
//	            String fromUsername = root.elementText("FromUserName");  
//	            String toUsername = root.elementText("ToUserName");  
//	            String keyword = root.elementTextTrim("Content");  
//	            String time = new Date().getTime()+"";  
//	            String textTpl = "<xml>"+  
//	                        "<ToUserName><![CDATA[%1$s]]></ToUserName>"+  
//	                        "<FromUserName><![CDATA[%2$s]]></FromUserName>"+  
//	                        "<CreateTime>%3$s</CreateTime>"+  
//	                        "<MsgType><![CDATA[%4$s]]></MsgType>"+  
//	                        "<Content><![CDATA[%5$s]]></Content>"+  
//	                        "<FuncFlag>0</FuncFlag>"+  
//	                        "</xml>";               
//	              
//	            if(null!=keyword&&!keyword.equals(""))  
//	            {  
//	                String msgType = "text";  
//	                String contentStr = "Welcome to wechat world!";  
//	                String resultStr = textTpl.format(textTpl, fromUsername, toUsername, time, msgType, contentStr);  
//	                this.print(resultStr);  
//	            }else{  
//	                this.print("Input something...");  
//	            }  
//	  
//	        }else {  
//	            this.print("");  
//	        }  
//	    }  
//	    //微信接口验证  
//	    public boolean checkSignature(){  
//	        String signature = final_request.getParameter("signature");  
//	        String timestamp = final_request.getParameter("timestamp");  
//	        String nonce = final_request.getParameter("nonce");  
//	        String token=TOKEN;  
//	        String[] tmpArr={token,timestamp,nonce};  
//	        Arrays.sort(tmpArr);  
//	        String tmpStr=this.ArrayToString(tmpArr);  
//	        tmpStr=this.SHA1Encode(tmpStr);  
//	        if(tmpStr.equalsIgnoreCase(signature)){  
//	            return true;  
//	        }else{  
//	            return false;  
//	        }  
//	    }  
//	    //向请求端发送返回数据  
//	    public void print(String content){  
//	        try{  
//	            final_response.getWriter().print(content);  
//	            final_response.getWriter().flush();  
//	            final_response.getWriter().close();  
//	        }catch(Exception e){  
//	              
//	        }  
//	    }  
//	    //数组转字符串  
//	    public String ArrayToString(String [] arr){  
//	        StringBuffer bf = new StringBuffer();  
//	        for(int i = 0; i < arr.length; i++){  
//	         bf.append(arr[i]);  
//	        }  
//	        return bf.toString();  
//	    }  
//	    //sha1加密  
//	    public String SHA1Encode(String sourceString) {  
//	        String resultString = null;  
//	        try {  
//	           resultString = new String(sourceString);  
//	           MessageDigest md = MessageDigest.getInstance("SHA-1");  
//	           resultString = byte2hexString(md.digest(resultString.getBytes()));  
//	        } catch (Exception ex) {  
//	        }  
//	        return resultString;  
//	    }  
//	    public final String byte2hexString(byte[] bytes) {  
//	        StringBuffer buf = new StringBuffer(bytes.length * 2);  
//	        for (int i = 0; i < bytes.length; i++) {  
//	            if (((int) bytes[i] & 0xff) < 0x10) {  
//	                buf.append("0");  
//	            }  
//	            buf.append(Long.toString((int) bytes[i] & 0xff, 16));  
//	        }  
//	        return buf.toString().toUpperCase();  
//	    }  
//	    //从输入流读取post参数  
//	    public String readStreamParameter(ServletInputStream in){  
//	        StringBuilder buffer = new StringBuilder();  
//	        BufferedReader reader=null;  
//	        try{  
//	            reader = new BufferedReader(new InputStreamReader(in));  
//	            String line=null;  
//	            while((line = reader.readLine())!=null){  
//	                buffer.append(line);  
//	            }  
//	        }catch(Exception e){  
//	            e.printStackTrace();  
//	        }finally{  
//	            if(null!=reader){  
//	                try {  
//	                    reader.close();  
//	                } catch (IOException e) {  
//	                    e.printStackTrace();  
//	                }  
//	            }  
//	        }  
//	        return buffer.toString();  
//	    }  
//	}
	
}