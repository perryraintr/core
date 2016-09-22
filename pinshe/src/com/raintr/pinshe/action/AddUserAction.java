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
public class AddUserAction extends BaseAction {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/adduser")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String name = request.getParameter("name");
		String wechat_guid = request.getParameter("wcid");
		String wechat = request.getParameter("wechat");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String avatar = request.getParameter("avatar");
		
		UserBean user = null;
		
		if(!StringGlobal.IsNull(wechat_guid))
			user = userService.ByWechatGuid(wechat_guid);
		
		if(user == null){
			name = StringGlobal.SerializeJson(name);
			wechat = StringGlobal.SerializeJson(wechat);
			phone = StringGlobal.SerializeJson(phone);
			address = StringGlobal.SerializeJson(address);
			//avatar = StringGlobal.SerializeJson(avatar);
			
			MultipartFile file = null;
			try{
				file = ((MultipartHttpServletRequest)request).getFile("file");
			}catch(Exception ex){}
			
			user = new UserBean();
			if(!StringGlobal.IsNull(name))
				user.setName(name);
			if(!StringGlobal.IsNull(wechat_guid))
				user.setWechat_guid(wechat_guid);
			if(!StringGlobal.IsNull(wechat))
				user.setWechat(wechat);
			if(!StringGlobal.IsNull(phone))
				user.setPhone(phone);
			if(!StringGlobal.IsNull(address))
				user.setAddress(address);
			if(!StringGlobal.IsNull(avatar))
				user.setAvatar(avatar);
			
			user.setCreate_time(new Date());
			user.setModify_time(new Date());
			user.setId(userService.Add(user, file));
		}
		
		StringBuffer json = new StringBuffer();
		json.append(String.format("%s,%s,%s,%s,%s,%s,%s", 	user.ToId(""),
															user.ToName(""),
															user.ToWechat(""),
															user.ToPhone(""),
															user.ToAddress(""),
															user.ToAvatar(""),
															user.ToModify_time("")));
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
		return null;
	}
}