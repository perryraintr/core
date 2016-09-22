package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.service.MemberService;
import com.raintr.pinshe.service.WechatService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class WechatAction extends BaseAction {
	@Autowired
	private WechatService wechatService;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/wechat")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String code = request.getParameter("code");
		String url = request.getParameter("state");
		if(!StringGlobal.IsNull(code) && !StringGlobal.IsNull(url)){
			UserBean user = wechatService.By(code);
			
			if(user != null){
				MemberBean member = memberService.ByWechatId(user.getWechat_guid());
				if(member == null){
					member = new MemberBean();
					member.setWechat_id(user.getWechat_guid());
					member.setName(StringGlobal.SerializeJson(user.getName()));
					member.setPhone("");
					member.setAvatar(user.getAvatar());
					member.setCurrent(0);
					member.setAmount(0);
					member.setCreate_time(new Date());
					member.setModify_time(new Date());
					memberService.Add(member);
				}else{
					if(!member.getAvatar().equals(user.getAvatar())){
						member.setAvatar(user.getAvatar());
						member.setModify_time(new Date());
						memberService.Modify(member);
					}
				}
				
				return "redirect:http://www.pinshe.org/html/v1/coffee/wechat.html?url=" + url + "&wcid=" + user.getWechat_guid() + "&phone=" + member.getPhone();
			}
		}
		
		return "redirect:" + url;
	}
}