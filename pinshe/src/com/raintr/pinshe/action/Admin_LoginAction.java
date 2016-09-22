package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.AdminBean;
import com.raintr.pinshe.service.AdminService;
import com.raintr.pinshe.utils.Md5Utils;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_LoginAction extends BaseAction {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = "/admin_login")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String method = request.getParameter("method");
		
		if("signup".equals(method)){
			AdminBean admin = adminService.ByPhone(phone);			
			if(admin == null){
				admin = new AdminBean();
				if(!StringGlobal.IsNull(name))
					admin.setName(name);
				if(!StringGlobal.IsNull(phone))
					admin.setPhone(phone);
				if(!StringGlobal.IsNull(password))
					admin.setPassword(Md5Utils.Md5(password));
				admin.setCreate_time(new Date());
				admin.setModify_time(new Date());
				admin.setId(adminService.Add(admin));
				
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s", admin.ToId(""),
															admin.ToName(""),
															admin.ToPhone(""),
															admin.ToCreate_time(""),
															admin.ToModify_time("")));
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}			
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if("reset".equals(method)){
			AdminBean admin = adminService.ByPhone(phone);
			if(admin != null){
				admin.setPassword(Md5Utils.Md5(password));
				adminService.Modify(admin);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", admin.ToId("")));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if("login".equals(method)){
			AdminBean admin = adminService.ByPhonePassword(phone, Md5Utils.Md5(password));
			if(admin != null){
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s", admin.ToId(""),
															admin.ToName(""),
															admin.ToPhone(""),
															admin.ToCreate_time(""),
															admin.ToModify_time("")));
	
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