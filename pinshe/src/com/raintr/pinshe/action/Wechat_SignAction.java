package com.raintr.pinshe.action;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.service.WechatService;

@Controller
@RequestMapping(value = "/")
public class Wechat_SignAction extends BaseAction {
	@Autowired
	private WechatService wechatService;
	
	@RequestMapping(value = "/wechat_sign")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String callBack = request.getParameter("url");
		
//		String jsapi_ticket = null;
//		String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", wechatService.getAppid(), wechatService.getAppsecret());
//		String json = NetGlobal.HttpGet(url, null);
//		if(json != null && json.length() > 0){
//			JSONObject row = JSON.parseObject(json);			
//			url = String.format("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi", row.getString("access_token"));
//			json = NetGlobal.HttpGet(url, null);
//			if(json != null && json.length() > 0){
//				row = JSON.parseObject(json);
//				jsapi_ticket = row.getString("ticket");
//			}
//		}
		
		String jsapi_ticket = wechatService.GetJsapiTicket();

        Map<String, String> ret = sign(jsapi_ticket, callBack);
//        for (Entry<String, String> entry : ret.entrySet()){
//            System.out.println(entry.getKey() + ", " + entry.getValue());
//        }
        
        response.getWriter().print(String.format("{\"appId\":\"%s\",\"timestamp\":\"%s\",\"nonceStr\":\"%s\",\"signature\":\"%s\"}", wechatService.getAppid(), ret.get("timestamp"), ret.get("nonceStr"), ret.get("signature")));
        
        return null;
	} 
	
	
    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        //System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	
	
}