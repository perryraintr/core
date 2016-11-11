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
public class Merchant_ModifyAction extends BaseAction {
	@Autowired
	private MerchantService groupService;
	
	@RequestMapping(value = "/merchant_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		
		if(StringGlobal.IsNull(id)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"id is null\"}}");
			return null;
		}
		
		String wechatId = request.getParameter("wcid");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String device = request.getParameter("device");
		String getuiId = request.getParameter("getui");
		String avatar = request.getParameter("avatar");
		String current = request.getParameter("current");
		String amount = request.getParameter("amount");
		String password = request.getParameter("password");
		String code = request.getParameter("code");

		if(!StringGlobal.IsNull(name))
			name = StringGlobal.SerializeJson(name);
		
		MerchantBean merchant = groupService.ById(Integer.parseInt(id));
		if(merchant != null){
			if(!StringGlobal.IsNull(code)){
				if(!code.equals(Cache.phones.get(merchant.getPhone()))){
					response.getWriter().print("{\"head\":1,\"body\":{}}");
					return null;
				}
			}
			
			if(!StringGlobal.IsNull(wechatId))
				merchant.setWechat_id(wechatId);
			if(!StringGlobal.IsNull(name))
				merchant.setName(name);
			if(!StringGlobal.IsNull(password))
				merchant.setPassword(Md5Utils.Md5(password));
			if(!StringGlobal.IsNull(phone))
				merchant.setPhone(phone);
			if(!StringGlobal.IsNull(device))
				merchant.setDevice(Integer.parseInt(device));
			if(!StringGlobal.IsNull(getuiId))
				merchant.setGetui_id(getuiId);
			if(!StringGlobal.IsNull(avatar))
				merchant.setAvatar(avatar);
			if(!StringGlobal.IsNull(current))
				merchant.setCurrent(Double.parseDouble(String.format("%.2f", Double.parseDouble(current))));
			if(!StringGlobal.IsNull(amount))
				merchant.setAmount(Double.parseDouble(String.format("%.2f", Double.parseDouble(amount))));
			merchant.setModify_time(new Date());
			groupService.Modify(merchant);
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", merchant.ToId("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}