package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.service.UserService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class ModifyUserAction extends BaseAction {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/modifyuser")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String wechat = request.getParameter("wechat");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");

		if(StringGlobal.IsNull(id)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "id is null"));
			return null;
		}
		
		UserBean user = userService.ById(Integer.parseInt(id));
		if(user != null){
			name = StringGlobal.SerializeJson(name);
			wechat = StringGlobal.SerializeJson(wechat);
			phone = StringGlobal.SerializeJson(phone);
			address = StringGlobal.SerializeJson(address);
			
			MultipartFile file = null;
			try{
				file = ((MultipartHttpServletRequest)request).getFile("file");
			}catch(Exception ex){}
			
			if(!StringGlobal.IsNull(name))
				user.setName(name);
			if(!StringGlobal.IsNull(wechat))
				user.setWechat(wechat);
			if(!StringGlobal.IsNull(phone))
				user.setPhone(phone);
			if(!StringGlobal.IsNull(address))
				user.setAddress(address);
			user.setModify_time(new Date());
			
			userService.Modify(user, file);
	
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s,%s,%s,%s,%s,%s,%s}}", user.ToId(""),
																									 user.ToName(""),
																									 user.ToWechat(""),
																									 user.ToPhone(""),
																									 user.ToAddress(""),
																									 user.ToAvatar(""),
																									 user.ToModify_time("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}