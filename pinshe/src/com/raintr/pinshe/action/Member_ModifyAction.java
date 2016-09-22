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
public class Member_ModifyAction extends BaseAction {
	@Autowired
	private MemberService groupService;
	
	@RequestMapping(value = "/member_modify")
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
		String avatar = request.getParameter("avatar");
		String current = request.getParameter("current");
		String amount = request.getParameter("amount");
		String password = request.getParameter("password");
		String code = request.getParameter("code");

		if(!StringGlobal.IsNull(name))
			name = StringGlobal.SerializeJson(name);
		
		MemberBean member = groupService.ById(Integer.parseInt(id));
		if(member != null){
			if(!StringGlobal.IsNull(code)){
				if(!code.equals(Cache.phones.get(member.getPhone()))){
					response.getWriter().print("{\"head\":1,\"body\":{}}");
					return null;
				}
			}
			
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
				member.setCurrent(Double.parseDouble(String.format("%.2f", Double.parseDouble(current))));
			if(!StringGlobal.IsNull(amount))
				member.setAmount(Double.parseDouble(String.format("%.2f", Double.parseDouble(amount))));
			member.setModify_time(new Date());
			groupService.Modify(member);
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", member.ToId("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}