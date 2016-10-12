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
import com.raintr.pinshe.utils.Cache;
import com.raintr.pinshe.utils.Md5Utils;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Merchant_AddAction extends BaseAction {
	@Autowired
	private MerchantService merchantService;
	
	@RequestMapping(value = "/merchant_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String wechatId = request.getParameter("wcid");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String avatar = request.getParameter("avatar");
		String current = request.getParameter("current");
		String amount = request.getParameter("amount");
		String password = request.getParameter("password");
		String code = request.getParameter("code");

		if(StringGlobal.IsNull(wechatId) && StringGlobal.IsNull(phone)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"wcid and phone is null\"}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(code)){
			if(!code.equals(Cache.phones.get(phone))){
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s:%s}}", Cache.phones.get(phone), code));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(name))
			name = StringGlobal.SerializeJson(name);
		
		MerchantBean merchant = null;
		
		if(!StringGlobal.IsNull(wechatId))
			merchant = merchantService.ByWechatId(wechatId);
		else if(!StringGlobal.IsNull(phone))
			merchant = merchantService.ByPhone(phone);
		
		if(merchant == null){
			merchant = new MerchantBean();
			if(!StringGlobal.IsNull(wechatId))
				merchant.setWechat_id(wechatId);
			if(!StringGlobal.IsNull(name))
				merchant.setName(name);
			if(!StringGlobal.IsNull(password))
				merchant.setPassword(Md5Utils.Md5(password));
			if(!StringGlobal.IsNull(phone))
				merchant.setPhone(phone);
			if(!StringGlobal.IsNull(avatar))
				merchant.setAvatar(avatar);
			if(!StringGlobal.IsNull(current))
				merchant.setCurrent(Double.parseDouble(current));
			if(!StringGlobal.IsNull(amount))
				merchant.setAmount(Double.parseDouble(amount));
			merchant.setCreate_time(new Date());
			merchant.setModify_time(new Date());
			merchant.setId(merchantService.Add(merchant));
		}else{
			if(!StringGlobal.IsNull(wechatId))
				merchant.setWechat_id(wechatId);
			if(!StringGlobal.IsNull(name))
				merchant.setName(name);
			if(!StringGlobal.IsNull(password))
				merchant.setPassword(Md5Utils.Md5(password));
			if(!StringGlobal.IsNull(phone))
				merchant.setPhone(phone);
			if(!StringGlobal.IsNull(avatar))
				merchant.setAvatar(avatar);
			if(!StringGlobal.IsNull(current))
				merchant.setCurrent(Double.parseDouble(current));
			if(!StringGlobal.IsNull(amount))
				merchant.setAmount(Double.parseDouble(amount));
			merchant.setModify_time(new Date());
			merchantService.Modify(merchant);
		}
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s,%s,%s,%s,%s,%s,%s,%s,%s}}", 	merchant.ToId(""),
																										merchant.ToWechat_id(""),
																										merchant.ToName(""),
																										merchant.ToPhone(""),
																										merchant.ToAvatar(""),
																										merchant.ToCurrent(""),
																										merchant.ToAmount(""),
																										merchant.ToCreate_time(""),
																										merchant.ToModify_time("")));
		return null;
	}
}