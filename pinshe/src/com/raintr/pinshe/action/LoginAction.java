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
import com.raintr.pinshe.service.WechatService;
import com.raintr.pinshe.utils.Md5Utils;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class LoginAction extends BaseAction {
	@Autowired
	private WechatService wechatService;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/login")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String phone = request.getParameter("phone");
		String wechatId = request.getParameter("wcid");
		String password = request.getParameter("password");
		String reset = request.getParameter("reset");
		
		if(!StringGlobal.IsNull(reset) && !StringGlobal.IsNull(phone)){
			MemberBean member = memberService.ByPhone(phone);
			if(member != null){
				member.setPassword(Md5Utils.Md5(password));
				memberService.Modify(member);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", member.ToId("")));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(phone) && !StringGlobal.IsNull(password)){
			MemberBean member = memberService.ByPhonePassword(phone, Md5Utils.Md5(password));
			if(member != null){
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", member.ToId(""),
																		member.ToWechat_id(""),
																		member.ToName(""),
																		member.ToPhone(""),
																		member.ToAvatar(""),
																		member.ToCurrent(""),
																		member.ToAmount(""),
																		member.ToCreate_time(""),
																		member.ToModify_time("")));
	
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		
		
		if(!StringGlobal.IsNull(phone) && !StringGlobal.IsNull(wechatId)){
			MemberBean member = memberService.ByPhone(phone);
			if(member == null){
				member = memberService.ByWechatId(wechatId);
				if(member != null){
					member.setPhone(phone);
					member.setModify_time(new Date());
					memberService.Modify(member);
					
					StringBuffer json = new StringBuffer();
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", member.ToId(""),
																			member.ToWechat_id(""),
																			member.ToName(""),
																			member.ToPhone(""),
																			member.ToAvatar(""),
																			member.ToCurrent(""),
																			member.ToAmount(""),
																			member.ToCreate_time(""),
																			member.ToModify_time("")));
		
					response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
					return null;
				}
			}else{
				if(!wechatId.equals(member.getWechat_id())){
					MemberBean other = memberService.ByWechatId(wechatId);
					if(other != null){
						member.setWechat_id(other.getWechat_id());
						member.setName(other.getName());
						member.setAvatar(other.getAvatar());
						memberService.Modify(member);
						memberService.Remove(other.getId());
					}
				}
				
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", member.ToId(""),
																		member.ToWechat_id(""),
																		member.ToName(""),
																		member.ToPhone(""),
																		member.ToAvatar(""),
																		member.ToCurrent(""),
																		member.ToAmount(""),
																		member.ToCreate_time(""),
																		member.ToModify_time("")));
	
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