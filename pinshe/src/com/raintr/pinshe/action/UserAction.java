package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.service.UserService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class UserAction extends BaseAction {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/user")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String wechat_guid = request.getParameter("wcid");
		String userId = request.getParameter("uid");
		
		if(!StringGlobal.IsNull(userId)){
			UserBean user = userService.ById(Integer.parseInt(userId));
			
			if(user != null){
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s,%s,%s,%s,%s,%s,%s}}", user.ToId(""),
																									     user.ToName(""),
																									     user.ToWechat(""),
																										 user.ToPhone(""),
																										 user.ToAddress(""),
																										 user.ToAvatar(""),
																										 user.ToModify_time("")));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(wechat_guid)){
			UserBean user = userService.ByWechatGuid(wechat_guid);
			if(user != null){
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s,%s,%s,%s,%s,%s,%s}}", user.ToId(""),
																									     user.ToName(""),
																									     user.ToWechat(""),
																										 user.ToPhone(""),
																										 user.ToAddress(""),
																										 user.ToAvatar(""),
																										 user.ToModify_time("")));
				return null;
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}