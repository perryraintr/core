package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.service.MemberService;
import com.raintr.pinshe.utils.Cache;
import com.raintr.pinshe.utils.Md5Utils;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Member_AddAction extends BaseAction {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/member_add")
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
		
		MemberBean member = null;
		
		if(!StringGlobal.IsNull(wechatId))
			member = memberService.ByWechatId(wechatId);
		else if(!StringGlobal.IsNull(phone))
			member = memberService.ByPhone(phone);
		
		if(member == null){
			member = new MemberBean();
			if(!StringGlobal.IsNull(wechatId))
				member.setWechat_id(wechatId);
			if(!StringGlobal.IsNull(name))
				member.setName(name);
			if(!StringGlobal.IsNull(password))
				member.setPassword(Md5Utils.Md5(password));
			if(!StringGlobal.IsNull(phone))
				member.setPhone(phone);
			if(!StringGlobal.IsNull(avatar))
				member.setAvatar(avatar);
			if(!StringGlobal.IsNull(current))
				member.setCurrent(Double.parseDouble(current));
			if(!StringGlobal.IsNull(amount))
				member.setAmount(Double.parseDouble(amount));
			member.setCreate_time(new Date());
			member.setModify_time(new Date());
			member.setId(memberService.Add(member));
		}else{
			if(!StringGlobal.IsNull(wechatId))
				member.setWechat_id(wechatId);
			if(!StringGlobal.IsNull(name))
				member.setName(name);
			if(!StringGlobal.IsNull(password))
				member.setPassword(Md5Utils.Md5(password));
			if(!StringGlobal.IsNull(phone))
				member.setPhone(phone);
			if(!StringGlobal.IsNull(avatar))
				member.setAvatar(avatar);
			if(!StringGlobal.IsNull(current))
				member.setCurrent(Double.parseDouble(current));
			if(!StringGlobal.IsNull(amount))
				member.setAmount(Double.parseDouble(amount));
			member.setModify_time(new Date());
			memberService.Modify(member);
		}
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s,%s,%s,%s,%s,%s,%s,%s,%s}}", 	member.ToId(""),
																										member.ToWechat_id(""),
																										member.ToName(""),
																										member.ToPhone(""),
																										member.ToAvatar(""),
																										member.ToCurrent(""),
																										member.ToAmount(""),
																										member.ToCreate_time(""),
																										member.ToModify_time("")));
		return null;
	}
}