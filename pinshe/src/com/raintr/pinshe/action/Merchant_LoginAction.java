package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MerchantBean;
import com.raintr.pinshe.service.MerchantService;
import com.raintr.pinshe.service.WechatService;
import com.raintr.pinshe.utils.Md5Utils;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Merchant_LoginAction extends BaseAction {
	@Autowired
	private WechatService wechatService;
	@Autowired
	private MerchantService merchantService;
	
	@RequestMapping(value = "/merchant_login")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String phone = request.getParameter("phone");
		String wechatId = request.getParameter("wcid");
		String password = request.getParameter("password");
		String reset = request.getParameter("reset");
		String device = request.getParameter("device");
		String getuiId = request.getParameter("getui");
		
		if(!StringGlobal.IsNull(reset) && !StringGlobal.IsNull(phone)){
			MerchantBean merchant = merchantService.ByPhone(phone);
			if(merchant != null){
				merchant.setPassword(Md5Utils.Md5(password));
				merchantService.Modify(merchant);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", merchant.ToId("")));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(phone) && !StringGlobal.IsNull(password)){
			MerchantBean merchant = merchantService.ByPhonePassword(phone, Md5Utils.Md5(password));
			if(merchant != null){
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", merchant.ToId(""),
																		merchant.ToWechat_id(""),
																		merchant.ToName(""),
																		merchant.ToPhone(""),
																		merchant.ToAvatar(""),
																		merchant.ToCurrent(""),
																		merchant.ToAmount(""),
																		merchant.ToCreate_time(""),
																		merchant.ToModify_time("")));
	
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		
		
		if(!StringGlobal.IsNull(phone) && !StringGlobal.IsNull(wechatId)){
			MerchantBean merchant = merchantService.ByPhone(phone);
			if(merchant == null){
				merchant = merchantService.ByWechatId(wechatId);
				if(merchant != null){
					merchant.setPhone(phone);
					if(!StringGlobal.IsNull(device))
						merchant.setDevice(Integer.parseInt(device));
					if(!StringGlobal.IsNull(getuiId))
						merchant.setGetui_id(getuiId);
					merchant.setModify_time(new Date());
					merchantService.Modify(merchant);
					
					StringBuffer json = new StringBuffer();
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", merchant.ToId(""),
																			merchant.ToWechat_id(""),
																			merchant.ToName(""),
																			merchant.ToPhone(""),
																			merchant.ToAvatar(""),
																			merchant.ToCurrent(""),
																			merchant.ToAmount(""),
																			merchant.ToCreate_time(""),
																			merchant.ToModify_time("")));
		
					response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
					return null;
				}
			}else{
				if(!wechatId.equals(merchant.getWechat_id())){
					MerchantBean other = merchantService.ByWechatId(wechatId);
					if(other != null){
						merchant.setWechat_id(other.getWechat_id());
						merchant.setName(other.getName());
						merchant.setAvatar(other.getAvatar());
						if(!StringGlobal.IsNull(device))
							merchant.setDevice(Integer.parseInt(device));
						if(!StringGlobal.IsNull(getuiId))
							merchant.setGetui_id(getuiId);
						merchantService.Modify(merchant);
						merchantService.Remove(other.getId());
					}
				}
				
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", merchant.ToId(""),
																		merchant.ToWechat_id(""),
																		merchant.ToName(""),
																		merchant.ToPhone(""),
																		merchant.ToAvatar(""),
																		merchant.ToCurrent(""),
																		merchant.ToAmount(""),
																		merchant.ToCreate_time(""),
																		merchant.ToModify_time("")));
	
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}